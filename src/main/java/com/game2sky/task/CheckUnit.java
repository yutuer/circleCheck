package com.game2sky.task;

import com.game2sky.application.log.BPLog;
import com.game2sky.svn.ModeEnum;
import com.game2sky.svn.SvnCheckTask;
import com.game2sky.util;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description 一个检查单元
 * @Author zhangfan
 * @Date 2020/7/9 18:13
 * @Version 1.0
 */
public class CheckUnit
{
    /**
     * 控制流程对象
     */
    private SvnCheckTask checker;

    /**
     * 定时器, 由外部传入. 建议每个checkunit复用一个
     */
    private ScheduledExecutorService scheduledExecutorService;

    /**
     * 定时器到了执行时间, 执行的操作
     */
    private ITickCheckerLogic tickLogick;

    /**
     * 第i次运行
     */
    private int count;

    /**
     * 当前运行模式
     */
    private ModeEnum mode;

    private final int cirecleTime;
    private final int laterPackageTime;
    private final int errorCheckTime;

    public CheckUnit(SvnCheckTask checker, ScheduledExecutorService scheduledExecutorService, ITickCheckerLogic tickLogick, int round, ModeEnum modeEnum, int cirecleTime, int laterPackageTime, int errorCheckTime)
    {
        this.checker = checker;
        this.scheduledExecutorService = scheduledExecutorService;
        this.tickLogick = tickLogick;
        if (tickLogick == null)
        {
            throw new RuntimeException("没有设置tick检查Logic实例");
        }

        this.count = round;
        this.mode = modeEnum;
        this.cirecleTime = cirecleTime;
        this.laterPackageTime = laterPackageTime;
        this.errorCheckTime = errorCheckTime;
    }

    /**
     * 定时检查更新
     */
    private ScheduledFuture<?> circleCheckScheduledFuture;

    public void startCheckExcelUpdate()
    {
        BPLog.BP_SVN.info("进入 {} 第{}次处理, 当前mode为 [{}]", tickLogick.getNameDesc(), count, mode);

        if (mode == ModeEnum.Normal)
        {
            this.circleCheckScheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(() ->
            {
                boolean boolVal = tickLogick.hasUpdate();
                if (boolVal)
                {
                    // 如果更新到内容. 停止circle的, 启动检查的
                    if (stopTicker())
                    {
                        // 启动目标检查
                        beginNextCheckSchedule(count, laterPackageTime);
                    }
                }
            }, 0, cirecleTime, TimeUnit.SECONDS);

        }
        else if (mode == ModeEnum.Error)
        {
            // 时间会短些, 报错已经发出. 等待提交后再次验证
            beginNextCheckSchedule(count, errorCheckTime);
        }
    }

    /**
     * 停下定时器
     */
    private boolean stopTicker()
    {
        final ScheduledFuture<?> circleCheckScheduledFuture = this.circleCheckScheduledFuture;
        if (circleCheckScheduledFuture != null && !circleCheckScheduledFuture.isCancelled())
        {
            BPLog.BP_SVN.info("检查到 {} 有更新, 开始进入打包等待计时", tickLogick.getNameDesc());

            boolean cancel = circleCheckScheduledFuture.cancel(true);
            BPLog.BP_SVN.info("cancel {} 定时任务, result:{}", tickLogick.getNameDesc(), cancel);

            return cancel;
        }
        return false;
    }

    /**
     * time时间后开始下一次check
     *
     * @param time
     */
    private void beginNextCheckSchedule(final int count, final int time)
    {
        int nextCount = count + 1;
        BPLog.BP_SVN.info("{} 准备等待 {}{}后, 进入下一轮 [round: {}]", tickLogick.getNameDesc(), time, util.showSecondDesc(TimeUnit.SECONDS), nextCount);

        // 时间会短些, 报错已经发出. 等待提交后再次验证

        // !!!!!!!!!一定要有try catch, 否则错误会被schedule吞掉, 查都查不出来
        scheduledExecutorService.schedule(() ->
        {
            BPLog.BP_SVN.info("{} 已经等待 {}{} 后, 执行 [round:{}] 的最后一次逻辑(更新并Check)", tickLogick.getNameDesc(), time, util.showSecondDesc(TimeUnit.SECONDS), count);

            // !!!!!!!!!一定要有try catch
            try
            {
                int check = tickLogick.execCheck();
                if (check > 0)
                {
                    checker.setMode(ModeEnum.Error);
                }
                else
                {
                    checker.setMode(ModeEnum.Normal);
                }

                // 执行完这次的流程后,  将控制权移交给上层, 由上层启动下一次
                checker.lastUnitOver(nextCount);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }, time, TimeUnit.SECONDS);

    }
}

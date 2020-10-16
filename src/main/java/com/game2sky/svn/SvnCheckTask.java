package com.game2sky.svn;

import com.game2sky.task.CheckUnit;
import com.game2sky.application.log.BPLog;
import com.game2sky.task.ISvnTaskConfiguration;
import com.game2sky.svn.config.IReloadConfig;
import com.game2sky.task.ITickCheckerLogic;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @Description 检查的模板类
 * @Author zhangfan
 * @Date 2020/8/21 21:11
 * @Version 1.0
 */
public class SvnCheckTask
{
    /**
     * 计时Es.
     */
    protected ScheduledExecutorService scheduledExecutorService;

    /**
     * 循环起来的检查逻辑
     */
    private ITickCheckerLogic tickCheckerLogic;

    /**
     * 配置
     */
    private ISvnTaskConfiguration configuration;

    /**
     * 当前运行模式
     */
    private ModeEnum mode = ModeEnum.Normal;

    public SvnCheckTask(String threadName, ITickCheckerLogic tickCheckerLogic, ISvnTaskConfiguration configuration)
    {
        if (tickCheckerLogic == null)
        {
            throw new RuntimeException("需要提供一个 tickCheckerLogic实例");
        }
        this.tickCheckerLogic = tickCheckerLogic;
        this.configuration = configuration;

        // 单线程
        scheduledExecutorService = Executors.newScheduledThreadPool(1, (r) ->
        {
            return new Thread(r, threadName);
        });
    }

    /**
     * start中执行一次的逻辑
     *
     * @return 返回错误值, 此值
     */
    private int startOnceLogic()
    {
        return tickCheckerLogic.execCheck();
    }

    public void start()
    {
        int errCount = startOnceLogic();
        if (errCount > 0)
        {
            // 进入错误处理模式
            mode = ModeEnum.Error;
        }

        // 开启单元流程
        begin(1);
    }

    public void begin(int round)
    {
        BPLog.BP_SVN.info("启动 {} 第 [{}] 次检查", tickCheckerLogic.getNameDesc(), round);

        ModeEnum mode = getMode();

        CheckUnit svnCheckUnit = new CheckUnit(this, scheduledExecutorService, tickCheckerLogic,
                round, mode, configuration.getCircleCheckTime(), configuration.getLaterCheckTime(), configuration.getErrorCheckTime());

        svnCheckUnit.startCheckExcelUpdate();
    }

    public ModeEnum getMode()
    {
        return mode;
    }

    public void setMode(ModeEnum mode)
    {
        this.mode = mode;
    }

    /**
     * 上轮结束
     *
     * @param count
     */
    public void lastUnitOver(final int count)
    {
        // 重新加载配置
        reloadProperties();

        // 开始下一次循环
        begin(count);
    }

    /**
     * 重新读取配置
     */
    private void reloadProperties()
    {
        if (configuration instanceof IReloadConfig)
        {
            IReloadConfig.class.cast(configuration).reloadConfig();
        }
    }
}

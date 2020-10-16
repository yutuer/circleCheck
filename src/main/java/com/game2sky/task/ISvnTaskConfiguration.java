package com.game2sky.task;

/**
 * @Description svn任务配置
 * @Author zhangfan
 * @Date 2020/8/21 10:01
 * @Version 1.0
 */
public interface ISvnTaskConfiguration
{

    /**
    检查流程
    1. 启动的时候 {@link ITickCheckerLogic#execCheck} check一次
    2. 开启检测循环unit
        2.1 每隔circleCheckTime 检查是否有更新 {@link ITickCheckerLogic#hasUpdate}
        2.2 如果有更新, 则LaterCheckTime后再次执行 check
            2.2.1 如果有错误, 则ErrorCheckTime后再次执行 check.
            2.2.2 否则跳到2.1
     */

    /**
     * unit检查更新的时间
     *
     * @return
     */
    int getCircleCheckTime();

    /**
     * 如果检查到更新, 则LaterCheck时间后执行检查(中间的时间给连续提交提供了时间)
     *
     * @return
     */
    int getLaterCheckTime();

    /**
     * 出错后, 下次更新再次检查的时间
     * @return
     */
    int getErrorCheckTime();
}

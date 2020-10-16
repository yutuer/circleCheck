package com.game2sky.tickLogic;

/**
 * @Description 定时检查更新逻辑
 * @Author zhangfan
 * @Date 2020/7/28 14:24
 * @Version 1.0
 */
public interface ITickCheckerLogic
{

    /**
     * 检查更新
     *
     * @return 是否有更新
     */
    boolean hasUpdate();

    /**
     * 执行check逻辑
     *
     * @return 如果大于0, 表示有错误
     */
    int execChecker();

    /**
     * 获得tick任务大致描述
     *
     * @return
     */
    String getNameDesc();

}

package com.game2sky.core;

/**
 * @Description svn定时检查任务接口, 需要配合configuration一起使用
 * @Author zhangfan
 * @Date 2020/8/21 10:00
 * @Version 1.0
 */
public interface ISvnOperaCheckTask
{
    /**
     * 开始任务
     */
    void start();

    /**
     * 进行下一轮
     *
     * @param round
     */
    void begin(int round);

}

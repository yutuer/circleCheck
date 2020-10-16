package com.game2sky.core;

import com.game2sky.task.ISvnTaskConfiguration;
import com.game2sky.task.ITickCheckerLogic;
import com.game2sky.task.TaskContext;

/**
 * @Description svn 定时任务执行容器
 * @Author zhangfan
 * @Date 2020/8/21 9:59
 * @Version 1.0
 */
public interface ISvnTaskExecutor
{

    /**
     * 提交执行一个svn 任务
     *
     * @param task
     * @param configuration
     */
    void executor(String threadName, ITickCheckerLogic task, ISvnTaskConfiguration configuration);

    /**
     * 提交一个任务
     *
     * @param taskContext
     */
    void executor(TaskContext taskContext);
}

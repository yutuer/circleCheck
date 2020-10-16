package com.game2sky.svn;

import com.game2sky.task.ISvnTaskConfiguration;
import com.game2sky.core.ISvnTaskExecutor;
import com.game2sky.task.ITickCheckerLogic;
import com.game2sky.task.TaskContext;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Description 门面类, 负责和客户端交互
 * 具体的任务由 {@link ITickCheckerLogic} 传入
 * @Author zhangfan
 * @Date 2020/7/2 22:33
 * @Version 1.0
 */
public class SvnCommitChecker implements ISvnTaskExecutor
{
    /**
     * 任务提交队列(单线程)
     */
    private Executor executor;

    public SvnCommitChecker()
    {
        executor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void executor(TaskContext taskContext)
    {
        this.executor(taskContext.getThreadName(), taskContext.getTask(), taskContext.getConfiguration());
    }

    @Override
    public void executor(String threadName, ITickCheckerLogic task, ISvnTaskConfiguration configuration)
    {
        executor.execute(() ->
        {
            SvnCheckTask svnCheckTask = new SvnCheckTask(threadName, task, configuration);
            svnCheckTask.start();
        });
    }

}

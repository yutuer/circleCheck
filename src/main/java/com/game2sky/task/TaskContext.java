package com.game2sky.task;

/**
 * @Description 任务上下文
 * @Author zhangfan
 * @Date 2020/8/24 11:22
 * @Version 1.0
 */
public class TaskContext
{
    private String threadName;
    private ITickCheckerLogic task;
    private ISvnTaskConfiguration configuration;

    public TaskContext()
    {
    }

    public TaskContext(String threadName, ITickCheckerLogic task, ISvnTaskConfiguration configuration)
    {
        this.threadName = threadName;
        this.task = task;
        this.configuration = configuration;
    }

    public void setThreadName(String threadName)
    {
        this.threadName = threadName;
    }

    public void setTask(ITickCheckerLogic task)
    {
        this.task = task;
    }

    public void setConfiguration(ISvnTaskConfiguration configuration)
    {
        this.configuration = configuration;
    }

    public String getThreadName()
    {
        return threadName;
    }

    public ITickCheckerLogic getTask()
    {
        return task;
    }

    public ISvnTaskConfiguration getConfiguration()
    {
        return configuration;
    }
}

package com.game2sky.svn.config;

import com.game2sky.task.ISvnTaskConfiguration;

/**
 * @Description 检查更新的时间配置
 * @Author zhangfan
 * @Date 2020/7/29 15:00
 * @Version 1.0
 */
public class SvnCommitCheckerConfig implements ISvnTaskConfiguration
{
    /**
     * 任务循环时间
     */
    private int cirecleTime;

    /**
     * 延迟时间
     */
    private int laterPackageTime;

    /**
     * 出错的话, 下次检查时间延迟
     */
    private int errorCheckTime;

    public SvnCommitCheckerConfig(int cirecleTime, int laterPackageTime, int errorCheckTime)
    {
        this.cirecleTime = cirecleTime;
        this.laterPackageTime = laterPackageTime;
        this.errorCheckTime = errorCheckTime;
    }

    /**
     * 更新配置
     *
     * @param cirecleTime
     * @param laterPackageTime
     * @param errorCheckTime
     */
    public void update(int cirecleTime, int laterPackageTime, int errorCheckTime)
    {
        this.cirecleTime = cirecleTime;
        this.laterPackageTime = laterPackageTime;
        this.errorCheckTime = errorCheckTime;
    }

    public int getCircleCheckTime()
    {
        return cirecleTime;
    }

    public int getLaterCheckTime()
    {
        return laterPackageTime;
    }

    public int getErrorCheckTime()
    {
        return errorCheckTime;
    }
}

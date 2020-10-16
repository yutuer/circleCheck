package com.game2sky.svn.config;

import com.game2sky.CheckConfig;
import com.game2sky.application.log.BPLog;
import com.game2sky.task.ISvnTaskConfiguration;

/**
 * @Description 地图时间配置重加载器
 * @Author zhangfan
 * @Date 2020/7/29 16:05
 * @Version 1.0
 */
public class MapReloadConfig implements IReloadConfig, ISvnTaskConfiguration
{

    private SvnCommitCheckerConfig svnCommitCheckerConfig;

    public MapReloadConfig(SvnCommitCheckerConfig svnCommitCheckerConfig)
    {
        this.svnCommitCheckerConfig = svnCommitCheckerConfig;
    }

    @Override
    public void reloadConfig()
    {
        CheckConfig.loadProperties(CheckConfig.args);

        int cirecleTime = CheckConfig.getMapCircleCheckTime();
        int laterPackageTime = CheckConfig.getMapLaterCheckTime();
        int errorCheckTime = CheckConfig.getMapErrorCheckTime();

        svnCommitCheckerConfig.update(cirecleTime, laterPackageTime, errorCheckTime);

        BPLog.BP_SVN.info("重新加载配置完毕, 当前 circleTime:{}, laterCheckTime:{}, errorCheckTime:{}", cirecleTime, laterPackageTime, errorCheckTime);
    }

    @Override
    public int getCircleCheckTime()
    {
        return svnCommitCheckerConfig.getCircleCheckTime();
    }

    @Override
    public int getLaterCheckTime()
    {
        return svnCommitCheckerConfig.getLaterCheckTime();
    }

    @Override
    public int getErrorCheckTime()
    {
        return svnCommitCheckerConfig.getErrorCheckTime();
    }
}

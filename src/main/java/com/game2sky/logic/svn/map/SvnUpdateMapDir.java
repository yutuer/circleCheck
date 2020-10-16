package com.game2sky.logic.svn.map;

import com.game2sky.CheckConfig;
import com.game2sky.application.log.BPLog;
import com.game2sky.logic.ILogicUnit;
import com.game2sky.logic.LogicResult;

/**
 * @Description 地图更新任务(客户端, 服务器 地图资源)
 * @Author zhangfan
 * @Date 2020/7/28 11:35
 * @Version 1.0
 */
public class SvnUpdateMapDir implements ILogicUnit
{
    @Override
    public LogicResult doLogic()
    {
        // 更新服务器地图目录
        LogicResult serverLogicResult = new SvnUpdateServerMapDir().doLogic();

        // 更新客户端地图目录
        LogicResult clientLogicResult = new SvnUpdateClientMapDir().doLogic();

        if (serverLogicResult == null || clientLogicResult == null)
        {
            return null;
        }

        boolean serverMapHasUpdate = serverLogicResult.hasBoolVal() && serverLogicResult.getBoolVal();
        if (serverMapHasUpdate)
        {
            BPLog.BP_SVN.info("服务器地图资源文件有更新, path:" + CheckConfig.getSvnServerMapResourcePath());
        }

        boolean clientMapHasUpdate = clientLogicResult.hasBoolVal() && clientLogicResult.getBoolVal();
        if (clientMapHasUpdate)
        {
            BPLog.BP_SVN.info("客户端地图资源文件有更新, path:" + CheckConfig.getSvnClientMapResourcePath());
        }

        return new LogicResult().setBoolVal(serverMapHasUpdate || clientMapHasUpdate);
    }
}

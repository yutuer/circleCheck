package com.game2sky.logic.svn.map;

import com.game2sky.CheckConfig;
import com.game2sky.application.log.BPLog;
import com.game2sky.logic.ILogicUnit;
import com.game2sky.logic.LogicResult;
import com.game2sky.svn.SvnOperaLocalUtil;

/**
 * @Description 更新服务器地图资源目录
 * @Author zhangfan
 * @Date 2020/7/27 18:31
 * @Version 1.0
 */
public class SvnUpdateServerMapDir implements ILogicUnit
{
    @Override
    public LogicResult doLogic()
    {
        boolean hasUpdate = SvnOperaLocalUtil.hasUpdate(CheckConfig.getSvnServerMapResourcePath(), CheckConfig.getSvnServerMapResourceRemotePath());

        BPLog.BP_SVN.info("更新目录:{} 完毕, 更新结果 [{}]", CheckConfig.getSvnServerMapResourcePath(), hasUpdate);

        return new LogicResult().setBoolVal(hasUpdate);
    }
}

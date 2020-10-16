package com.game2sky.logic.svn.excel;

import com.game2sky.CheckConfig;
import com.game2sky.application.log.BPLog;
import com.game2sky.logic.ILogicUnit;
import com.game2sky.logic.LogicResult;
import com.game2sky.svn.SvnOperaLocalUtil;

/**
 * @Description 更新mmo.jar, script.jar
 * @Author zhangfan
 * @Date 2020/7/8 15:38
 * @Version 1.0
 */
public class SvnUpdateMMOJar implements ILogicUnit
{

    @Override
    public LogicResult doLogic()
    {
        boolean hasUpdate = SvnOperaLocalUtil.hasUpdate(CheckConfig.getSvnMMOJarPath(), CheckConfig.getSvnMMOJarRemotePath());

        BPLog.BP_SVN.info("更新目录:{} 完毕, 更新结果 [{}]", CheckConfig.getSvnMMOJarPath(), hasUpdate);

        return new LogicResult().setBoolVal(hasUpdate);
    }
}

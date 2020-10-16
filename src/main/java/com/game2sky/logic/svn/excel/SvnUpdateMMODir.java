package com.game2sky.logic.svn.excel;

import com.game2sky.CheckConfig;
import com.game2sky.application.log.BPLog;
import com.game2sky.logic.ILogicUnit;
import com.game2sky.logic.LogicResult;
import com.game2sky.svn.SvnOperaLocalUtil;

/**
 * @Description 更新mmo目录
 * @Author zhangfan
 * @Date 2020/7/8 17:37
 * @Version 1.0
 */
public class SvnUpdateMMODir implements ILogicUnit
{
    @Override
    public LogicResult doLogic()
    {
        boolean hasUpdate = SvnOperaLocalUtil.hasUpdate(CheckConfig.getSvnMMOPath(), CheckConfig.getSvnMMORemotePath());

        BPLog.BP_SVN.info("更新目录:{} 完毕, 更新结果 [{}]", CheckConfig.getSvnMMOPath(), hasUpdate);

        return new LogicResult().setBoolVal(hasUpdate);
    }
}

package com.game2sky.logic.svn.excel;

import com.game2sky.CheckConfig;
import com.game2sky.application.log.BPLog;
import com.game2sky.logic.ILogicUnit;
import com.game2sky.logic.LogicResult;
import com.game2sky.svn.SvnOperaLocalUtil;

/**
 * @Description 更新excel目录
 * @Author zhangfan
 * @Date 2020/7/8 15:41
 * @Version 1.0
 */
public class SvnUpdateExcelDir implements ILogicUnit
{
    @Override
    public LogicResult doLogic()
    {
        boolean hasUpdate = SvnOperaLocalUtil.hasUpdate(CheckConfig.getSvnExcelPath(), CheckConfig.getSvnExcelRemotePath());

        BPLog.BP_SVN.info("更新目录:{} 完毕, 更新结果 [{}]", CheckConfig.getSvnExcelPath(), hasUpdate);

        return new LogicResult().setBoolVal(hasUpdate);
    }
}

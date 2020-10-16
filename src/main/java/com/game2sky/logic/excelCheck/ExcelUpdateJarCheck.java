package com.game2sky.logic.excelCheck;

import com.game2sky.CheckConfig;
import com.game2sky.application.log.BPLog;
import com.game2sky.logic.svn.excel.SvnUpdateExcelDir;
import com.game2sky.logic.svn.excel.SvnUpdateMMOJar;
import com.game2sky.logic.svn.excel.SvnUpdateScriptJar;
import com.game2sky.verZf.Bootstrap;

import java.io.IOException;

/**
 * @Description 更新表, mmo.jar, script.jar 后执行excel检查, 报错后发送邮件
 * @Author zhangfan
 * @Date 2020/7/8 15:46
 * @Version 1.0
 */
public class ExcelUpdateJarCheck
{
    public int check()
    {
        // 更新操作
        LogicSeqGroup logicSeqQueue = new LogicSeqGroup(new SvnUpdateExcelDir(), new SvnUpdateMMOJar(), new SvnUpdateScriptJar());
        logicSeqQueue.run();

        // 先进行表数据的合并
        try
        {
            BPLog.BP_SVN.info("开始进行表数据的合并");

            new Bootstrap().main(CheckConfig.args);
        }
        catch (IOException e)
        {
            BPLog.BP_SVN.error("合并表数据失败");

            e.printStackTrace();
        }

        ExcelCheck excelCheck = new ExcelCheck();
        excelCheck.doLogic();
        return excelCheck.getErrCount();
    }
}

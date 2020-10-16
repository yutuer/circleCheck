package com.game2sky.tickLogic;

import com.game2sky.logic.LogicResult;
import com.game2sky.logic.excelCheck.ExcelUpdateJarCheck;
import com.game2sky.logic.svn.excel.SvnUpdateExcelDir;
import com.game2sky.task.ITickCheckerLogic;

/**
 * @Description excel 定时触发执行
 * @Author zhangfan
 * @Date 2020/7/28 14:28
 * @Version 1.0
 */
public class ExcelTickCheckLogic implements ITickCheckerLogic
{
    @Override
    public boolean hasUpdate()
    {
        LogicResult logicResult = new SvnUpdateExcelDir().doLogic();
        if (logicResult != null && logicResult.hasBoolVal())
        {
            return logicResult.getBoolVal();
        }
        return false;
    }

    @Override
    public int execCheck()
    {
        int check = new ExcelUpdateJarCheck().check();
        return check;
    }

    @Override
    public String getNameDesc()
    {
        return "[Excel表格]";
    }
}

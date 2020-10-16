package com.game2sky.logic.bat;

import com.game2sky.logic.ILogicUnit;
import com.game2sky.logic.LogicResult;
import com.game2sky.util;

import java.io.IOException;

/**
 * @Description 执行excel bat
 * @Author zhangfan
 * @Date 2020/7/4 11:55
 * @Version 1.0
 */
public class ExcelBatLogic implements ILogicUnit
{

    @Override
    public LogicResult doLogic()
    {
        try
        {
            util.dealExcelBat();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}


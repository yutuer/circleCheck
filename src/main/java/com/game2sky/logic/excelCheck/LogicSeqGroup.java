package com.game2sky.logic.excelCheck;

import com.game2sky.logic.ILogicUnit;

import java.util.Arrays;
import java.util.List;

/**
 * @Description 顺序逻辑执行组
 * @Author zhangfan
 * @Date 2020/7/8 15:22
 * @Version 1.0
 */
public class LogicSeqGroup
{
    /**
     * 顺序运行
     */
    private List<ILogicUnit> logicUnits;

    /**
     * 执行完队列里, 最后的执行逻辑. 防止意义混淆, 放在这里 .通常用来启动下一个运行队列
     */
    private ILogicUnit finallyLogicUnit;

    public LogicSeqGroup(ILogicUnit... logicUnits)
    {
        this.logicUnits = Arrays.asList(logicUnits);
    }

    public void setFinallyLogicUnit(ILogicUnit finallyLogicUnit)
    {
        this.finallyLogicUnit = finallyLogicUnit;
    }

    public void run()
    {
        try
        {
            for (int i = 0, size = logicUnits.size(); i < size; i++)
            {
                try
                {
                    ILogicUnit logicUnit = logicUnits.get(i);
                    logicUnit.doLogic();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        finally
        {
            if (finallyLogicUnit != null)
            {
                finallyLogicUnit.doLogic();
            }
        }
    }


}

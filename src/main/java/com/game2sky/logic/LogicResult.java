package com.game2sky.logic;

/**
 * @Description 返回结果
 * @Author zhangfan
 * @Date 2020/7/8 16:12
 * @Version 1.0
 */
public class LogicResult
{

    private boolean hasInt;

    private int intVal;

    private boolean hasBool;
    private boolean boolVal;

    public boolean isHasInt()
    {
        return hasInt;
    }

    public int getIntVal()
    {
        return intVal;
    }

    public LogicResult setIntVal(int intVal)
    {
        this.intVal = intVal;
        hasInt = true;
        return this;
    }

    public boolean hasBoolVal()
    {
        return hasBool;
    }

    public boolean getBoolVal()
    {
        return boolVal;
    }

    public LogicResult setBoolVal(boolean boolVal)
    {
        this.boolVal = boolVal;
        hasBool = true;
        return this;
    }
}

package com.game2sky.logic.mapResourceCheck.mapCheck;

/**
 * @Description 溢出异常
 * @Author zhangfan
 * @Date 2020/8/22 11:57
 * @Version 1.0
 */
public class OverflowException extends RuntimeException
{
    public OverflowException(String errMsg)
    {
        super(errMsg);
    }
}

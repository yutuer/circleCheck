package com.game2sky;

/**
 * @Description svn检查异常. 这个异常抓住后, 放入到错误处理中
 *              其他都用error来走
 *
 * @Author zhangfan
 * @Date 2020/7/27 20:28
 * @Version 1.0
 */
public class SVNCheckException extends Exception
{
    public SVNCheckException(String message)
    {
        super(message);
    }
}

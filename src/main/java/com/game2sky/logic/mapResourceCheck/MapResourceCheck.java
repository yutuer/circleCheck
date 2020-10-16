package com.game2sky.logic.mapResourceCheck;

import com.game2sky.CheckConfig;
import com.game2sky.excelCheck.ErrorSenderUtil;
import com.game2sky.logic.mapResourceCheck.mapCheck.MapResourceCheckFacade;
import com.game2sky.logic.svn.map.SvnUpdateMapDir;

import java.io.IOException;

/**
 * @Description 地图资源检查
 * @Author zhangfan
 * @Date 2020/7/27 18:29
 * @Version 1.0
 */
public class MapResourceCheck
{
    public static final int ErrorCode = 1;

    /**
     * 检查2个目录md5是否有错误
     *
     * @return 错误数量
     */
    public int check()
    {
        // 再次更新
        new SvnUpdateMapDir().doLogic();

        // 设置错误处理器
        ErrorSenderUtil.registerConfigErrorWriter();

        int compare = 0;
        try
        {
            MapResourceCheckFacade mapResourceCheckFacade = new MapResourceCheckFacade(CheckConfig.getSvnServerMapResourcePath(),
                    CheckConfig.getSvnClientMapResourcePath());
            compare = mapResourceCheckFacade.compareResource();
        }
        finally
        {
            int i = 0;
            try
            {
                i = ErrorSenderUtil.finallyRun();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            compare += i;
        }
        return compare;
    }

}

package com.game2sky.logic.bat;

import com.game2sky.util;

import java.io.IOException;

/**
 * @Description 执行excel bat
 * @Author zhangfan
 * @Date 2020/7/4 11:55
 * @Version 1.0
 */
public class MMOPackagelBatLogic
{
    public MMOPackagelBatLogic(String targetDir)
    {
    }

    protected void call() throws IOException, InterruptedException
    {
        util.packageMMOJarBat();
    }
}


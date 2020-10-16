package com.game2sky.logic.mapResourceCheck.mapCheck;

import com.game2sky.CheckConfig;
import com.game2sky.application.log.BPLog;

/**
 * @Description 给策划使用的地图校验工具启动类
 * @Author zhangfan
 * @Date 2020/8/24 11:47
 * @Version 1.0
 */
public class MapCheckForCeHua
{
    public static void main(String[] args)
    {
        CheckConfig.loadProperties(args);

        MapResourceCheckFacade mapResourceCheckFacade = new MapResourceCheckFacade(CheckConfig.getSvnServerMapResourcePath(),
                CheckConfig.getSvnClientMapResourcePath());
        int compare = mapResourceCheckFacade.compareResource();
        BPLog.BP_LOGIC.info("compare: {}", compare);
    }
}

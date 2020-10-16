package com.game2sky.logic.mapResourceCheck.mapCheck;

import com.game2sky.SVNCheckException;
import com.game2sky.application.log.BPLog;

import java.io.File;

/**
 * @Description 地图校验门面类
 * @Author zhangfan
 * @Date 2020/8/22 11:00
 * @Version 1.0
 */
public class MapResourceCheckFacade
{
    /**
     * 服务器地图目录
     */
    private String serverMapDirPath;

    /**
     * 客户端地图目录
     */
    private String clientMapDirPath;

    public MapResourceCheckFacade(String serverMapDirPath, String clientMapDirPath)
    {
        this.serverMapDirPath = serverMapDirPath;
        this.clientMapDirPath = clientMapDirPath;
    }

    /**
     * 开始比较
     *
     * @return 错误数量
     */
    public int compareResource()
    {
        ServerLevelOneDirRule serverLevelOneDirRule = new ServerLevelOneDirRule(new File(serverMapDirPath));
        serverLevelOneDirRule.listServerMapDir();

        ClientLevelOneDirRule clientLevelOneDirRule = new ClientLevelOneDirRule(new File(clientMapDirPath), serverLevelOneDirRule);
        clientLevelOneDirRule.listClientMapDir();

        int compare = 0;
        File[] dirs = serverLevelOneDirRule.getDirs();

        for (int i = 0, size = dirs.length; i < size; i++)
        {
            File serverDir = dirs[i];
            String dirName = serverDir.getName();

            File clientDir = clientLevelOneDirRule.getClientDirMap().get(dirName);

            ServerLevelTwoDirRule serverLevelTwoDirRule = new ServerLevelTwoDirRule(dirName, serverDir);
            ClientLevelTwoDirRule clientLevelTwoDirRule = new ClientLevelTwoDirRule(dirName, clientDir, serverLevelTwoDirRule);

            try
            {
                clientLevelTwoDirRule.extractFiles();

                compare += serverLevelTwoDirRule.compare(clientLevelTwoDirRule);
            }
            catch (SVNCheckException e)
            {
                BPLog.BP_LOGIC.error(e.getMessage());

                e.printStackTrace();
            }
        }
        return compare;
    }

}

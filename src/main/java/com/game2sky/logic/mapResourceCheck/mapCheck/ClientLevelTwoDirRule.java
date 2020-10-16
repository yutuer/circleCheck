package com.game2sky.logic.mapResourceCheck.mapCheck;

import com.game2sky.SVNCheckException;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * @Description 客户端二级目录规则
 * @Author zhangfan
 * @Date 2020/7/27 20:44
 * @Version 1.0
 */
public class ClientLevelTwoDirRule extends DirRule
{

    private static final String DataDir = "Data";

    /**
     * 客户端导航文件后缀名
     */
    public static final String NAV_SUFFIX = "_nav_b.bytes";

    /**
     * 服务器的目录规则(已经提取好了的. 使用服务器的去找客户端的)
     */
    private ServerLevelTwoDirRule serverDirRule;

    public ClientLevelTwoDirRule(String dirName, File dir, ServerLevelTwoDirRule serverDirRule)
    {
        super(dirName, dir);
        this.serverDirRule = serverDirRule;
    }

    @Override
    protected void extractFiles() throws SVNCheckException
    {
        if (this.dirName == null || !this.dirName.equals(serverDirRule.dirName))
        {
            return;
        }

        if (dir == null || !dir.isDirectory())
        {
            return;
        }

        // 客户端地图文件是放在Data目录下的
        File dataDir = new File(dir.getAbsolutePath() + File.separator + DataDir);
        if (!dataDir.exists() || !dataDir.isDirectory())
        {
            throw new SVNCheckException(String.format("dir: [ %s ]里面没有 [ %s ] 目录夹", dir.getAbsolutePath(), DataDir));
        }

        // 如果没有提取过, 先提取次
        if (!serverDirRule.isExtract())
        {
            serverDirRule.extractFiles();
        }

        final Map<String, File> branchMaps = serverDirRule.branchMaps;

        File[] files = dataDir.listFiles();
        for (int i = 0, size = files.length; i < size; i++)
        {
            File file = files[i];
            if (file.getName().endsWith(NAV_SUFFIX))
            {
                // 客户端的父目录Nav命名规则和服务器的一样
                if (file.getName().equals(serverDirRule.mainFileName))
                {
                    // 找到父目录的nav文件了
                    mainFile = file;
                }
                else
                {
                    if (branchMaps.isEmpty())
                    {
                        continue;
                    }

                    Set<String> keySet = branchMaps.keySet();
                    for (String s : keySet)
                    {
                        // 客户端子目录Nav命名规则  父目录名_子目录名_nav.txt
                        String fileName = dir.getName() + "_" + s + NAV_SUFFIX;
                        if (file.getName().equals(fileName))
                        {
                            // 找到客户端的子目录nav文件
                            this.branchMaps.put(s, file);
                        }
                    }
                }
            }
        }
    }
}
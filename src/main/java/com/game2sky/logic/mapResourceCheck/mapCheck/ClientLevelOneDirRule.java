package com.game2sky.logic.mapResourceCheck.mapCheck;

import com.game2sky.logic.mapResourceCheck.MapResourceCheck;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;

/**
 * @Description 客户端一级目录规则
 * @Author zhangfan
 * @Date 2020/7/28 10:22
 * @Version 1.0
 */
public class ClientLevelOneDirRule
{

    /**
     * 客户端顶级地图目录
     */
    private File dir;

    /**
     * 服务器一级目录规则
     */
    private ServerLevelOneDirRule serverLevelOneDirRule;

    /**
     * 客户端一级目录
     */
    private HashMap<String, File> clientDirMap;

    /**
     * 使用服务器的目录做参考
     *
     * @param serverLevelOneDirRule
     */
    public ClientLevelOneDirRule(File dir, ServerLevelOneDirRule serverLevelOneDirRule)
    {
        this.dir = dir;
        this.serverLevelOneDirRule = serverLevelOneDirRule;
    }

    /**
     * 获得客户端的地图文件夹
     *
     * @return
     */
    public int listClientMapDir()
    {
        if (dir == null || !dir.exists())
        {
            return MapResourceCheck.ErrorCode;
        }

        if (!dir.isDirectory())
        {
            return MapResourceCheck.ErrorCode;
        }

        // 根据服务器的目录, 来决定客户端的目录
        final File[] serverDirs = serverLevelOneDirRule.getDirs();
        File[] files = dir.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File file)
            {
                // 过滤掉svn目录
                if (file.getName().endsWith(".svn"))
                {
                    return false;
                }

                if (!file.isDirectory())
                {
                    return false;
                }

                if (contains(serverDirs, file))
                {
                    return true;
                }

                return false;
            }
        });

        // 统计到map
        HashMap<String, File> clientDirMap = new HashMap<>();
        for (int i = 0, size = files.length; i < size; i++)
        {
            File file = files[i];
            clientDirMap.put(file.getName(), file);
        }

        // 设置
        this.clientDirMap = clientDirMap;
        return 0;
    }

    private String getDirLastName(File file)
    {
        if (file == null || !file.exists() || !file.isDirectory())
        {
            return null;
        }

        String name = file.getName();
        return name;
    }

    /**
     * files是否名称包含file
     *
     * @param files 已知都是dir了.
     * @param file  必须是dir
     * @return
     */
    private boolean contains(File[] files, File file)
    {
        if (file == null || !file.exists())
        {
            return false;
        }

        if (!file.isDirectory())
        {
            return false;
        }

        String fileDirLastName = getDirLastName(file);
        if (fileDirLastName == null)
        {
            return false;
        }

        for (int i = 0, size = files.length; i < size; i++)
        {
            File f = files[i];

            String fDirLastName = getDirLastName(f);
            if (fDirLastName == null)
            {
                continue;
            }

            if (fileDirLastName.equals(fDirLastName))
            {
                return true;
            }
        }
        return false;
    }

    public HashMap<String, File> getClientDirMap()
    {
        return clientDirMap;
    }
}

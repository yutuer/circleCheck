package com.game2sky.logic.mapResourceCheck.mapCheck;

import com.game2sky.logic.mapResourceCheck.MapResourceCheck;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 * @Description 一级目录规则
 * @Author zhangfan
 * @Date 2020/7/27 21:45
 * @Version 1.0
 */
public class ServerLevelOneDirRule
{
    private File dir;

    /**
     * 选择出的服务器的地图目录结构
     */
    private File[] dirs;

    public ServerLevelOneDirRule(File dir)
    {
        this.dir = dir;
    }

    /**
     * 获得服务器的地图文件夹
     *
     * @return
     */
    public int listServerMapDir()
    {
        if (dir == null || !dir.exists())
        {
            return MapResourceCheck.ErrorCode;
        }

        if (!dir.isDirectory())
        {
            return MapResourceCheck.ErrorCode;
        }

        dirs = dir.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File file)
            {
                // 过滤掉svn目录
                if (file.getName().endsWith(".svn"))
                {
                    return false;
                }

                if (file.isDirectory())
                {
                    return true;
                }
                return false;
            }
        });

        return 0;
    }

    public File[] getDirs()
    {
        return dirs;
    }
}

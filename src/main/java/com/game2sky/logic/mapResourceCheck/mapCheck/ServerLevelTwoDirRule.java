package com.game2sky.logic.mapResourceCheck.mapCheck;

import com.game2sky.SVNCheckException;

import java.io.File;

/**
 * @Description 服务器二级目录规则
 * @Author zhangfan
 * @Date 2020/7/27 20:44
 * @Version 1.0
 */
public class ServerLevelTwoDirRule extends DirRule
{
    /**
     * 主文件名称
     */
    String mainFileName;

    /**
     * 提取状态标识, 可以支持重复提取
     */
    private boolean isExtract = false;

    /**
     * 服务器导航文件后缀名
     */
    public static final String NAV_SUFFIX = "_nav.txt";

    public ServerLevelTwoDirRule(String dirName, File dir)
    {
        super(dirName, dir);
    }

    @Override
    protected void extractFiles() throws SVNCheckException
    {
        if (dir == null || !dir.exists() || !dir.isDirectory())
        {
            return;
        }

        File[] files = dir.listFiles();
        for (int i = 0, size = files.length; i < size; i++)
        {
            File file = files[i];
            if (file.isDirectory())
            {
                String serverDirNavFile = getServerDirNavFile(file);
                branchMaps.put(file.getName(), new File(file.getAbsolutePath() + File.separator + serverDirNavFile));
            }
            else
            {
                if (file.getName().endsWith(NAV_SUFFIX))
                {
                    //校验下文件名称
                    if (!file.getName().equals(dir.getName() + NAV_SUFFIX))
                    {
                        throw new RuntimeException("mainFile文件名称错误");
                    }

                    mainFile = file;
                    mainFileName = file.getName();
                }
            }
        }

        isExtract = true;
    }

    /**
     * 获取服务器的nav规则文件
     *
     * @param dir
     * @return
     */
    private String getServerDirNavFile(File dir)
    {
        if (dir == null || !dir.exists())
        {
            throw new RuntimeException();
        }

        if (!dir.isDirectory())
        {
            throw new RuntimeException("dir:" + dir.getPath() + "不是目录");
        }

        String name = dir.getName();
        return name + NAV_SUFFIX;
    }

    public boolean isExtract()
    {
        return isExtract;
    }
}

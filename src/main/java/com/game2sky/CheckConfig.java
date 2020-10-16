package com.game2sky;

import com.game2sky.application.log.BPLog;
import com.game2sky.utils.PropertiesUtils;

import java.util.Properties;

/**
 * @Description svn解析常量配置
 * @Author zhangfan
 * @Date 2020/7/3 21:22
 * @Version 1.0
 */
public class CheckConfig
{
    // 查看properties说明..

    private static int circleCheckTime;
    private static int laterCheckTime;
    private static int errorCheckTime;

    private static int mapCircleCheckTime;
    private static int mapLaterCheckTime;
    private static int mapErrorCheckTime;

    /**
     * 脚本目录
     */
    private static String jarDir;
    /**
     * 脚本目录
     */
    private static String batRootDir;
    /**
     * 更新使用
     */
    private static String svnExcelPath;
    private static String svnExcelRemotePath;

    private static String svnProtocalPath;
    private static String svnProtocalRemotePath;

    private static String svnMMOPath;
    private static String svnMMORemotePath;

    private static String svnMMOJarPath;
    private static String svnMMOJarRemotePath;

    private static String svnScriptPath;
    private static String svnScriptRemotePath;

    private static String svnClientMapResourcePath;
    private static String svnClientMapResourceRemotePath;

    private static String svnServerMapResourcePath;
    private static String svnServerMapResourceRemotePath;

    /**
     * 命令行传入的参数, excel反射需要使用
     */
    public static String[] args;

    /**
     * 读取配置
     *
     * @param args
     */
    public static void loadProperties(String[] args)
    {
        // 参数先存起来
        CheckConfig.args = args;

        // 读取配置(写死吧, 太乱了, 6, 7个jar, 再用命令行传入, 要崩溃了)
        final String propertiesFile = "circleCheck.properties";

        Properties properties = PropertiesUtils.getProperties(propertiesFile);
        if (properties == null)
        {
            BPLog.BP_SVN.error("读取配置文件[{}]失败", propertiesFile);
            System.exit(1);
        }

        CheckConfig.setCircleCheckTime(PropertiesUtils.getInt(properties, "circleCheckTime", 10));
        CheckConfig.setLaterCheckTime(PropertiesUtils.getInt(properties, "laterCheckTime", 300));
        CheckConfig.setErrorCheckTime(PropertiesUtils.getInt(properties, "errorCheckTime", 60));

        CheckConfig.setMapCircleCheckTime(PropertiesUtils.getInt(properties, "mapCircleCheckTime", 10));
        CheckConfig.setMapLaterCheckTime(PropertiesUtils.getInt(properties, "mapLaterCheckTime", 300));
        CheckConfig.setMapErrorCheckTime(PropertiesUtils.getInt(properties, "mapErrorCheckTime", 60));

        CheckConfig.setJarDir(PropertiesUtils.getString(properties, "jarDir", "E:/trunk/code/tools/ttt"));

        CheckConfig.setBatRootDir(PropertiesUtils.getString(properties, "batRootDir", "E:/trunk/code/tools/ttt"));

        CheckConfig.setSvnExcelPath(PropertiesUtils.getString(properties, "svnExcelPath", "E:/trunk/code/common/ExcelTool/excel"));
        CheckConfig.setSvnExcelRemotePath(PropertiesUtils.getString(properties, "svnExcelRemotePath", "E:/trunk/code/common/ExcelTool/excel"));


        CheckConfig.setSvnProtocalPath(PropertiesUtils.getString(properties, "svnProtocalPath", "E:/trunk/code/ttt/protocal"));
        CheckConfig.setSvnProtocalRemotePath(PropertiesUtils.getString(properties, "svnProtocalRemotePath", "E:/trunk/code/ttt/protocal"));

        CheckConfig.setSvnMMOPath(PropertiesUtils.getString(properties, "svnMMOPath", "E:/trunk/code/ttt/bp3Server/mmo.core"));
        CheckConfig.setSvnMMORemotePath(PropertiesUtils.getString(properties, "svnMMORemotePath", "E:/trunk/code/ttt/bp3Server/mmo.core"));

        CheckConfig.setSvnMMOJarPath(PropertiesUtils.getString(properties, "svnMMOJarPath", "E:/trunk/code/ttt/bp3Server/mmo.core"));
        CheckConfig.setSvnMMOJarRemotePath(PropertiesUtils.getString(properties, "svnMMOJarRemotePath", "E:/trunk/code/ttt/bp3Server/mmo.core"));

        CheckConfig.setSvnScriptPath(PropertiesUtils.getString(properties, "svnScriptJarPath", "E:/trunk/code/ttt/bp3Server/mmo.core"));
        CheckConfig.setSvnScriptRemotePath(PropertiesUtils.getString(properties, "svnScriptJarRemotePath", "E:/trunk/code/ttt/bp3Server/mmo.core"));

        CheckConfig.setSvnScriptPath(PropertiesUtils.getString(properties, "svnScriptJarPath", "E:/trunk/code/ttt/bp3Server/mmo.core"));
        CheckConfig.setSvnScriptRemotePath(PropertiesUtils.getString(properties, "svnScriptJarRemotePath", "E:/trunk/code/ttt/bp3Server/mmo.core"));

        CheckConfig.setSvnServerMapResourcePath(PropertiesUtils.getString(properties, "svnServerMapResourcePath", "E:/trunk/code/ttt/bp3Server/mmo.core"));
        CheckConfig.setSvnServerMapResourceRemotePath(PropertiesUtils.getString(properties, "svnServerMapResourceRemotePath", "E:/trunk/code/ttt/bp3Server/mmo.core"));

        CheckConfig.setSvnClientMapResourcePath(PropertiesUtils.getString(properties, "svnClientMapResourcePath", "E:/trunk/code/ttt/bp3Server/mmo.core"));
        CheckConfig.setSvnClientMapResourceRemotePath(PropertiesUtils.getString(properties, "svnClientMapResourceRemotePath", "E:/trunk/code/ttt/bp3Server/mmo.core"));

    }

    public static String getSvnExcelPath()
    {
        return svnExcelPath;
    }

    public static void setSvnExcelPath(String svnExcelPath)
    {
        CheckConfig.svnExcelPath = svnExcelPath;
    }

    public static int getCircleCheckTime()
    {
        return circleCheckTime;
    }

    public static void setCircleCheckTime(int circleCheckTime)
    {
        CheckConfig.circleCheckTime = circleCheckTime;
    }

    public static int getLaterCheckTime()
    {
        return laterCheckTime;
    }

    public static void setLaterCheckTime(int laterCheckTime)
    {
        CheckConfig.laterCheckTime = laterCheckTime;
    }

    public static String getJarDir()
    {
        return jarDir;
    }

    public static void setJarDir(String jarDir)
    {
        CheckConfig.jarDir = jarDir;
    }

    public static String getSvnProtocalPath()
    {
        return svnProtocalPath;
    }

    public static void setSvnProtocalPath(String svnProtocalPath)
    {
        CheckConfig.svnProtocalPath = svnProtocalPath;
    }

    public static String getSvnMMOPath()
    {
        return svnMMOPath;
    }

    public static void setSvnMMOPath(String svnMMOPath)
    {
        CheckConfig.svnMMOPath = svnMMOPath;
    }

    public static String getSvnScriptPath()
    {
        return svnScriptPath;
    }

    public static void setSvnScriptPath(String svnScriptPath)
    {
        CheckConfig.svnScriptPath = svnScriptPath;
    }

    public static String getSvnMMOJarPath()
    {
        return svnMMOJarPath;
    }

    public static void setSvnMMOJarPath(String svnMMOJarPath)
    {
        CheckConfig.svnMMOJarPath = svnMMOJarPath;
    }

    public static int getErrorCheckTime()
    {
        return errorCheckTime;
    }

    public static void setErrorCheckTime(int errorCheckTime)
    {
        CheckConfig.errorCheckTime = errorCheckTime;
    }

    public static String getSvnExcelRemotePath()
    {
        return svnExcelRemotePath;
    }

    public static void setSvnExcelRemotePath(String svnExcelRemotePath)
    {
        CheckConfig.svnExcelRemotePath = svnExcelRemotePath;
    }

    public static String getSvnProtocalRemotePath()
    {
        return svnProtocalRemotePath;
    }

    public static void setSvnProtocalRemotePath(String svnProtocalRemotePath)
    {
        CheckConfig.svnProtocalRemotePath = svnProtocalRemotePath;
    }

    public static String getSvnMMORemotePath()
    {
        return svnMMORemotePath;
    }

    public static void setSvnMMORemotePath(String svnMMORemotePath)
    {
        CheckConfig.svnMMORemotePath = svnMMORemotePath;
    }

    public static String getSvnMMOJarRemotePath()
    {
        return svnMMOJarRemotePath;
    }

    public static void setSvnMMOJarRemotePath(String svnMMOJarRemotePath)
    {
        CheckConfig.svnMMOJarRemotePath = svnMMOJarRemotePath;
    }

    public static String getSvnScriptRemotePath()
    {
        return svnScriptRemotePath;
    }

    public static void setSvnScriptRemotePath(String svnScriptRemotePath)
    {
        CheckConfig.svnScriptRemotePath = svnScriptRemotePath;
    }

    public static String getBatRootDir()
    {
        return batRootDir;
    }

    public static void setBatRootDir(String batRootDir)
    {
        CheckConfig.batRootDir = batRootDir;
    }

    public static String getSvnClientMapResourcePath()
    {
        return svnClientMapResourcePath;
    }

    public static void setSvnClientMapResourcePath(String svnClientMapResourcePath)
    {
        CheckConfig.svnClientMapResourcePath = svnClientMapResourcePath;
    }

    public static String getSvnClientMapResourceRemotePath()
    {
        return svnClientMapResourceRemotePath;
    }

    public static void setSvnClientMapResourceRemotePath(String svnClientMapResourceRemotePath)
    {
        CheckConfig.svnClientMapResourceRemotePath = svnClientMapResourceRemotePath;
    }

    public static String getSvnServerMapResourcePath()
    {
        return svnServerMapResourcePath;
    }

    public static void setSvnServerMapResourcePath(String svnServerMapResourcePath)
    {
        CheckConfig.svnServerMapResourcePath = svnServerMapResourcePath;
    }

    public static String getSvnServerMapResourceRemotePath()
    {
        return svnServerMapResourceRemotePath;
    }

    public static void setSvnServerMapResourceRemotePath(String svnServerMapResourceRemotePath)
    {
        CheckConfig.svnServerMapResourceRemotePath = svnServerMapResourceRemotePath;
    }

    public static int getMapCircleCheckTime()
    {
        return mapCircleCheckTime;
    }

    public static void setMapCircleCheckTime(int mapCircleCheckTime)
    {
        CheckConfig.mapCircleCheckTime = mapCircleCheckTime;
    }

    public static int getMapLaterCheckTime()
    {
        return mapLaterCheckTime;
    }

    public static void setMapLaterCheckTime(int mapLaterCheckTime)
    {
        CheckConfig.mapLaterCheckTime = mapLaterCheckTime;
    }

    public static int getMapErrorCheckTime()
    {
        return mapErrorCheckTime;
    }

    public static void setMapErrorCheckTime(int mapErrorCheckTime)
    {
        CheckConfig.mapErrorCheckTime = mapErrorCheckTime;
    }
}

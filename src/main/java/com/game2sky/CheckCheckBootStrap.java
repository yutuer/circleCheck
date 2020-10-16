package com.game2sky;

import com.game2sky.svn.SvnCommitChecker;
import com.game2sky.svn.config.ExcelReloadConfig;
import com.game2sky.svn.config.MapReloadConfig;
import com.game2sky.svn.config.SvnCommitCheckerConfig;
import com.game2sky.task.TaskContext;
import com.game2sky.tickLogic.ExcelTickCheckLogic;
import com.game2sky.tickLogic.MapTickCheckLogic;

/**
 * @Description 启动器.
 * @Author zhangfan
 * @Date 2020/7/2 21:38
 * @Version 1.0
 */
public class CheckCheckBootStrap
{

    public static void main(String[] args)
    {
        // 读取配置
        CheckConfig.loadProperties(args);

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            util.sendMail("定时检查表服务挂掉了, 处理处理处理!!", "zhangfan@game2sky.com", "zhangfan");
        }));

        // 启动svnCheck
        SvnCommitChecker svnCommitChecker = new SvnCommitChecker();

        // 变成这样, 下一步可以使用类似spring的方法由容器反转写在xml里注入了
        final int size = 2;
        TaskContext[] taskContexts = new TaskContext[size];

        // 检查excel
        ExcelReloadConfig excelReloadConfig = new ExcelReloadConfig(new SvnCommitCheckerConfig(CheckConfig.getCircleCheckTime(),
                CheckConfig.getLaterCheckTime(), CheckConfig.getErrorCheckTime()));
        taskContexts[0] = new TaskContext("circleCheckExcelThread", new ExcelTickCheckLogic(), excelReloadConfig);

        // 检查地图数据
        MapReloadConfig mapReloadConfig = new MapReloadConfig(new SvnCommitCheckerConfig(CheckConfig.getMapCircleCheckTime(),
                CheckConfig.getMapLaterCheckTime(), CheckConfig.getMapErrorCheckTime()));
        taskContexts[1] = new TaskContext("circleCheckMapThread", new MapTickCheckLogic(), mapReloadConfig);

        for (int i = 0; i < taskContexts.length; i++)
        {
            svnCommitChecker.executor(taskContexts[i]);
        }
    }
}

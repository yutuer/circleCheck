package com.game2sky.tickLogic;

import com.game2sky.logic.LogicResult;
import com.game2sky.logic.mapResourceCheck.MapResourceCheck;
import com.game2sky.logic.svn.map.SvnUpdateMapDir;
import com.game2sky.task.ITickCheckerLogic;

/**
 * @Description 地图时间检查逻辑
 * @Author zhangfan
 * @Date 2020/7/28 14:38
 * @Version 1.0
 */
public class MapTickCheckLogic implements ITickCheckerLogic
{
    @Override
    public boolean hasUpdate()
    {
        LogicResult logicResult = new SvnUpdateMapDir().doLogic();
        if (logicResult != null && logicResult.hasBoolVal())
        {
            return logicResult.getBoolVal();
        }
        return false;
    }

    @Override
    public int execCheck()
    {
        int check = new MapResourceCheck().check();
        return check;
    }

    @Override
    public String getNameDesc()
    {
        return "[地图资源文件]";
    }
}

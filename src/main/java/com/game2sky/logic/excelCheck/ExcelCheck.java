package com.game2sky.logic.excelCheck;

import com.game2sky.CheckConfig;
import com.game2sky.JarClassLoader;
import com.game2sky.application.log.BPLog;
import com.game2sky.logic.ILogicUnit;
import com.game2sky.logic.LogicResult;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @Description excel检查(准备条件都已经准备好了)
 *
 * 因为需要运行的时候能同时进行mmo.jar和script.jar的更新. 所以使用了自定义的classLoad加载器加载 {@link JarClassLoader}
 *
 * @Author zhangfan
 * @Date 2020/7/8 16:41
 * @Version 1.0
 */
public class ExcelCheck implements ILogicUnit
{

    /**
     * 错误的数量
     */
    private int errCount;

    @Override
    public LogicResult doLogic()
    {
        LogicResult logicResult = new LogicResult().setIntVal(0);

        // 检查操作, 使用新的classLoader
        String jarDirs = CheckConfig.getJarDir();
        String[] split = jarDirs.split(";");

        JarClassLoader jarClassLoader = JarClassLoader.getInstance(split);
        try
        {
            Class<?> c = jarClassLoader.loadClass("com.game2sky.ExcelDataOpera");
            if (c == null)
            {
                BPLog.BP_SVN.error("没有加载到class: [ {} ]", "com.game2sky.ExcelDataOpera");

                return logicResult.setIntVal(1);
            }

            BPLog.BP_SVN.info("check使用的ExcelDataOpera class hashCode:{} , classLoader:{}", c.hashCode(), jarClassLoader);

            Method m = c.getDeclaredMethod("startCheck", String[].class);
            if (m != null && Modifier.isStatic(m.getModifiers()))
            {
                Object rs = m.invoke(null, new Object[]{CheckConfig.args});
                if (rs != null && rs instanceof Integer)
                {
                    errCount = Integer.valueOf(rs.toString());
                }
            }
            else
            {
                BPLog.BP_SVN.error("ExcelDataOpera类 没有找到方法: [ {} ]", "startCheck");
                return logicResult.setIntVal(1);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return logicResult;
    }

    public int getErrCount()
    {
        return errCount;
    }
}

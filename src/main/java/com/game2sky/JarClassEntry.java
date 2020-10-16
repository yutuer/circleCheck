package com.game2sky;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description jar包里的类entry
 * @Author zhangfan
 * @Date 2020/7/22 20:15
 * @Version 1.0
 */
public class JarClassEntry
{
    private String filePath;

    private Map<String, byte[]> classes = new HashMap<>();

    public JarClassEntry(String filePath)
    {
        this.filePath = filePath;
    }

    public void fillData(String className, byte[] b)
    {
        classes.put(className, b);
    }

    /**
     * 根据名称找到类
     *
     * @param className
     * @return
     */
    public byte[] findClassByClassName(String className)
    {
        return classes.get(className);
    }

    public String getFilePath()
    {
        return filePath;
    }
}

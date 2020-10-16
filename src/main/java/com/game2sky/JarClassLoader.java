package com.game2sky;

import com.game2sky.application.log.BPLog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * jar包的classloader
 * 一份的使用AppClassLoader加载,   多份的如 mmo.jar, script.jar 交由JarClassLoader加载
 * 注意覆写 findResource, findResources, 以及findClass方法
 *
 * @Author zhangfan
 * @Date 2020/7/9 18:20
 * @Version 1.0
 */
public class JarClassLoader extends URLClassLoader
{
    /**
     * 当前jar路径集合
     */
    private File[] jarPaths;

    /**
     * 传入jar文件路径, 注意顺序
     *
     * @param jarFiles
     * @return
     */
    public static JarClassLoader getInstance(String... jarFiles)
    {
        List<File> list = new ArrayList<>();
        List<URL> urlList = new ArrayList<>();
        for (String d : jarFiles)
        {
            File file = getJarFile(d);
            if (file != null)
            {
                list.add(file);
            }

            try
            {
                URL url = file.toURL();
                urlList.add(url);
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
        }
        return new JarClassLoader(list.toArray(new File[0]), urlList.toArray(new URL[0]));
    }

    private static File getJarFile(String fileName)
    {
        File file = new File(fileName);
        if (!file.exists())
        {
            BPLog.BP_SVN.error("没有path:" + fileName + " 路径");
            return null;
        }

        if (file.isDirectory())
        {
            BPLog.BP_SVN.error("fileName:" + fileName + " 是目录");
            return null;
        }
        return file;
    }

    private JarClassLoader(File[] jarPaths, URL[] urls)
    {
        super(urls); //parent使用AppClassloader
        this.jarPaths = jarPaths;
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException
    {
        String classNameInJar = className.replaceAll("\\.", "/") + ".class";

        for (File file : jarPaths)
        {
            JarFile jarFile;
            try
            {
                jarFile = new JarFile(file);
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements())
                {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (classNameInJar.equals(name))
                    {
                        try (InputStream input = jarFile.getInputStream(entry); ByteArrayOutputStream baos = new ByteArrayOutputStream())
                        {
                            int bufferSize = 4096;
                            byte[] buffer = new byte[bufferSize];
                            int bytesNumRead = 0;
                            while ((bytesNumRead = input.read(buffer)) != -1)
                            {
                                baos.write(buffer, 0, bytesNumRead);
                            }
                            byte[] cc = baos.toByteArray();
                            return defineClass(className, cc, 0, cc.length);
                        }
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return super.findClass(className);
    }

    @Override
    public URL findResource(String name)
    {
        return super.findResource(name);
    }

    @Override
    public Enumeration<URL> findResources(String name) throws IOException
    {
        Enumeration<URL> resources = super.findResources(name);
        return resources;
    }

    public static void main(String[] args) throws ClassNotFoundException
    {
        {
            JarClassLoader jarClassLoader = JarClassLoader.getInstance(new String[]{"E:\\trunk\\code\\tools\\circleCheck\\src\\main\\resources\\classes\\entityCheckExcelData.jar", "E:\\trunk\\code\\tools\\circleCheck\\src\\main\\resources\\classes\\mmo.core-1.jar"});
            Class<?> aClass = jarClassLoader.loadClass("com.game2sky.ExcelDataOpera");
            System.out.println(aClass.getClassLoader());
            System.out.println(aClass.hashCode());

            aClass = jarClassLoader.loadClass("com.game2sky.application.common.dict.init.ScanUtil");
            System.out.println(aClass.getClassLoader());
            System.out.println(aClass.hashCode());
        }
        {
            JarClassLoader jarClassLoader = JarClassLoader.getInstance(new String[]{"E:\\trunk\\code\\tools\\circleCheck\\src\\main\\resources\\classes\\entityCheckExcelData.jar", "E:\\trunk\\code\\tools\\circleCheck\\src\\main\\resources\\classes\\mmo.core-1.jar"});
            Class<?> aClass = jarClassLoader.loadClass("com.game2sky.ExcelDataOpera");
            System.out.println(aClass.getClassLoader());
            System.out.println(aClass.hashCode());

            aClass = jarClassLoader.loadClass("com.game2sky.application.common.dict.init.ScanUtil");
            System.out.println(aClass.getClassLoader());
            System.out.println(aClass.hashCode());
        }
        {
            JarClassLoader jarClassLoader = JarClassLoader.getInstance(new String[]{"E:\\trunk\\code\\tools\\circleCheck\\src\\main\\resources\\classes\\entityCheckExcelData.jar", "E:\\trunk\\code\\tools\\circleCheck\\src\\main\\resources\\classes\\mmo.core-1.jar"});
            Class<?> aClass = jarClassLoader.loadClass("com.game2sky.ExcelDataOpera");
            System.out.println(aClass.getClassLoader());
            System.out.println(aClass.hashCode());

            aClass = jarClassLoader.loadClass("com.game2sky.application.common.dict.init.ScanUtil");
            System.out.println(aClass.getClassLoader());
            System.out.println(aClass.hashCode());
        }
//        {
//            JarClassLoader jarClassLoader = JarClassLoader.getInstance("E:\\trunk\\code\\tools\\circleCheck\\src\\main\\resources\\classes\\entityCheckExcelData.jar");
//            URL resource = jarClassLoader.getResource("circleCheck.properties");
//            System.out.println(resource);
//        }
//        {
//            JarClassLoader jarClassLoader = JarClassLoader.getInstance("E:\\trunk\\code\\tools\\circleCheck\\src\\main\\resources\\classes\\entityCheckExcelData.jar");
//            URL resource = jarClassLoader.getResource("log4j2-test.xml");
//            System.out.println(resource);
//        }
    }
}

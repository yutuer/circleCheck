package com.game2sky;

import com.game2sky.application.log.BPLog;
import com.game2sky.excelCheck.mail.MailEntity;
import com.game2sky.excelCheck.mail.MailSender;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author zhangfan
 * @Date 2020/7/3 15:03
 * @Version 1.0
 */
public class util
{

    /**
     * 是否是windows系统
     *
     * @return
     */
    public static boolean isWinOS()
    {
        String os = System.getProperty("os.name"); //会打印 Windows 10 或者 Linux
        if (os.toLowerCase().startsWith("win"))
        {
            return true;
        }
        return false;
    }

    /**
     * 系统执行
     *
     * @param cmd
     * @param dir
     * @throws IOException
     * @throws InterruptedException
     */
    public static void runtimeExec(String cmd, String dir) throws IOException, InterruptedException
    {
        boolean winOS = isWinOS();
        if (winOS)
        {
            Process process = Runtime.getRuntime().exec("cmd /c " + cmd, null, new File(dir));

            // error 不能读取, 因为会导致阻塞
//            try (InputStream inputStream = process.getErrorStream())
//            {
//                int len;
//                byte[] b = new byte[1024 * 1024];
//                while ((len = inputStream.read(b)) != -1)
//                {
//                    BPLog.BP_SVN.error(new String(b, 0, len, Charset.forName("UTF-8")));
//                }
//            }

            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream())))
            {
                String line;
                while ((line = bufferedReader.readLine()) != null)
                {
                    BPLog.BP_SVN.info(line);
                }
            }

            process.waitFor(3, TimeUnit.MINUTES);
        }
    }

    public static void packageMMOJarBat() throws IOException, InterruptedException
    {
        runtimeExec("mvn -B -Dmaven.test.skip=true clean package", CheckConfig.getSvnMMOPath());
    }

    public static void dealExcelBat() throws IOException, InterruptedException
    {
        runtimeExec("excel.bat", CheckConfig.getSvnExcelPath());
    }

    public static void dealProtocalBat() throws IOException, InterruptedException
    {
        runtimeExec("protocal.bat", CheckConfig.getSvnProtocalPath());
    }

    public static void sendMail(String content, String receiverMailAddress, String receiverName)
    {
        MailEntity mailEntity = new MailEntity().setSenderAddr("zhangfan@game2sky.com", "9506068Zf", "smtp.game2sky.com", "zhangfan")
                .setReceiver(receiverMailAddress, receiverName).setTitle("表数据错误").setContent(content);
        try
        {
            MailSender.getInstance().sendMail(mailEntity);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 根据单位显示文字
     *
     * @param timeUnit
     * @return
     */
    public static String showSecondDesc(TimeUnit timeUnit)
    {
        switch (timeUnit)
        {
            case MILLISECONDS:
                return "毫秒";
            case SECONDS:
                return "秒";
            case MINUTES:
                return "分";
            case HOURS:
                return "小时";
            case MICROSECONDS:
                return "微秒";
            case DAYS:
                return "天";
        }
        return "年";
    }
}

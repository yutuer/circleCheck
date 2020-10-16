package com.game2sky;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @Description TODO
 * @Author zhangfan
 * @Date 2020/7/28 11:57
 * @Version 1.0
 */
public class SvnMapCheckUnit
{
    /**
     * 计时Es, 共用一个.
     */
    private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1, (r) ->
    {
        return new Thread(r, "circleCheckMapThread");
    });

}

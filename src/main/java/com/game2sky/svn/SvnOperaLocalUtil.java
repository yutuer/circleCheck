package com.game2sky.svn;

import com.game2sky.application.log.BPLog;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNErrorCode;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNStatus;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description com.game2sky.svn 本地操作util
 * @Author zhangfan
 * @Date 2020/7/3 15:46
 * @Version 1.0
 */
public class SvnOperaLocalUtil
{

    public static final String svnUserName = "zhangfan";
    public static final String svnPassword = "zhangfan1";

    // svn权限类唯一
    private static Map<String, SVNClientManager> SVNClientManagers = new HashMap<>();

    /**
     * 根据远程地址获取SvnClientManager
     *
     * @param remoteSvnHost
     * @return
     */
    public static SVNClientManager getSVNClientManager(String remoteSvnHost)
    {
        synchronized (SvnOperaLocalUtil.class)
        {
            SVNClientManager svnClientManager = SVNClientManagers.get(remoteSvnHost);
            if (svnClientManager == null)
            {
                svnClientManager = SvnOperaUtil.authSvn(remoteSvnHost, svnUserName, svnPassword);
                if (svnClientManager != null)
                {
                    SVNClientManagers.put(remoteSvnHost, svnClientManager);
                    return svnClientManager;
                }
            }
            return svnClientManager;
        }
    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    public static long getSvnVersion(String path, String remoteSvnHost)
    {
        SVNClientManager svnClientManager = getSVNClientManager(remoteSvnHost);
        if (svnClientManager != null)
        {
            final File wcFile = new File(path);

            SVNStatus svnStatus = SvnOperaUtil.showStatus(svnClientManager, wcFile, false);
            if (svnStatus != null)
            {
//                SVNRevision revision = svnStatus.getRevision();
                SVNRevision revision = svnStatus.getCommittedRevision();
                if (revision != null)
                {
                    return revision.getNumber();
                }
            }
        }
        return 0;
    }

    /**
     * 是否有更新
     *
     * @return
     */
    public static boolean hasUpdate(long oldVersion, long version)
    {
        return version - oldVersion > 0;
    }

    /**
     * 是否有更新
     *
     * @return
     */
    public static boolean hasUpdate(String path, String remoteSvnHost)
    {
        long oldSvnVersion = getSvnVersion(path, remoteSvnHost);
        BPLog.BP_SVN.info("path: [ {} ] 更新之前的此目录最后修改版本号: {}", path, oldSvnVersion);

        long version = updateSvn(path, remoteSvnHost);
        BPLog.BP_SVN.info("path: [ {} ] 更新之后的整体修改版本号: {}", path, version);

        long svnVersion = getSvnVersion(path, remoteSvnHost);
        BPLog.BP_SVN.info("path: [ {} ] 更新之后的此目录最后修改版本号: {}", path, svnVersion);

        return hasUpdate(oldSvnVersion, svnVersion);
    }

    /**
     * 更新指定目录
     */
    public static long updateSvn(String path, String remoteSvnHost)
    {
        SVNClientManager svnClientManager = getSVNClientManager(remoteSvnHost);
        if (svnClientManager != null)
        {
            final File wcFile = new File(path);
            SVNRevision svnRevision = SVNRevision.create(new Date());
            try
            {
                long update = SvnOperaUtil.update(svnClientManager, wcFile, svnRevision, SVNDepth.INFINITY);
                return update;
            }
            catch (SVNException e)
            {
                BPLog.BP_SVN.error(e.getErrorMessage(), e);

                if (e.getErrorMessage().getErrorCode() == SVNErrorCode.WC_LOCKED)
                {
                    //清理目录吧, 应该是被lock了. 全部都是true, 留个最干净的. (有的不知道啥意思, 汗)
                    SvnOperaUtil.cleanup(svnClientManager, wcFile, true, true, true, true, true, true);
                }
            }
        }
        return 0;
    }

    public static void main(String[] args)
    {
        hasUpdate("E:/trunk/code/common/ExcelTool/excel", "http://172.16.1.57/svn/badperson3/trunk/code/common/ExcelTool/excel@HEAD");
    }

}

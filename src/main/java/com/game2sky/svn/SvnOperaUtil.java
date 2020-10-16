package com.game2sky.svn;

import com.game2sky.application.log.BPLog;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;

/**
 * @Description com.game2sky.svn 基础操作 com.game2sky.util
 * @Author zhangfan
 * @Date 2020/7/3 11:06
 * @Version 1.0
 */
public class SvnOperaUtil
{
    /**
     * 通过不同的协议初始化版本库
     */
    public static void setupLibrary()
    {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }

    /**
     * 验证登录svn
     *
     * @Param: svnRoot:svn的根路径
     */
    public static SVNClientManager authSvn(String svnRoot, String username, String password)
    {
        // 初始化版本库
        setupLibrary();

        // 创建库连接
        SVNRepository repository = null;
        try
        {
            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(svnRoot));
        }
        catch (SVNException e)
        {
            BPLog.BP_SVN.error(e.getErrorMessage(), e);
            return null;
        }

        // 身份验证
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);

        // 创建身份验证管理器
        repository.setAuthenticationManager(authManager);

        ISVNOptions options = SVNWCUtil.createDefaultOptions(true);
        SVNClientManager clientManager = SVNClientManager.newInstance(options, authManager);
        return clientManager;
    }

    /**
     * 创建文件夹
     */
    public static SVNCommitInfo makeDirectory(SVNClientManager clientManager, SVNURL url, String commitMessage)
    {
        try
        {
            return clientManager.getCommitClient().doMkDir(new SVNURL[]{url}, commitMessage);
        }
        catch (SVNException e)
        {
            BPLog.BP_SVN.error(e.getErrorMessage(), e);
        }
        return null;
    }

    /**
     * 导入文件夹
     *
     * @Param localPath:本地路径
     * @Param dstURL:目标地址
     */
    public static SVNCommitInfo importDirectory(SVNClientManager clientManager, File localPath, SVNURL dstURL, String commitMessage, boolean isRecursive)
    {
        try
        {
            return clientManager.getCommitClient().doImport(localPath, dstURL, commitMessage, true, true);
        }
        catch (SVNException e)
        {
            BPLog.BP_SVN.error(e.getErrorMessage(), e);
        }
        return null;
    }

    /**
     * 添加入口
     */
    public static void addEntry(SVNClientManager clientManager, File wcPath)
    {
        try
        {
            clientManager.getWCClient().doAdd(wcPath, true, false, false, false, true);
        }
        catch (SVNException e)
        {
            BPLog.BP_SVN.error(e.getErrorMessage(), e);
        }
    }

    /**
     * 显示状态
     */
    public static SVNStatus showStatus(SVNClientManager clientManager, File wcPath, boolean remote)
    {
        SVNStatus status = null;
        try
        {
            status = clientManager.getStatusClient().doStatus(wcPath, remote);
        }
        catch (SVNException e)
        {
            BPLog.BP_SVN.error(e.getErrorMessage(), e);
        }
        return status;
    }

    /**
     * 提交
     *
     * @Param keepLocks:是否保持锁定
     */
    public static SVNCommitInfo commit(SVNClientManager clientManager, File wcPath, boolean keepLocks, String commitMessage)
    {
        try
        {
            return clientManager.getCommitClient().doCommit(new File[]{wcPath}, keepLocks, commitMessage, false, false);
        }
        catch (SVNException e)
        {
            BPLog.BP_SVN.error(e.getErrorMessage(), e);
        }
        return null;
    }

    /**
     * 更新
     *
     * @param depth 深度, 查看类定义的枚举吧.
     * @see SVNDepth
     */
    public static long update(SVNClientManager clientManager, File wcPath, SVNRevision updateToRevision, SVNDepth depth) throws SVNException
    {
        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);

        if (!updateClient.isUpdateLocksOnDemand())
        {
            // 只锁定当前目录
            updateClient.setUpdateLocksOnDemand(true);
        }

        long[] longs = updateClient.doUpdate(new File[]{wcPath}, updateToRevision, depth, false, false);
        if (longs != null && longs.length > 0)
        {
            return longs[0];
        }
        return 0;
    }

    /**
     * 从SVN导出项目到本地
     *
     * @Param url:SVN的url
     * @Param revision:版本
     * @Param destPath:目标路径
     */
    public static long checkout(SVNClientManager clientManager, SVNURL url, SVNRevision revision, File destPath)
    {
        SVNUpdateClient updateClient = clientManager.getUpdateClient();
        updateClient.setIgnoreExternals(false);
        try
        {
            return updateClient.doCheckout(url, destPath, revision, revision, false);
        }
        catch (SVNException e)
        {
            BPLog.BP_SVN.error(e.getErrorMessage(), e);
        }
        return 0;
    }

    /**
     * 清理路径
     *
     * @param clientManager
     * @param wcPath
     * @param deleteWCProperties
     * @return
     */
    public static void cleanup(SVNClientManager clientManager, File wcPath, boolean deleteWCProperties, boolean breakLocks
            , boolean vacuumPristines, boolean removeUnversionedItems, boolean removeIgnoredItems, boolean includeExternals)
    {
        SVNWCClient wcClient = clientManager.getWCClient();
        try
        {
            wcClient.doCleanup(wcPath, deleteWCProperties, breakLocks, vacuumPristines, removeUnversionedItems, removeIgnoredItems, includeExternals);
        }
        catch (SVNException e)
        {
            BPLog.BP_SVN.error(e.getErrorMessage(), e);
        }
    }


    /**
     * 确定path是否是一个工作空间
     */
    public static boolean isWorkingCopy(File path)
    {
        if (!path.exists())
        {
            BPLog.BP_SVN.warn("'" + path + "' not exist!");
            return false;
        }
        try
        {
            if (null == SVNWCUtil.getWorkingCopyRoot(path, false))
            {
                return false;
            }
        }
        catch (SVNException e)
        {
            BPLog.BP_SVN.error(e.getErrorMessage(), e);
        }
        return true;
    }

    /**
     * 确定一个URL在SVN上是否存在
     */
    public static boolean isURLExist(SVNURL url, String username, String password)
    {
        try
        {
            SVNRepository svnRepository = SVNRepositoryFactory.create(url);
            ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
            svnRepository.setAuthenticationManager(authManager);
            SVNNodeKind nodeKind = svnRepository.checkPath("", -1); //遍历SVN,获取结点。
            return nodeKind == SVNNodeKind.NONE ? false : true;
        }
        catch (SVNException e)
        {
            BPLog.BP_SVN.error(e.getErrorMessage(), e);
        }
        return false;
    }
}

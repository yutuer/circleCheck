package com.game2sky.logic.mapResourceCheck.mapCheck;

import com.game2sky.SVNCheckException;
import com.game2sky.application.log.BPLog;
import com.game2sky.logic.mapResourceCheck.MapResourceCheck;

import java.io.*;
import java.util.*;

/**
 * @Description 共有的结构
 * @Author zhangfan
 * @Date 2020/7/27 20:47
 * @Version 1.0
 */
public abstract class DirRule
{
    private static final int ErrorCode = -1;

    /**
     * md5码的行数
     */
    private static final int MD5Line = 3;

    /**
     * 上层目录
     */
    protected File dir;

    /**
     * 目录名称
     */
    protected String dirName;

    /**
     * 子目录(key -> 子目录名称, value 子目录对应的nav文件名称)
     */
    protected Map<String, File> branchMaps = new HashMap<>();

    /**
     * 主文件
     */
    protected File mainFile;

    public DirRule(String dirName, File dir)
    {
        this.dir = dir;
        this.dirName = dirName;
    }

    protected abstract void extractFiles() throws SVNCheckException;

    /**
     * 两个目录工具互相比较
     *
     * @param that
     * @return
     * @throws SVNCheckException
     */
    public int compare(DirRule that) throws SVNCheckException
    {
        // 子目录长度不一样, 就肯定不用比了
        if (this.branchMaps.size() != that.branchMaps.size())
        {
            String notSameSizeErrMsg = getNotSameSizeErrMsg(that);
            BPLog.BP_LOGIC.error("dir:[{}] 和 dir:[{}] 的子目录长度不一致.  msg:{}", this.dir.getAbsolutePath(), that.dir.getAbsolutePath(), notSameSizeErrMsg);
            return MapResourceCheck.ErrorCode;
        }

        checkFileMD5Diff(this.mainFile, that.mainFile);

        Set<String> keySet = branchMaps.keySet();
        for (String key : keySet)
        {
            File thatFile = that.branchMaps.get(key);
            if (thatFile == null)
            {
                BPLog.BP_LOGIC.error("key:{} 在 dir:{} 中不存在", key, that.dir.getAbsolutePath());
            }

            checkFileMD5Diff(this.branchMaps.get(key), thatFile);
        }

        return 0;
    }

    /**
     * 读取txt文件的md5码
     *
     * @param file
     * @return
     * @throws SVNCheckException
     */
    private String readTxtFileMD5(File file) throws SVNCheckException
    {
        String md5;

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            int i = 0;
            while (null != (md5 = br.readLine()))
            {
                if (++i >= MD5Line)
                {
                    break;
                }
            }

            if (i < MD5Line)
            {
                throw new SVNCheckException(String.format("文件%s没有第%d行\n", file.getPath(), MD5Line));
            }

            return md5;
        }
        catch (Exception e)
        {
            throw new SVNCheckException(e.getMessage());
        }
    }

    /**
     * 读取byte文件的md5码
     *
     * @param file
     * @return
     * @throws SVNCheckException
     */
    private static String readByteFileMD5(File file) throws SVNCheckException
    {
        String md5;

        try (DataInputStream dis = new DataInputStream(new FileInputStream(file)))
        {
            byte[] data = new byte[dis.available()];
            dis.read(data);

            BinReader binReader = new BinReader(data);

            binReader.ReadString();
            binReader.ReadLong();
            md5 = binReader.ReadString();

            return md5;
        }
        catch (Exception e)
        {
            throw new SVNCheckException(e.getMessage());
        }
    }

    private String getNotSameSizeErrMsg(DirRule that)
    {
        if (this.branchMaps.size() != that.branchMaps.size())
        {
            HashSet<String> thisSet = new HashSet<>(this.branchMaps.keySet());
            HashSet<String> thatSet = new HashSet<>(that.branchMaps.keySet());

            int thisSize = this.branchMaps.size();
            int thatSize = that.branchMaps.size();

            if (thisSize > thatSize)
            {
                thisSet.removeAll(thatSet);
                return this.dir.getPath() + " 比 " + that.dir.getPath() + " 多出的地图: " + joinString(thisSet);
            }
            else
            {
                thatSet.removeAll(thisSet);
                return that.dir.getPath() + " 比 " + this.dir.getPath() + " 多出的地图: " + joinString(thatSet);
            }
        }
        return "";
    }

    private String joinString(Collection<String> c)
    {
        if (c == null || c.isEmpty())
        {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        if (c instanceof List)
        {
            List list = List.class.cast(c);
            for (int i = 0, size = list.size(); i < size; i++)
            {
                if (i > 0)
                {
                    sb.append(",");
                }
                sb.append(list.get(i));
            }
        }
        else
        {
            int i = 0;
            Iterator<String> iterator = c.iterator();
            while (iterator.hasNext())
            {
                if (i++ > 0)
                {
                    sb.append(",");
                }
                String next = iterator.next();
                sb.append(next);
            }
        }
        return sb.toString();
    }

    /**
     * 返回2个文件的md5码比较结果
     *
     * @param serverFile
     * @param clientFile
     * @return 0 一样 -1 不一样
     */
    private int checkFileMD5Diff(File serverFile, File clientFile) throws SVNCheckException
    {
        if (serverFile == null || clientFile == null)
        {
            return ErrorCode;
        }

        if (!serverFile.exists() || !serverFile.isFile() || !clientFile.exists() || !clientFile.isFile())
        {
            return ErrorCode;
        }

        String md5S = readTxtFileMD5(serverFile);
        // 客户端使用byte的方式
        String md5C = readByteFileMD5(clientFile);

        if (md5S == null || md5C == null)
        {
            return ErrorCode;
        }

        if (!md5S.equals(md5C))
        {
            throw new SVNCheckException(String.format("文件md5不相等:  serverFile(%s):[%s], clientFile(%s):[%s] \n", serverFile.getPath(), md5S, clientFile.getPath(), md5C));
        }
        return 0;
    }

}

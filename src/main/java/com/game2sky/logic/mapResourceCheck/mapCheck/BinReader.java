package com.game2sky.logic.mapResourceCheck.mapCheck;

import java.util.Arrays;

/**
 * @Description 字节读取util.
 *
 * write(v) ->   zv = zigzag(v) ->  wi = writeInt(zv)
 * read(wi) ->   ri = readInt(wi) -> v = zigzag(ri)
 *
 * @Author zhangfan
 * @Date 2020/8/22 11:34
 * @Version 1.0
 */
public class BinReader
{
    private byte[] ReadBuffer;

    private int position;

    public BinReader(byte[] readBuffer)
    {
        ReadBuffer = readBuffer;
        position = 0;
    }

    public int ToZigzag32(int value)
    {
        int v = value;
        // 这句是把 符号位移动到最右边, 如果是负数, 前面的取反
        // 结果就是负数是奇数 负数的正数表示 *2 后-1 *(例如 -1 变成 1 , -2变成3 -4变成7), 正数变成 正数*2 (1变成2, 2变成4, 4变成8)
        return (v << 1) ^ (v >> 31);
    }

    public int FromZigzag32(int v)
    {
        int value = v;
        return (-(value & 0x01)) ^ ((value >> 1) & ~(1 << 31));
    }

    public int ReadInt()
    {
        return FromZigzag32(Readint());
    }

    /**
     * 这里的v 已经是zigzag之后的了
     *
     * @param v
     * @return
     */
    public byte[] Writeint(int v)
    {
        int value = v;

        byte[] data = new byte[5];
        int count = 0;
        do
        {
            //所有字节位先全部使用0x80 填充
            data[count] = (byte) ((value & 0x7F) | 0x80);
            count++;
        } while ((value >>= 7) != 0);

        //最高位字节用0x7F 去掉最高位的0
        data[count - 1] &= 0x7F;

        return Arrays.copyOfRange(data, 0, count);
    }

    private int Readint()
    {
        int value = ReadBuffer[position++];
        if ((value & 0x80) == 0)
        {
            return value;
        }

        value &= 0x7F;
        int chunk = ReadBuffer[position++];
        value |= (chunk & 0x7F) << 7;
        if ((chunk & 0x80) == 0)
        {
            return value;
        }

        chunk = ReadBuffer[position++];
        value |= (chunk & 0x7F) << 14;
        if ((chunk & 0x80) == 0)
        {
            return value;
        }

        chunk = ReadBuffer[position++];
        value |= (chunk & 0x7F) << 21;
        if ((chunk & 0x80) == 0)
        {
            return value;
        }

        // 大部分的压缩读取, 其实在这之前就结束了.
        chunk = ReadBuffer[position++];
        value |= chunk << 28;
        if ((chunk & 0xF0) == 0)
        {
            return value;
        }
        throw new OverflowException("Readint32Variant Error!");
    }

    /**
     * 因为c#的写入方式, 查看 BinSerializer.WriteString(string v)
     * 中文可能会有问题
     *
     * @return
     */
    public String ReadString()
    {
        int len = ReadInt();
        int p = position;

        // 设置读取后的位置
        position += len;

        byte[] bytes = new byte[len / 2];
        for (int i = p, j = 0; i < position; i++)
        {
            byte b = ReadBuffer[i];
            if (b != 0)
            {
                bytes[j++] = b;
            }
        }
        return new String(bytes);
    }

    public void ReadLong()
    {
        // 跳过8个字节. 原来的压缩  BitConverter.ToInt64(ReadBuffer, p) 有点复杂
        position += 8;
    }
}
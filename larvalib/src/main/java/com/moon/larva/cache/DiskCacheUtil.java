package com.moon.larva.cache;

import android.graphics.Bitmap;

import com.moon.larva.bitmap.BitmapUtil;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * author: moon
 * created on: 17/10/2 上午10:42
 * description:
 */
public class DiskCacheUtil {


    /**
     * 从磁盘中加载
     *
     * @param dstWidth
     * @param dstHeight
     * @return
     * @throws IOException
     */
    public static Bitmap loadFromDisk(DiskLruCache  mDiskLruCache,String key, int dstWidth, int dstHeight)
            throws
            IOException {

        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (snapshot != null) {
            FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(0);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            return BitmapUtil.decodeSampledBitmapFromFD(fileDescriptor, dstWidth, dstHeight);
        }

        return null;
    }

    /**
     * 从磁盘中加载
     *
     * @return
     * @throws IOException
     */
    public static Bitmap loadFromDisk(DiskLruCache  mDiskLruCache,String key)
            throws
            IOException {

        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (snapshot != null) {
            FileInputStream fileInputStream = (FileInputStream) snapshot.getInputStream(0);
            FileDescriptor fileDescriptor = fileInputStream.getFD();
            return BitmapUtil.decodeSampledBitmapFromFD(fileDescriptor);
        }

        return null;
    }
}

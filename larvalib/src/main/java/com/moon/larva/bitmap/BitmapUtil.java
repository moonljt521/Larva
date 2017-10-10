package com.moon.larva.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;
import java.io.InputStream;

/**
 * author: moon
 * created on: 17/10/1 上午11:26
 * description:
 */
public class BitmapUtil {

    public static Bitmap decodeSampledBitmapFromFD(FileDescriptor fd) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calInSampleSize();
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    public static Bitmap decodeSampledBitmapFromFD(FileDescriptor fd, int dstWidth, int dstHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = calInSampleSize(options, dstWidth, dstHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    /**
     * 计算采样率
     * @param options
     * @param dstWidth imageView的宽（高）
     * @param dstHeight
     * @return inSampleSize
     */
    private static int calInSampleSize(BitmapFactory.Options options, int dstWidth, int dstHeight) {
        int rawWidth = options.outWidth;  // 图片实际宽（高）
        int rawHeight = options.outHeight;
        int inSampleSize = 1;
        if (rawWidth > dstWidth || rawHeight > dstHeight) {
            float ratioHeight = (float) rawHeight / dstHeight;
            float ratioWidth = (float) rawWidth / dstHeight;
            inSampleSize = (int) Math.min(ratioWidth, ratioHeight);
        }
        return inSampleSize;
    }


    /**
     * 计算采样率
     * @return inSampleSize
     */
    private static int calInSampleSize() {
        return 2;
    }
}

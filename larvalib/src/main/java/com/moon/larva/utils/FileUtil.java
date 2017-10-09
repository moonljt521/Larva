package com.moon.larva.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.LruCache;

import java.io.File;

/**
 * author: moon
 * created on: 17/10/1 上午11:07
 * description:  缓存工具类
 */
public class FileUtil {

    /**
     * 获取缓存目录
     * @param context
     * @param dirName
     * @return
     */
    public static File getAppCacheDir(Context context, String dirName) {
        String cacheDirString;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            cacheDirString = context.getExternalCacheDir().getPath();
        } else {
            cacheDirString = context.getCacheDir().getPath();
        }
        return new File(cacheDirString + File.separator + dirName);
    }


    /**
     * 取出内存缓存
     * @param key
     * @return
     */
    public static Bitmap getFromMemoryCache(LruCache<String,Bitmap> mMemoryCache, String key) {
        return mMemoryCache.get(key);
    }
}

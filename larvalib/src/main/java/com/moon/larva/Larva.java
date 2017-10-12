package com.moon.larva;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import com.moon.larva.cache.DiskCacheUtil;
import com.moon.larva.cache.DiskLruCache;
import com.moon.larva.cache.MemoryCacheUtil;
import com.moon.larva.cache.NetCacheUtil;
import com.moon.larva.request.Result;
import com.moon.larva.utils.FileUtil;
import com.moon.larva.utils.UDebug;
import com.moon.larva.utils.Util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author: moon
 * created on: 17/10/1 上午10:42
 * description:
 */
public class Larva {

    private Larva(){

    }

    /**
     *
     * @param context
     * @return
     */
    public static LarvaManager with(Context context) {
        return LarvaManager.getLarvaManager(context);
    }





}

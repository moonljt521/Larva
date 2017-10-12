package com.moon.larva;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
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
 * created on: 17/10/1 下午7:28
 * description:  larva管理器，全面处理请求和传入的参数，
 */
public class LarvaManager {

    private String mUrl ;

    private int width,height;

    private static volatile LarvaManager sLarvaManager ;

    private static final int MESSAGE_SEND_RESULT = 1;

    private Handler mMainHandler = new MainHandler(Looper.getMainLooper());

    private static final int DISK_APP_VERSION = 1;  // diskCache 版本号，如果发生变化，缓存会被清空

    /* 磁盘缓存的总大小，若缓存对象的总大小超过了maxSize，DiskLruCache会自动删去最近最少使用的一些缓存对象。**/
    private static final int DISK_CACHE_SIZE = 30 * 1024 * 1024;

    /* 磁盘缓存类，来自google源码 **/
    private DiskLruCache mDiskLruCache;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAX_POOL_SIZE = 2 * CPU_COUNT + 1;
    private static final long KEEP_ALIVE = 5L;

    /* 内存缓存 */
    private LruCache<String, Bitmap> mMemoryCache;
    private static final int IMG_URL = R.id.iv_url;

    // LarvaManager


    private static final Executor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    private LarvaManager(Context context){
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int memoryCacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(memoryCacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };

        File diskCacheDir = FileUtil.getAppCacheDir(context, "images");
        if (!diskCacheDir.exists()) {
            diskCacheDir.mkdirs();
        }

        if (diskCacheDir.getUsableSpace() > DISK_CACHE_SIZE) {
            try {
                mDiskLruCache = DiskLruCache.open(diskCacheDir, DISK_APP_VERSION, 1, DISK_CACHE_SIZE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public static LarvaManager getLarvaManager(Context context){

        if (sLarvaManager == null){
            synchronized (LarvaManager.class){
                if (sLarvaManager == null){
                    sLarvaManager = new LarvaManager(context);
                }
            }
        }

        return sLarvaManager;

    }

    public LarvaManager load(String url){
        this.mUrl = url;
        return this;
    }

    public LarvaManager into(ImageView imageView){
        load(mUrl,imageView);

        return this;

    }

    public void override(int width, int height){
        this.width = width;
        this.height = height;
    }

    /**
     * Bitmap 添加到内存中
     *
     * @param key
     * @param bitmap
     */
    private void addToMemoryCache(String key, Bitmap bitmap) {
        if (FileUtil.getFromMemoryCache(mMemoryCache, key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    /**
     * 加载 ~~~
     *
     * @param url
     * @param imageView
     */
    public void load(final String url, final ImageView imageView) {
        if (TextUtils.isEmpty(url)){
            return;
        }
        imageView.setTag(IMG_URL, url);
        Bitmap bitmap = loadFromMemory(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(url);
                if (bitmap != null) {
                    Result result = new Result(imageView, bitmap, url);
                    Message msg = mMainHandler.obtainMessage(MESSAGE_SEND_RESULT, result);
                    msg.sendToTarget();
                }
            }
        };
        threadPoolExecutor.execute(loadBitmapTask);
    }


    /**
     * 尝试分别从内存、磁盘、网络中加载图片
     * @param url
     * @return
     */
    private Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        try {
            boolean limitSize = false;
            bitmap = loadFromMemory(url);
            if (bitmap != null) {
                return bitmap;
            }

            if (width>0 && height>0){
                limitSize = true;
                bitmap = loadFromDisk(url, width, height);
            }else {
                bitmap = loadFromDisk(url);
            }

            if (bitmap != null) {
                return bitmap;
            }

            if (limitSize){
                bitmap = loadFromNet(url, width, height);
            }else {
                bitmap = loadFromNet(url);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }  catch (NullPointerException e){
            e.printStackTrace();
        }

        return bitmap;
    }


    private Bitmap loadFromNet(String url, int dstWidth, int dstHeight) throws IOException {

        if (mDiskLruCache == null) {
            throw new NullPointerException("mDiskLruCache has not been init");
        }

        String key = MemoryCacheUtil.getKeyFromUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(0);
            if (NetCacheUtil.getStreamFromUrl(url, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadFromDisk(url, dstWidth, dstHeight);
    }

    private Bitmap loadFromNet(String url) throws IOException {
        Util.assertMainThread();

        if (mDiskLruCache == null) {
            throw new NullPointerException("mDiskLruCache has not been init");
        }

        String key = MemoryCacheUtil.getKeyFromUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null) {
            OutputStream outputStream = editor.newOutputStream(0);
            if (NetCacheUtil.getStreamFromUrl(url, outputStream)) {
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadFromDisk(url);
    }

    /**
     * 从内存中加载Bitmap
     *
     * @param url
     * @return
     */
    private Bitmap loadFromMemory(String url) {
        return FileUtil.getFromMemoryCache(mMemoryCache, MemoryCacheUtil.getKeyFromUrl(url));
    }

    /**
     * 从磁盘中加载
     *
     * @param url
     * @param dstWidth
     * @param dstHeight
     * @return
     * @throws IOException
     */
    private Bitmap loadFromDisk(String url, int dstWidth, int dstHeight) throws IOException {

        if (mDiskLruCache == null) {
            throw new NullPointerException("mDiskLruCache has not been init");
        }

        String key = MemoryCacheUtil.getKeyFromUrl(url);
        Bitmap bitmap = DiskCacheUtil.loadFromDisk(mDiskLruCache,key,dstWidth,dstHeight);
        if (bitmap != null) {
            addToMemoryCache(key, bitmap);
        }
        return bitmap;
    }

    /**
     * 从磁盘中加载
     *
     * @param url
     * @return
     * @throws IOException
     */
    private Bitmap loadFromDisk(String url) throws IOException {
        Util.assertMainThread();

        if (mDiskLruCache == null) {
            throw new NullPointerException("mDiskLruCache has not been init");
        }

        String key = MemoryCacheUtil.getKeyFromUrl(url);
        Bitmap bitmap = DiskCacheUtil.loadFromDisk(mDiskLruCache,key);
        if (bitmap != null) {
            addToMemoryCache(key, bitmap);
        }
        return bitmap;
    }

    /**
     * 主线程 handler
     */
    private static class MainHandler extends Handler {
        MainHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MESSAGE_SEND_RESULT) {
                Result result = (Result) msg.obj;
                ImageView imageView = result.imageView;
                String url = (String) imageView.getTag(IMG_URL);
                if (url.equals(result.url)) {
                    imageView.setImageBitmap(result.bitmap);
                } else {
                    UDebug.i("~~~~~~~~~~~~~");
                }
            }
        }
    }


//    public static class Create {
//
//        private String url;
//
//        private ImageView imageView;
//
//        public LarvaManager.Create load(String url) {
//            this.url = url;
//            return this;
//        }
//
//        public LarvaManager.Create into(ImageView imageView) {
//            this.imageView = imageView;
//            return this;
//        }
//
//        private void build(LarvaManager manager) {
//            manager.url = this.url;
//            manager.imageView = this.imageView;
//        }
//
//        public LarvaManager with(Context context) {
//            LarvaManager mLarva = new LarvaManager(context);
//            build(mLarva);
//            return mLarva;
//        }
//
//
//    }

}

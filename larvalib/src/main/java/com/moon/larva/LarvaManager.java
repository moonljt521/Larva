package com.moon.larva;

import android.content.Context;
import android.widget.ImageView;

/**
 * author: moon
 * created on: 17/10/1 下午7:28
 * description:  larva管理器，全面处理请求和传入的参数，
 */
public class LarvaManager {

    private String url ;

    private ImageView imageView;

    private static volatile LarvaManager sLarvaManager ;

    private LarvaManager(Context context){

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



    public static class Create {

        private String url;

        private ImageView imageView;

        public LarvaManager.Create load(String url) {
            this.url = url;
            return this;
        }

        public LarvaManager.Create into(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        private void build(LarvaManager manager) {
            manager.url = this.url;
            manager.imageView = this.imageView;
        }

        public LarvaManager with(Context context) {
            LarvaManager mLarva = new LarvaManager(context);
            build(mLarva);
            return mLarva;
        }


    }

}

package com.moon.larva.request;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * author: moon
 * created on: 17/10/2 下午1:03
 * description:
 */
public class Result {
    public ImageView imageView;
    public Bitmap bitmap;
    public String url;

    public Result(ImageView imageView, Bitmap bitmap, String url) {
        this.imageView = imageView;
        this.bitmap = bitmap;
        this.url = url;
    }
}

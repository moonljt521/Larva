package com.moon.larva.utils;

import android.os.Looper;

/**
 * author: moon
 * created on: 17/10/9 下午6:23
 * description:
 */
public class Util {

    public static void assertMainThread() {
        if (!isOnMainThread()) {
            throw new IllegalArgumentException("You must call this method on the main thread");
        }
    }

    /**
     * Returns {@code true} if called on the main thread, {@code false} otherwise.
     */
    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}

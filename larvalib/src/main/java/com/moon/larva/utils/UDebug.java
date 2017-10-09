package com.moon.larva.utils;

import android.util.Log;

import com.moon.larva.Larva;

/**
 * author: moon
 * created on: 17/10/1 下午1:46
 * description:
 */
public class UDebug {

    private static final String TAG = Larva.class.getSimpleName();

    private static boolean cancelLog;

    private UDebug() {

    }

    public static void i(String value) {
        if (!cancelLog) {
            Log.i(TAG, value);
        }
    }
}

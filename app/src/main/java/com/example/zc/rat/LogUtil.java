package com.example.zc.rat;

import android.util.Log;

public class LogUtil {
    private static final String TAG = "LogUtil";
    public static final boolean LOG_ON = true;

    public static void i(String msg) {
        if (!LOG_ON) {
            return;
        }
        Log.i(TAG, msg);
    } public static void e(String msg) {
        if (!LOG_ON) {
            return;
        }
        Log.e(TAG, msg);
    } public static void d(String msg) {
        if (!LOG_ON) {
            return;
        }
        Log.d(TAG, msg);
    }
}

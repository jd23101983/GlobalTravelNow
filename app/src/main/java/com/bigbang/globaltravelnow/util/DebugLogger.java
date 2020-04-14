package com.bigbang.globaltravelnow.util;

import android.util.Log;

import static com.bigbang.globaltravelnow.util.Constants.ERROR_PREFIX;
import static com.bigbang.globaltravelnow.util.Constants.TAG;

public class DebugLogger {

    public static void logError(Throwable throwable) {
        Log.d(TAG, ERROR_PREFIX + throwable.getLocalizedMessage());
    }

    public static void logDebug(String message) {
        Log.d(TAG, message);
    }
}

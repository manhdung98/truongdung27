package com.tintuc.until;

import android.util.Log;

public class LogUtil {
    static boolean isLogEnable = true;
    public static void d(String tag, String msg){
        if(isLogEnable){
            Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg){
        if(isLogEnable){
            Log.d(tag, msg);
        }
    }
    public static void e(String tag, String msg){
        if(isLogEnable){
            Log.d(tag, msg);
        }
    }
}

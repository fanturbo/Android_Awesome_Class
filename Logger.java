package com.water.watertime.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.water.watertime.BuildConfig;

import java.util.concurrent.ExecutorService;

public class Logger {
    private static String mTag = "";

    private final static boolean DEBUG = BuildConfig.ISDEBUG;
    private static ExecutorService executorService;

    public Logger(Class clazz, String mAppTag) {
        mTag = mAppTag + clazz.getSimpleName();
    }

    static String className;
    static String methodName;
    static int lineNumber;

    public static boolean isDebuggable() {
        return DEBUG;
    }

    public static void setMyTags(String tag) {
        mTag = tag;
    }

    private static String createLog(String log) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(methodName);
        buffer.append(":");
        buffer.append(lineNumber);
        buffer.append("]");
        buffer.append(log);

        return buffer.toString();
    }

    private static String createTag(String tag) {

        StringBuffer buffer = new StringBuffer();
        buffer.append("[");
        buffer.append(className);
        buffer.append("]");
        buffer.append(tag);

        return buffer.toString();
    }

    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String message) {
        if (!isDebuggable())
            return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        if (mTag == "" || mTag == null)
            Log.e(className, createLog(message));
        else
            Log.e(createTag(mTag), createLog(message));
    }

    public static void e(String tag, String message) {
        if (!isDebuggable())
            return;

        // Throwable instance must be created before any methods
        getMethodNames(new Throwable().getStackTrace());
        Log.e(createTag(tag), createLog(message));
    }

    public static void i(Object message) {
        if (!isDebuggable())
            return;
        String messageString = message.toString();
        getMethodNames(new Throwable().getStackTrace());
        if (mTag == "" || mTag == null)
            Log.i(className, createLog(messageString));
        else
            Log.i(createTag(mTag), createLog(messageString));
    }

    public static void i(String tag, String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.i(createTag(tag), createLog(message));
    }

    public static void d(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        if (mTag == "" || mTag == null) {
            //Log.d(className, createLog(message));
            //for leshi phone
            Log.i(className, createLog(message));
            postDebugLog(className + "::" + createLog(message));
        } else {
            //Log.d(createTag(mTag), createLog(message));
            //for leshi phone
            Log.i(createTag(mTag), createLog(message));
            postDebugLog(createTag(mTag) + "::" + createLog(message));
        }


    }

    public static void d(String tag, String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.d(createTag(tag), createLog(message));
    }

    public static void v(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        if (mTag == "" || mTag == null) {
            Log.v(className, createLog(message));
            postDebugLog(className + "::" + createLog(message));
        } else {
            Log.v(createTag(mTag), createLog(message));
            postDebugLog(createTag(mTag) + "::" + createLog(message));
        }
    }

    public static void v(String tag, String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.v(createTag(tag), createLog(message));
    }

    public static void w(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        if (mTag == "" || mTag == null) {
            Log.w(className, createLog(message));
            postDebugLog(className + "::" + createLog(message));
        } else {
            Log.w(createTag(mTag), createLog(message));
            postDebugLog(createTag(mTag) + "::" + createLog(message));
        }

    }

    public static void w(String tag, String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.w(createTag(tag), createLog(message));
    }

    public static void wtf(String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        if (mTag == "" || mTag == null)
            Log.wtf(className, createLog(message));
        else
            Log.wtf(createTag(mTag), createLog(message));
    }

    public static void wtf(String tag, String message) {
        if (!isDebuggable())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(createTag(tag), createLog(message));
    }

    public static void toast(String message, Context context) {
        Toast.makeText(context, createLog(message), Toast.LENGTH_SHORT).show();
    }

    /**
     * 提交日志到服务器
     *
     * @param message
     */
    public static void postDebugLog(final String message) {
        //开启线程池执行log信息上传
//        if (executorService == null) {
//            executorService = Executors.newFixedThreadPool(3);
//        }
//        executorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                Map<String, String> params = new HashMap<>();
//                params.put("device_name", AppUtility.getDevicesName());
//                params.put("log", message);
//                HttpUtil.doPost(params, HttpRequest.APP_DEBUG_LOG, "utf-8");
//            }
//        });
    }
}
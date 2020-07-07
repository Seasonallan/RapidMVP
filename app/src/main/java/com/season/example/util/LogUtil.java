package com.season.example.util;

import android.util.Log;


/**
 * Disc: 日志输出类
 * User: SeasonAllan(451360508@qq.com)
 * Time: 2017-09-11 21:26
 */
public class LogUtil {

    private static String logFilterRegex = ".*";
    private static String charset = "UTF-8";
    private static int logLength = 7;
    private static boolean isDebug = true;

    public static void setCharset(String charset) {
        LogUtil.charset = charset;
    }

    public static void setLogFilterRegex(String logFilterRegex) {
        LogUtil.logFilterRegex = logFilterRegex;
    }

    public static void v() {
        LogUtil.performV(" ", 3);
    }

    public static void v(Object... values) {
        String str = getLogValues(getLogLength(), values);
        LogUtil.performV(str, 3);
    }

    public static void performV(Object msg, int i) {
        if (!isDebug) {
            return;
        }
        String tag = createLogTag(i);

        if (!isPass(tag)) {
            return;
        }
        if (msg != null) {
            Log.v(tag, msg.toString());
        } else {
            Log.v(tag, "null");
        }
    }

    public static void d() {
        LogUtil.performD(" ", 3);
    }

    public static void d(Object... values) {
        String str = getLogValues(getLogLength(), values);
        LogUtil.performD(str, 3);
    }

    public static void performD(Object msg, int i) {
        if (!isDebug) {
            return;
        }
        String tag = createLogTag(i);

        if (!isPass(tag)) {
            return;
        }
        if (msg != null) {
            Log.d(tag, msg.toString());
        } else {
            Log.d(tag, "null");
        }
    }

    public static void i() {
        LogUtil.performI(" ", 3);
    }

    public static void i(Object... msg) {

        String str = getLogValues(getLogLength(), msg);
        LogUtil.performI(str, 3);
    }

    public static void performI(Object msg, int i) {

        if (!isDebug()) {
            return;
        }
        String tag = createLogTag(i);
        if (!isPass(tag)) {
            return;
        }
        if (msg != null) {
            Log.i(tag, msg.toString());
        } else {
            Log.i(tag, "null");
        }
    }

    public static void w() {

        LogUtil.performW(" ", 3);
    }

    public static void w(Object... msg) {

        String str = getLogValues(getLogLength(), msg);
        LogUtil.performW(str, 3);
    }

    public static void performW(Object msg, int i) {

        if (!isDebug) {
            return;
        }
        String tag = createLogTag(i);

        if (!isPass(tag)) {
            return;
        }
        if (msg != null) {

            Log.w(tag, msg.toString());
        } else {
            Log.w(tag, "null");
        }
    }

    public static void e() {
        LogUtil.performE(" ", 3);
    }

    public static void e(Object... msg) {
        String str = getLogValues(getLogLength(), msg);
        LogUtil.performE(str, 3);
    }

    public static void performE(Object msg, int i) {

        if (!isDebug) {
            return;
        }
        String tag = createLogTag(i);

        if (!isPass(tag)) {
            return;
        }
        if (msg != null) {

            Log.e(tag, msg.toString());
        } else {
            Log.e(tag, "null");
        }
    }

    public static void log(Object msg, int i) {

        if (!isDebug) {
            return;
        }
        String tag = createLogTag(i);

        if (!isPass(tag)) {
            return;
        }
        if (msg != null) {

            Log.e(tag, msg.toString());
        } else {
            Log.e(tag, "null");
        }
    }

    private static boolean isPass(String tag) {
        if (tag.matches(logFilterRegex)) {
            return true;
        }
        return false;
    }

    public static void log(Object msg) {
        log(msg, 3);
    }

    public static void log() {
        log("   ", 3);
    }

    public static String createLogTag(int stackCount) {
        StackTraceElement stack[] = new Throwable().getStackTrace();
        StackTraceElement stackMsg = stack[stackCount];
        String className = stackMsg.getClassName();
        className = className.substring(className.lastIndexOf(".") + 1);
        String name = Thread.currentThread().getName();
        if (name.length() > 15) {
            name = name.substring(0, 15);
        }
        String threadTag = "[" + name + "] ";
        return "Y->" + threadTag + className + "." + stackMsg.getMethodName()
                + " line: " + stackMsg.getLineNumber();
    }

    public static void logThreadInfo() {
        String info = Thread.currentThread().getId() + " : "
                + Thread.currentThread().getName();
        LogUtil.log(info, 3);
    }

    /**
     * 比如 logValues(10,"12","2.025") 打印出 12 | 2.025
     *
     * @param length
     * @param values
     */
    public static void logValues(int length, Object... values) {

        String str = getLogValues(length, values);

        LogUtil.log(str, 3);
    }

    private static String getLogValues(int length, Object... values) {
        StringBuilder mainBuilder = new StringBuilder();

        for (Object object : values) {

            if (object == null) {
                object = "null";
            }

            StringBuilder sb = new StringBuilder(object.toString());

            while (sb.length() < length) {
                sb.append(" ");
            }

            sb.append("| ");

            mainBuilder.append(sb);
        }
        return mainBuilder.toString();
    }

    /**
     * 抛出错误的具体行号
     *
     * @param e catch捕获到的Exception
     */
    public static void logError(Exception e) {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackTrace = e.getStackTrace();
        sb.append(">>>>>>>>>>      " + e.toString() + " at :     <<<<<<<<<<"
                + "\n");
        for (int i = 0; i < stackTrace.length; i++) {
            if (i < 10) {
                StackTraceElement stackTraceElement = stackTrace[i];
                String errorMsg = stackTraceElement.toString();
                sb.append(errorMsg).append("\n");
            } else {
                sb.append("more : " + (stackTrace.length - 10) + "..." + "\n");
                break;
            }
        }
        sb.append(">>>>>>>>>>     end of error     <<<<<<<<<<");
        log(sb.toString(), 3);
    }

    /**
     * 抛出错误的具体行号
     *
     * @param e catch捕获到的Exception
     */
    public static void logError(String msg, Exception e) {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stackTrace = e.getStackTrace();
        sb.append(">>>>>>>>>>      " + e.toString() + " at :     <<<<<<<<<<"
                + "\n");
        for (int i = 0; i < stackTrace.length; i++) {
            if (i < 15) {
                StackTraceElement stackTraceElement = stackTrace[i];
                String errorMsg = stackTraceElement.toString();
                sb.append(errorMsg).append("\n");
            } else {
                sb.append("more : " + (stackTrace.length - 15) + "..." + "\n");
                break;
            }
        }
        sb.append(">>>>>>>>>>     end of error     <<<<<<<<<<");
        log(msg == null ? sb.toString() : msg + "---" + sb.toString(), 4);
    }

    public static String getCharset() {
        return charset;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setDebug(boolean isDebug) {
        LogUtil.isDebug = isDebug;
    }

    public static int getLogLength() {
        return logLength;
    }

    public static void setLogLength(int logLength) {
        LogUtil.logLength = logLength;
    }
}

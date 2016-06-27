package com.zeal.utils;

import org.apache.log4j.Logger;

/**
 * Created by yang_shoulai on 2015/10/15.
 */
public class LogUtils {

    private static Level level = Level.DEBUG;

    public enum Level {
        ERROR(0),

        WARN(1),

        INFO(2),

        DEBUG(3);

        public int value;

        Level(int value) {
            this.value = value;
        }
    }

    public static boolean isDebugEnable() {

        return level.value >= Level.DEBUG.value;
    }

    public static boolean isInfoEnable() {
        return level.value >= Level.INFO.value;
    }


    public static void debug(Class<?> clz, Object message) {
        if (isDebugEnable()) {
            Logger.getLogger(clz).debug(message);
        }

    }

    public static void debug(Class<?> clz, Object message, Throwable throwable) {
        if (isDebugEnable()) {
            Logger.getLogger(clz).debug(message, throwable);
        }

    }

    public static void info(Class<?> clz, Object message) {
        if (isInfoEnable()) {
            Logger.getLogger(clz).info(message);
        }
    }

    public static void info(Class<?> clz, Object message, Throwable throwable) {
        if (isInfoEnable()) {
            Logger.getLogger(clz).info(message, throwable);
        }

    }

    public static void warn(Class<?> clz, Object message) {
        if (level.value >= Level.WARN.value) {
            Logger.getLogger(clz).warn(message);
        }

    }

    public static void warn(Class<?> clz, Object message, Throwable throwable) {
        if (level.value >= Level.WARN.value) {
            Logger.getLogger(clz).warn(message, throwable);
        }

    }

    public static void error(Class<?> clz, Object message) {
        if (level.value >= Level.ERROR.value) {
            Logger.getLogger(clz).error(message);
        }

    }

    public static void error(Class<?> clz, Object message, Throwable throwable) {
        if (level.value >= Level.ERROR.value) {

            Logger.getLogger(clz).error(message, throwable);
        }
    }

}

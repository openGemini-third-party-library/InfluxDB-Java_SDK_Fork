package org.influxdb.impl;

public final class ThreadLocalHeader {

    private ThreadLocalHeader() {
        throw new UnsupportedOperationException("工具类不应被实例化");
    }

    private static final ThreadLocal<String> THREAD_LOCAL_DATA = new ThreadLocal<>();

    public static void setData(final String value) {
        THREAD_LOCAL_DATA.set(value);
    }

    public static String getData() {
        return THREAD_LOCAL_DATA.get();
    }

    public static void clear() {
        THREAD_LOCAL_DATA.remove();
    }
}

package org.influxdb.impl;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

final class Lz4RequestInterceptor implements Interceptor {

    private static final String LZ4 = "lz4";

    private AtomicBoolean enabled = new AtomicBoolean(false);

    Lz4RequestInterceptor() {
    }

    public void enable() {
        enabled.set(true);
    }

    public boolean isEnabled() {
        return enabled.get();
    }

    public void disable() {
        enabled.set(false);
    }

    @Override
    public Response intercept(final Chain chain) throws IOException {

        if (enabled.get()) {
            ThreadLocalHeader.setData("lz4");
            return chain.proceed(chain.request().newBuilder().header("Accept-Encoding", LZ4).build());
        }

        return chain.proceed(chain.request());
    }


}

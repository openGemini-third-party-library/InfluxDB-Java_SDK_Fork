package org.influxdb.msgpack;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * A Retrofit Convertor Factory for jSON lz4 response.
 *
 * @author hoan.le [at] bonitoo.io
 *
 */
public class Lz4PackConverterFactory extends Converter.Factory {
    public static Lz4PackConverterFactory create() {
    return new Lz4PackConverterFactory();
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(final Type type, final Annotation[] annotations,
      final Retrofit retrofit) {

    return new Lz4PackResponseBodyConverter();
    }
}

package org.influxdb.msgpack;

import com.alibaba.fastjson.JSON;
import net.jpountz.lz4.LZ4FrameInputStream;
import okhttp3.ResponseBody;
import okio.Okio;
import org.influxdb.dto.QueryResult;
import org.influxdb.impl.ThreadLocalHeader;
import retrofit2.Converter;

import java.io.IOException;
import java.io.InputStream;

/**
 * Test the InfluxDB API over MessagePack format.
 *
 * @author hoan.le [at] bonitoo.io
 *
 */
public class MessagePackResponseBodyConverter implements Converter<ResponseBody, QueryResult> {

    @Override
    public QueryResult convert(final ResponseBody value) throws IOException {
        try (InputStream is = value.byteStream()) {
            if ("lz4".equals(ThreadLocalHeader.getData())) {
                ThreadLocalHeader.clear();
                return JSON.parseObject(new String(doRead(decompressLz4(is))), QueryResult.class);
            } else {
                MessagePackTraverser traverser = new MessagePackTraverser();
                return traverser.parse(is);
            }
        }
    }

    private InputStream decompressLz4(final InputStream compressedInputStream) throws IOException {

        LZ4FrameInputStream lz4InputStream = new LZ4FrameInputStream(compressedInputStream,
                Boolean.TRUE);
        return lz4InputStream;
    }

    public static byte[] doRead(final InputStream inputStream) throws IOException {
        return Okio.buffer(Okio.source(inputStream)).readByteArray();
    }
}

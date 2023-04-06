package com.specialiststeak.learn_api.utils;

import lombok.experimental.UtilityClass;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

@UtilityClass
public class Compression {
    public static byte[] compressByteArray(byte[] data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(data, 0, data.length);
        gzip.close();
        byte[] compressedData = bos.toByteArray();
        bos.close();
        return compressedData;
    }
}

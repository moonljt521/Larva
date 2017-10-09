package com.moon.larva.cache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author: moon
 * created on: 17/10/2 上午10:21
 * description:
 */
public class NetCacheUtil {

    public static boolean getStreamFromUrl(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            bis = new BufferedInputStream(urlConnection.getInputStream());
            bos = new BufferedOutputStream(outputStream);

            int byteRead;
            while ((byteRead = bis.read()) != -1) {
                bos.write(byteRead);
            }
            return true;
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            close(bis);
            close(bos);
        }
        return false;
    }

    private static void close(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

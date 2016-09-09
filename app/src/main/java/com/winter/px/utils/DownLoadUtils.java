package com.winter.px.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadUtils {

    //得到解析的字符串
    public static String getJsonString(String u) {
        String jString = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(u).build();
        try {
            Response response = client.newCall(request).execute();
            jString = response.body().string();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.d("winter", "------DownLoadUtils.getJsonString----" + jString);
        return jString;
    }

    //
    public static Bitmap getImageByteUrl(String url) {
        URL address;
        Bitmap bitmap = null;
        try {
            address = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) address.openConnection();
            connection.connect();
            if (connection.getResponseCode() == 200) {
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());

            } else {
                Log.d("winter", "connect fail");
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }

    //得到图片的字节数组
    public static byte[] getImageByte(String url) {
        byte[] bs = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            bs = response.body().bytes();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Log.d("winter", "------DownLoadUtils.getImageByte------" + bs);
        return bs;
    }

    //判断文件夹内是否含有文件
    public static boolean isHaveImage(File[] file, String path) {
        Uri uri = Uri.parse(path);
        String str = uri.getLastPathSegment();
        if (file != null) {
            for (File ff : file) {
                if (ff.getName().equals(str)) {
                    return true;
                }
            }
        }
        return false;

    }
}

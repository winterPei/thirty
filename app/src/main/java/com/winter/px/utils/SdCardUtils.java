package com.winter.px.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by peiyangyang on 2016/8/27.
 */
public class SdCardUtils {
    //三级缓存
    public static void saveToSdCardBitmap(byte[] bs, String url) {
        Uri uri = Uri.parse(url);
        String str = Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + "image" +File.separator+ uri.getLastPathSegment();
        Log.d("winter","sd卡存储文件位置-----"+str);
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+
                File.separator + "image");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File[] files = dir.listFiles();
        if (!DownLoadUtils.isHaveImage(files, url)) {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(str);
                fos.write(bs, 0, bs.length);
                fos.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Bitmap getSdCardImage(String url) {

        Uri uri = Uri.parse(url);
        String str = Environment.getExternalStorageState().toString() +
                File.separator + "image" +
                File.separator +
                uri.getLastPathSegment();
        FileInputStream fis = null;
        ByteArrayOutputStream baos = null;
        try {
            fis = new FileInputStream(str);
            baos = new ByteArrayOutputStream();
            byte[] bs = new byte[1024 * 10];
            int len = 0;
            while ((len = fis.read(bs)) != -1) {
                baos.write(bs, 0, bs.length);
                baos.flush();
            }
            bs = baos.toByteArray();
            return OptionBitmap.getOptionBitmap(bs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos!=null){
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}



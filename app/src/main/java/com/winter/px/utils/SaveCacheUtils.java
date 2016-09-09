package com.winter.px.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by peiyangyang on 2016/8/27.
 */
public class SaveCacheUtils {
    //二级缓存
    public static void saveCacheBitmap(byte[] bs, String url, Context context) {
        Uri uri = Uri.parse(url);
        String str = context.getCacheDir().toString() +
                File.separator + "image" +
                File.separator + uri.getLastPathSegment();
        File dir = new File(context.getCacheDir().toString() + File.separator + "image");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File[] files = dir.listFiles();
        if (!DownLoadUtils.isHaveImage(files, url)) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(str);
                out.write(bs, 0, bs.length);
                out.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }


    }
    public static Bitmap getCacheBitmap(String url,Context context){
       Uri uri = Uri.parse(url);
        String str  = context.getCacheDir().toString()+
                File.separator+"image"+
                File.separator+uri.getLastPathSegment();
        FileInputStream in = null;
        ByteArrayOutputStream baos = null;
        try {
            in = new FileInputStream(str);
            baos = new ByteArrayOutputStream();
            byte[] bs = new byte[1024*10];
            int len =0;
            while ((len=in.read(bs))!=-1){
                baos.write(bs,0,bs.length);
                baos.flush();
            }
            bs = baos.toByteArray();
            return OptionBitmap.getOptionBitmap(bs);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }
}

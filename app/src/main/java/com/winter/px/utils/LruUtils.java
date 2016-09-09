package com.winter.px.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by peiyangyang on 2016/8/26.
 */
public class LruUtils {
    LruCache<String,Bitmap> lruCache;


    public LruCache initLru(){
        int maxSize = 4*1024*1024;//设置最大缓存空间为4M
        return lruCache = new LruCache<>(maxSize);

    }
    //从缓存里获得图片
    public Bitmap getImageBitmap(String url){
        if (lruCache!=null){
            return lruCache.get(url);
        }
        return null;
    }
    //将图片存入缓存
    public void saveImageBitmap(String url,Bitmap bitmap){
        Log.d("winter","String"+url+"-----"+"Bitmap"+bitmap);
        if (getImageBitmap(url)==null){
            lruCache.put(url,bitmap);
        }
    }
    //从缓存中移除图片
    public void deleteImageBitmap(String url){
        if (getImageBitmap(url)!=null){
            lruCache.remove(url);
        }
    }
}

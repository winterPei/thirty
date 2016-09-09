package com.winter.px.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.winter.px.bean.NewsDa;
import com.winter.px.niceview.R;
import com.winter.px.okActivity.NewsWebViewActivity;
import com.winter.px.utils.DownLoadUtils;
import com.winter.px.utils.LruUtils;
import com.winter.px.utils.OptionBitmap;
import com.winter.px.utils.SaveCacheUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by peiyangyang on 2016/9/8.
 */
public class StfFragmentAdapter extends BaseAdapter {
    List<NewsDa.ListBean> list;
    Context context;
    ExecutorService executorService;
    LruUtils lruUtils;
    Handler handler;

    public StfFragmentAdapter(List<NewsDa.ListBean> list, Context context, ExecutorService executorService) {
        this.list = list;
        this.context = context;
        this.executorService = executorService;
        handler = new Handler();
    }

    @Override
    public int getCount() {
        return list.size()/2;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        lruUtils = new LruUtils();
        lruUtils.initLru();
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_gridview,null);
            viewHolder.stfLeftImage = (ImageView) convertView.findViewById(R.id.LeftImageView);
            viewHolder.stfLeftText = (TextView) convertView.findViewById(R.id.gridLeftTitle);
            viewHolder.stfRightImage = (ImageView) convertView.findViewById(R.id.RightImageView);
            viewHolder.stfRightText = (TextView) convertView.findViewById(R.id.gridRightTitle);
            convertView.setTag(viewHolder);

        }else{
            viewHolder  = (ViewHolder) convertView.getTag();
        }
        viewHolder.stfLeftText.setText(list.get(position*2).getTitle());
        viewHolder.stfRightText.setText(list.get(position*2+1).getTitle());
        final String stfLeftIU = list.get(position*2).getPic_url();
        final String stfRightIU = list.get(position*2+1).getPic_url();
        viewHolder.stfLeftImage.setTag(stfLeftIU);
        viewHolder.stfRightImage.setTag(stfRightIU);
        File cacheFile = new File(context.getCacheDir()+File.separator+"image");
        File[] files = cacheFile.listFiles();
        if (lruUtils.getImageBitmap(stfLeftIU)!=null||lruUtils.getImageBitmap(stfRightIU)!=null){
            if (viewHolder.stfLeftImage.getTag().equals(stfLeftIU)&&viewHolder.stfLeftImage.getTag()!=null){
                viewHolder.stfLeftImage.setImageBitmap(lruUtils.getImageBitmap(stfLeftIU));
            }
            if (viewHolder.stfRightImage.getTag().equals(stfRightIU)&&viewHolder.stfRightImage.getTag()!=null){
                viewHolder.stfRightImage.setImageBitmap(lruUtils.getImageBitmap(stfRightIU));
            }
        }else if(files!=null&&DownLoadUtils.isHaveImage(files,stfLeftIU)||files!=null&&DownLoadUtils.isHaveImage(files,stfRightIU)){
            if (viewHolder.stfLeftImage.getTag().equals(stfLeftIU)&&viewHolder.stfLeftImage.getTag()!=null){
                viewHolder.stfLeftImage.setImageBitmap(SaveCacheUtils.getCacheBitmap(stfLeftIU,context));
            }
            if (viewHolder.stfRightImage.getTag().equals(stfRightIU)&&viewHolder.stfRightImage.getTag()!=null){
                viewHolder.stfRightImage.setImageBitmap(SaveCacheUtils.getCacheBitmap(stfRightIU,context));
            }

        }else{
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //获得字节数组
                    byte[] stfLeftByte = DownLoadUtils.getImageByte(stfLeftIU);
                    byte[] stfRightByte = DownLoadUtils.getImageByte(stfRightIU);
                    //二次采样得到bitmap
                    final Bitmap leftBitmap = OptionBitmap.getOptionBitmap(stfLeftByte);
                    final Bitmap rightBitmap = OptionBitmap.getOptionBitmap(stfRightByte);
                    if (leftBitmap!=null){
                        lruUtils.saveImageBitmap(stfLeftIU,leftBitmap);
                        SaveCacheUtils.saveCacheBitmap(stfLeftByte,stfLeftIU,context);
                    }
                    if (rightBitmap!=null){
                        lruUtils.saveImageBitmap(stfRightIU,rightBitmap);
                        SaveCacheUtils.saveCacheBitmap(stfRightByte,stfRightIU,context);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (viewHolder.stfLeftImage.getTag().equals(stfLeftIU)&&viewHolder.stfLeftImage.getTag()!=null){
                                viewHolder.stfLeftImage.setImageBitmap(leftBitmap);
                            }
                            if (viewHolder.stfRightImage.getTag().equals(stfRightIU)&&viewHolder.stfRightImage.getTag()!=null){
                                viewHolder.stfRightImage.setImageBitmap(rightBitmap);
                            }
                        }
                    });
                }
            });
        }
        viewHolder.stfLeftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String leftWeb_urlString = list.get(position*2).getWeb_url();
                String leftTitle_String = list.get(position*2).getTitle();
                Intent intent = new Intent(context, NewsWebViewActivity.class);
                intent.putExtra("web_url",leftWeb_urlString);
                intent.putExtra("title",leftTitle_String);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        viewHolder.stfRightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rightWeb_urlString = list.get(position*2+1).getWeb_url();
                String rightTitle_String = list.get(position*2+1).getTitle();
                Intent intent = new Intent(context, NewsWebViewActivity.class);
                intent.putExtra("web_url",rightWeb_urlString);
                intent.putExtra("title",rightTitle_String);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        return convertView;
    }
    class ViewHolder{
        ImageView stfLeftImage,stfRightImage;
        TextView stfLeftText,stfRightText;
    }
}

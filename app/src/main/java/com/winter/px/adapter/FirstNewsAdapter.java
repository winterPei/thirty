package com.winter.px.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.winter.px.bean.GridNews;
import com.winter.px.bean.ListNews;
import com.winter.px.niceview.R;
import com.winter.px.okActivity.NewsWebViewActivity;
import com.winter.px.utils.DownLoadUtils;
import com.winter.px.utils.LruUtils;
import com.winter.px.utils.OptionBitmap;

import java.util.List;

/**
 * Created by peiyangyang on 2016/9/5.
 */
public class FirstNewsAdapter extends BaseAdapter {
    List<GridNews.GriddataBean> gridNews;
    List<ListNews.ListdataBean> listNews;
    Context context;
    public final int TYPE_GRID = 0;
    public final int TYPE_LIST = 1;
    LruUtils lruUtils;
    Handler handler;



    public FirstNewsAdapter(List<GridNews.GriddataBean> gridNews, Context context, List<ListNews.ListdataBean> listNews) {
        this.gridNews = gridNews;
        this.context = context;
        this.listNews = listNews;
        handler = new Handler();
    }

    @Override
    public int getCount() {
        return gridNews.size() / 2 + listNews.size();
    }

    @Override
    public Object getItem(int position) {
        return position % 5 == 0 ? gridNews.get(position) : listNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        int num = position % 5;
        if (num != 0) {
            return TYPE_LIST;
        } else {
            return TYPE_GRID;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        final ListViewHolder listViewHolder;
        final GridViewHolder gridViewHolder;
        lruUtils = new LruUtils();
        lruUtils.initLru();
        switch (type) {
            case 0:
                if (convertView == null) {
                    gridViewHolder = new GridViewHolder();
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_gridview, null);
                    gridViewHolder.gridImage = (ImageView) convertView.findViewById(R.id.LeftImageView);
                    gridViewHolder.gridText = (TextView) convertView.findViewById(R.id.gridLeftTitle);
                    gridViewHolder.gridRightImage = (ImageView) convertView.findViewById(R.id.RightImageView);
                    gridViewHolder.gridRightText = (TextView) convertView.findViewById(R.id.gridRightTitle);
                    convertView.setTag(gridViewHolder);
                } else {
                    gridViewHolder = (GridViewHolder) convertView.getTag();

                }
                gridViewHolder.gridText.setText(gridNews.get(position*2/5).getTitle());
                final String urlString = gridNews.get(position*2/5).getPic_url();
                gridViewHolder.gridRightText.setText(gridNews.get(position*2/5+1).getTitle());
                final String urlRightString = gridNews.get(position*2/5+1).getPic_url();
                gridViewHolder.gridImage.setTag(urlString);
                gridViewHolder.gridRightImage.setTag(urlRightString);
                gridViewHolder.gridRightImage.setImageResource(R.drawable.bg_bg);
                gridViewHolder.gridImage.setImageResource(R.drawable.bg_bg);
                Log.d("winter", "=adapter=>urlString" + urlString);
                if (lruUtils.getImageBitmap(urlString) != null||lruUtils.getImageBitmap(urlRightString)!=null) {
                    //一级缓存，缓存入内存中
                    if (gridViewHolder.gridImage.getTag().equals(urlString) && gridViewHolder.gridImage.getTag() != null) {
                        gridViewHolder.gridImage.setImageBitmap(lruUtils.getImageBitmap(urlString));
                    }
                    if (gridViewHolder.gridRightImage.getTag().equals(urlRightString) && gridViewHolder.gridRightImage.getTag() != null) {
                        gridViewHolder.gridRightImage.setImageBitmap(lruUtils.getImageBitmap(urlRightString));
                    }
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            byte[] bs = DownLoadUtils.getImageByte(urlString);
                            byte[] rightBs = DownLoadUtils.getImageByte(urlRightString);
                            final Bitmap rightBitmap = OptionBitmap.getOptionBitmap(rightBs);
                            final Bitmap bitmap = OptionBitmap.getOptionBitmap(bs);
                            if (bitmap != null) {
                                lruUtils.saveImageBitmap(urlString, bitmap);
                            }
                            if (rightBitmap != null) {
                                lruUtils.saveImageBitmap(urlRightString, rightBitmap);
                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (gridViewHolder.gridImage.getTag().equals(urlString) && gridViewHolder.gridImage.getTag() != null) {
                                        gridViewHolder.gridImage.setImageBitmap(bitmap);
                                    }
                                    if (gridViewHolder.gridRightImage.getTag().equals(urlRightString) && gridViewHolder.gridRightImage.getTag() != null) {
                                        gridViewHolder.gridRightImage.setImageBitmap(rightBitmap);
                                    }
                                }
                            });
                        }
                    }).start();
                }
                gridViewHolder.gridImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String leftWeb_urlString = gridNews.get(position*2/5).getWeb_url();
                        String leftTitle_String = gridNews.get(position*2/5).getTitle();
                        Intent intent = new Intent(context, NewsWebViewActivity.class);
                        intent.putExtra("web_url",leftWeb_urlString);
                        intent.putExtra("title",leftTitle_String);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                gridViewHolder.gridRightImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String rightWeb_urlString = gridNews.get(position*2/5+1).getWeb_url();
                        String rightTitle_String = gridNews.get(position*2/5+1).getTitle();
                        Intent intent = new Intent(context, NewsWebViewActivity.class);
                        intent.putExtra("web_url",rightWeb_urlString);
                        intent.putExtra("title",rightTitle_String);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });

                break;
            case 1:
                if (convertView == null) {
                    listViewHolder = new ListViewHolder();
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_listview, null);
                    listViewHolder.titleText = (TextView) convertView.findViewById(R.id.listViewTitle);
                    listViewHolder.contentText = (TextView) convertView.findViewById(R.id.listViewDate);
                    listViewHolder.listImage = (ImageView) convertView.findViewById(R.id.listImageView);
                    convertView.setTag(listViewHolder);
                } else {
                    listViewHolder = (ListViewHolder) convertView.getTag();
                }
                int a = position-((int)position/5+1);
                listViewHolder.titleText.setText(listNews.get(position-((int)position/5+1)).getTitle());
                listViewHolder.contentText.setText(listNews.get(position-((int)position/5+1)).getDate());
                final String listString = listNews.get(position-((int)position/5+1)).getPic_url();
                //设置默认图片防止图片错位
                listViewHolder.listImage.setTag(listString);
                listViewHolder.listImage.setImageResource(R.drawable.bg_bg);
                if (lruUtils.getImageBitmap(listString) != null) {
                    if (listViewHolder.listImage.getTag().equals(listString) && listViewHolder.listImage.getTag() != null) {
                        listViewHolder.listImage.setImageBitmap(lruUtils.getImageBitmap(listString));
                    }
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            byte[] listBytes = DownLoadUtils.getImageByte(listString);
                            final Bitmap bitmap = OptionBitmap.getOptionBitmap(listBytes);
                            if (bitmap != null) {
                                lruUtils.saveImageBitmap(listString, bitmap);
                            }
                            Log.d("winter", "-----listString-----" + listString + "-----"+"Bitmap-----" + bitmap);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (listViewHolder.listImage.getTag().equals(listString) && listViewHolder.listImage.getTag() != null) {
                                        listViewHolder.listImage.setImageBitmap(bitmap);
                                    }
                                }
                            });
                        }
                    }).start();
                }
                listViewHolder.listImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String listWeb_urlString = listNews.get(position-((int)position/5+1)).getWeb_url();
                        String listTitle_String = listNews.get(position-((int)position/5+1)).getTitle();
                        Intent intent = new Intent(context, NewsWebViewActivity.class);
                        intent.putExtra("web_url",listWeb_urlString);
                        intent.putExtra("title",listTitle_String);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                });
                break;
        }


        return convertView;
    }

    class ListViewHolder {
        ImageView listImage;
        TextView titleText;
        TextView contentText;
    }

    class GridViewHolder {
        ImageView gridImage;
        TextView gridText;
        ImageView gridRightImage;
        TextView gridRightText;
    }
}

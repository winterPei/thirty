package com.winter.px.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.winter.px.bean.NewsDa;
import com.winter.px.niceview.R;
import com.winter.px.utils.DownLoadUtils;
import com.winter.px.utils.LruUtils;
import com.winter.px.utils.OptionBitmap;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by peiyangyang on 2016/9/6.
 */
public class CustomFragmentAdapter extends BaseAdapter {
    List<NewsDa.ListBean> list;
    Context context;
    LruUtils lruUtils;
    Handler handler;

    public CustomFragmentAdapter(List<NewsDa.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
        handler = new Handler();

    }

    @Override
    public int getCount() {
        return list.size();
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
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        lruUtils = new LruUtils();
        lruUtils.initLru();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.news_customgrid, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.customImageView);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.customTextView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).getTitle());
        final String imageUrl = list.get(position).getPic_url();
        viewHolder.imageView.setTag(imageUrl);
        if (lruUtils.getImageBitmap(imageUrl) != null) {
            if (viewHolder.imageView.getTag().equals(imageUrl) && viewHolder.imageView.getTag() != null) {
                viewHolder.imageView.setImageBitmap(lruUtils.getImageBitmap(imageUrl));
            }
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final byte[] imageByte = DownLoadUtils.getImageByte(imageUrl);
                    if (imageByte!=null){
                    final Bitmap bitmap = OptionBitmap.getOptionBitmap(imageByte);
                    if (bitmap != null) {
                        lruUtils.saveImageBitmap(imageUrl, bitmap);
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (viewHolder.imageView.getTag().equals(imageUrl) && viewHolder.imageView.getTag() != null) {
                                viewHolder.imageView.setImageBitmap(bitmap);
                            }
                        }
                    });}
                }
            }).start();
        }

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}

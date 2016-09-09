package com.winter.px.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.winter.px.niceview.R;

import java.util.List;

/**
 * Created by peiyangyang on 2016/9/7.
 */
public class BBSRightListViewAdapter extends BaseAdapter {
    List<String> list;
    Context context;

    public BBSRightListViewAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.bbs_right_listview, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.rightBBSTextViewId);
            convertView.setTag(viewHolder);
        }else{
            viewHolder  = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position).toString());
        return convertView;

    }
    class ViewHolder{
        TextView textView;
    }
}

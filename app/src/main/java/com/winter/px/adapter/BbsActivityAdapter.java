package com.winter.px.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.winter.px.bean.BbsBean;
import com.winter.px.niceview.R;

import java.util.List;

/**
 * Created by peiyangyang on 2016/9/8.
 */
public class BbsActivityAdapter extends BaseAdapter {
    List<BbsBean.ListBean> list;
    Context context;

    public BbsActivityAdapter(List<BbsBean.ListBean> list, Context context) {
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
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.bbs_activity_item,null);
            viewHolder.bbsItemTitle = (TextView) convertView.findViewById(R.id.bbsItemTitleId);
            viewHolder.bbsItemAuthor = (TextView) convertView.findViewById(R.id.bbsItemAuthorId);
            viewHolder.bbsCount  = (TextView) convertView.findViewById(R.id.bbsCountId);
            viewHolder.bbsItemReplay = (TextView) convertView.findViewById(R.id.bbsItemReplayId);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.bbsItemTitle.setText(list.get(position).getTitle());
        viewHolder.bbsItemAuthor.setText(list.get(position).getAuthor());
        viewHolder.bbsCount.setText(list.get(position).getViews());
        viewHolder.bbsItemReplay.setText(list.get(position).getReply());

        return convertView;
    }
  class ViewHolder{
      TextView bbsItemTitle,bbsItemAuthor,bbsCount,bbsItemReplay;
  }
}

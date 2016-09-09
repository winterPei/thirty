package com.winter.px.JsonUtils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winter.px.bean.GridNews;
import com.winter.px.bean.ListNews;

import java.util.List;

/**
 * Created by peiyangyang on 2016/9/5.
 */
public class ListNewsJson {
    List<ListNews.ListdataBean> listNews;
    List<GridNews.GriddataBean> gridNews;

    public List<ListNews.ListdataBean> getListNews(String listString) {
        Gson gson = new Gson();
        ListNews listData = gson.fromJson(listString,ListNews.class);
        if (listData!=null){
            listNews = listData.getListdata();
        }
        Log.d("winter", "MyJsonUtil--getListNews---------" + listNews);
        return listNews;
    }

    public List<GridNews.GriddataBean> getGridNews(String gridString) {
        Gson gson = new Gson();
        GridNews gridData = gson.fromJson(gridString,GridNews.class);
        if (gridData!=null){
            gridNews = gridData.getGriddata();
        }
        Log.d("winter", "MyJsonUtil--getGridNews---------" + gridNews);
        return gridNews;
    }
}

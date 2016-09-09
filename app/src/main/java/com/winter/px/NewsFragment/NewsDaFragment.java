package com.winter.px.NewsFragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.winter.px.adapter.CustomFragmentAdapter;
import com.winter.px.bean.NewsDa;
import com.winter.px.niceview.R;
import com.winter.px.utils.DownLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peiyangyang on 2016/9/6.
 */
public class NewsDaFragment extends Fragment {
    PullToRefreshGridView gridView;
    private static int pagePosition = 1;
    List<NewsDa.ListBean> datas;
    Handler newHandler;
    CustomFragmentAdapter customAdapter;

    public NewsDaFragment getInstance(String url) {
        NewsDaFragment newsDaFragment = new NewsDaFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        Log.d("aaaaaaaaaaa", "--------------------");
        newsDaFragment.setArguments(bundle);
        return newsDaFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_custom, null);
        newHandler = new Handler();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final String apiUrl = getArguments().getString("url");
        gridView = (PullToRefreshGridView) view.findViewById(R.id.pullGridId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonString = DownLoadUtils.getJsonString(apiUrl + pagePosition);
                Log.d("winter", "=======aaaaajsonString======" + jsonString);
                Gson gson = new Gson();
                NewsDa newDa = gson.fromJson(jsonString, NewsDa.class);
                if (newDa != null) {
                    datas = newDa.getList();
                    Log.d("winter", "toString---------------" + newDa.getList().get(0).toString());

                    newHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            customAdapter = new CustomFragmentAdapter(datas, getContext());
                            gridView.setAdapter(customAdapter);

                        }
                    });
                }
            }
        }).start();


        gridView.setMode(PullToRefreshBase.Mode.BOTH);
        gridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                pagePosition = pagePosition + 1;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String jsonString = DownLoadUtils.getJsonString(apiUrl + pagePosition);
                        Gson gson = new Gson();
                        NewsDa newDa = gson.fromJson(jsonString, NewsDa.class);
                        datas.addAll(newDa.getList());
                        newHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                customAdapter.notifyDataSetChanged();
                                gridView.onRefreshComplete();
                            }
                        });
                    }
                }).start();
            }
        });
    }
}

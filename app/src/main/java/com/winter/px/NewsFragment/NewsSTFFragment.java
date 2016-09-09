package com.winter.px.NewsFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.winter.px.adapter.StfFragmentAdapter;
import com.winter.px.bean.NewsDa;
import com.winter.px.niceview.R;
import com.winter.px.utils.DownLoadUtils;
import com.winter.px.utils.HeadViewUtil;
import com.winter.px.utils.ThreadPoolUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by peiyangyang on 2016/9/8.
 */
public class NewsSTFFragment extends Fragment {
    PullToRefreshListView stfRefreshListView;
    private static int stfPagePosition = 1;
    List<NewsDa.ListBean> stfData;
    ExecutorService executorService;
    StfFragmentAdapter stfAdapter;
    String stfApiString = "";
    String headString = "";
    NewsDa stfNewsDa;
    HeadViewUtil addHeaderView = new HeadViewUtil();

    public NewsSTFFragment getInstance(String url,String headUrl) {
        NewsSTFFragment stfFragment = new NewsSTFFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("headUrl",headUrl);
        Log.d("sssssssss", "----");
        stfFragment.setArguments(bundle);
        return stfFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        stfData = new ArrayList<>();
        executorService = new ThreadPoolUtils().getThreadPool(2);
        return inflater.inflate(R.layout.stf_fragment, null);
    }

    Handler stfHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            stfNewsDa = (NewsDa) msg.obj;
            if (stfNewsDa != null) {
                stfData.addAll(stfNewsDa.getList());
                stfAdapter.notifyDataSetChanged();
                stfRefreshListView.onRefreshComplete();
            }


        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stfRefreshListView = (PullToRefreshListView) view.findViewById(R.id.stfRefreshListId);
        stfApiString = getArguments().getString("url").toString();
        headString = getArguments().get("headUrl").toString();
        //使用线程池下载字符串
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String stfJsonString = DownLoadUtils.getJsonString(stfApiString + stfPagePosition);
                Gson gson = new Gson();
                stfNewsDa = gson.fromJson(stfJsonString, NewsDa.class);

                //使用handler发送数据源
                Message message = Message.obtain();

                message.obj = stfNewsDa;
                stfHandler.sendMessage(message);
            }
        });
        stfAdapter = new StfFragmentAdapter(stfData, getActivity().getApplication(), executorService);
        stfRefreshListView.setAdapter(stfAdapter);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String stfJsonString = DownLoadUtils.getJsonString(stfApiString + stfPagePosition);
//                Gson gson = new Gson();
//                NewsDa stfNewsDa = gson.fromJson(stfJsonString, NewsDa.class);
//                stfData = stfNewsDa.getList();
//                //使用handler发送数据源
//                Message message = Message.obtain();
//                message.what = 1;
//                message.obj = stfData;
//                stfHandler.sendMessage(message);
//            }
//        }).start();
        //添加头部视图
        stfRefreshListView.getRefreshableView().addHeaderView(addHeaderView.getHeadView(
                getActivity().getSupportFragmentManager(),
                stfHandler,
                getContext(),
                headString));
        stfRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        stfRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                stfPagePosition = stfPagePosition + 1;
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        String stfJsonString = DownLoadUtils.getJsonString(stfApiString + stfPagePosition);
                        Gson gson = new Gson();
                        stfNewsDa = gson.fromJson(stfJsonString, NewsDa.class);
                        Message message = Message.obtain();
                        message.obj = stfNewsDa;
                        stfHandler.sendMessage(message);
                    }
                });
            }
        });

    }
}

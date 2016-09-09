package com.winter.px.NewsFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.winter.px.JsonUtils.ListNewsJson;
import com.winter.px.adapter.FirstNewsAdapter;
import com.winter.px.adapter.HeadViewPagerAdapter;
import com.winter.px.bean.GridNews;
import com.winter.px.bean.HeadView;
import com.winter.px.bean.ListNews;
import com.winter.px.niceview.R;
import com.winter.px.utils.DownLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peiyangyang on 2016/9/3.
 */
public class FirstFragment extends Fragment {

    ViewPager viewPager;
    PullToRefreshListView pullToListView;
    List<ListNews.ListdataBean> listNews;
    List<GridNews.GriddataBean> gridNews;
    List<HeadView> headData;
    List<Fragment> listFragment;
    String URL_STRING = "http://api.fengniao.com/app_ipad/news_jingxuan.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&page=";
    String HEAD_STRING = "http://api.fengniao.com/app_ipad/focus_pic.php?mid=19928?appImei=000000000000000&osType=Android&osVersion=4.1.1";
    private static int pagePosition = 1;
    FirstNewsAdapter adapter;
    HeadViewPagerAdapter viewPagerAdapter;
    Handler firstHandler = new Handler();
    private static int prePosition = 0;//记录上次被选择的页数

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.jx_item_listview, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pullToListView = (PullToRefreshListView) view.findViewById(R.id.pullTolistViewId);
        final String url = URL_STRING + pagePosition;
        //下载数据进行解析，适配
        listNews = new ArrayList<>();
        gridNews = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String str = DownLoadUtils.getJsonString(url);
                String listString = str.replace("160120", "listdata");
                String gridString = str.replace("280280", "griddata");
                Log.d("winter", "----listString----" + listString);
                //通过解析得到对象
                ListNewsJson newJson = new ListNewsJson();
                listNews = newJson.getListNews(listString);
                gridNews = newJson.getGridNews(gridString);
                Log.d("winter", "--------" + listNews);
                //使用handler发送对象
                firstHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new FirstNewsAdapter(gridNews, getContext(), listNews);
                        pullToListView.setAdapter(adapter);
                    }
                });

            }
        }).start();


        //添加头部视图
        View headInflater = LayoutInflater.from(getContext()).inflate(R.layout.head_viewpager, null);
        viewPager = (ViewPager) headInflater.findViewById(R.id.headViewPagerId);
        final LinearLayout pointFrame = (LinearLayout) headInflater.findViewById(R.id.doitPoint);
        pullToListView.getRefreshableView().addHeaderView(headInflater);
        listFragment = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String headString = DownLoadUtils.getJsonString(HEAD_STRING);
                Log.d("winter", "--===--headString--==---" + headString);
                Gson gson = new Gson();
                headData = gson.fromJson(headString, new TypeToken<List<HeadView>>() {
                }.getType());
                Log.d("winter", "--===--headData--==---" + headData);
                //添加保护，防止空指针
                if (headData!=null) {
                    firstHandler.post(new Runnable() {
                        @Override
                        public void run() {

                            for (int i = 0; i < headData.size(); i++) {
                                HeadFragment headFragment = new HeadFragment().
                                        getHeadFragment(headData.get(i).getTitle().toString(),
                                                headData.get(i).getPic_src().toString(),
                                                headData.get(i).getWeb_url().toString());
                                listFragment.add(headFragment);

                                View poitView = new View(getContext());
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(15, 15);
                                params.leftMargin = 8;
                                poitView.setLayoutParams(params);
                                poitView.setBackgroundResource(R.drawable.point_hui);
                                pointFrame.addView(poitView);

                                poitView.setTag(i);
                                //给view设置点击事件---点击后viewpager要联动
                                poitView.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        // TODO 用view来携带点击的位置信息,
                                        //将viewpager设置成点击的页面
                                        int pos = (Integer) v.getTag();
                                        viewPager.setCurrentItem(pos);
                                    }
                                });
                            }
                            //给第一个默认选中红色
                            pointFrame.getChildAt(0).setBackgroundResource(R.drawable.point_h);
                            viewPagerAdapter = new HeadViewPagerAdapter(getFragmentManager(), listFragment);
                            viewPager.setAdapter(viewPagerAdapter);
                        }
                    });
                }
            }
        }).start();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //将选择的viewpager页对应的导航圈设置为高亮
                pointFrame.getChildAt(position).
                        setBackgroundResource(R.drawable.point_h);
                //将上次选择的导航圈还原
                pointFrame.getChildAt(prePosition).
                        setBackgroundResource(R.drawable.point_hui);
                prePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                int num = 0;
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                    final int finalNum = num;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            viewPager.setCurrentItem(finalNum);
                        }
                    });
                    if (num > 2) {
                        num = 0;
                    } else {
                        num++;
                    }
                }
            }
        }).start();

        pullToListView.setMode(PullToRefreshBase.Mode.BOTH);
        //设置监听实现下拉，上拉刷新
        pullToListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                pagePosition = pagePosition + 1;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String pageString = DownLoadUtils.getJsonString(URL_STRING + pagePosition);
                        Log.d("win", "--------" + pagePosition);
                        String listPageString = pageString.replace("160120", "listdata");
                        String gridPageString = pageString.replace("280280", "griddata");
                        Log.d("winter", "----listString----" + listPageString);
                        //通过解析得到对象
                        ListNewsJson newJson = new ListNewsJson();
                        for (int i = 0; i < newJson.getListNews(listPageString).size(); i++) {
                            listNews.add(newJson.getListNews(listPageString).get(i));
                        }
                        for (int i = 0; i < newJson.getGridNews(gridPageString).size(); i++) {
                            gridNews.add(newJson.getGridNews(gridPageString).get(i));
                        }
                        Log.d("winter", "--------" + listNews);
                        //使用handler发送对象
                        firstHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                                pullToListView.onRefreshComplete();
                            }
                        });
                    }
                }).start();


            }
        });
    }


}

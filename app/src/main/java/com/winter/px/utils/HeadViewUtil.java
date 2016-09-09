package com.winter.px.utils;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winter.px.NewsFragment.HeadFragment;
import com.winter.px.adapter.HeadViewPagerAdapter;
import com.winter.px.bean.HeadView;
import com.winter.px.niceview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peiyangyang on 2016/9/7.
 */
public class HeadViewUtil {
    private ViewPager viewPager;
    private List<HeadView> headData;
    private List<Fragment> listFragment;
    private HeadViewPagerAdapter viewPagerAdapter;
    private LinearLayout pointLinear;
    private static int prePosition = 0;

    public View getHeadView(final FragmentManager fragmentManager, final Handler handler, final Context context, final String url) {
        View headInflater = LayoutInflater.from(context).inflate(R.layout.head_viewpager, null);
        pointLinear = (LinearLayout) headInflater.findViewById(R.id.doitPoint);
        viewPager = (ViewPager) headInflater.findViewById(R.id.headViewPagerId);
        listFragment = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String headString = DownLoadUtils.getJsonString(url);
                Gson gson = new Gson();
                try {
                    headData = gson.fromJson(headString, new TypeToken<List<HeadView>>() {
                    }.getType());
                } catch (RuntimeException e) {
                    return;
                }
                Log.d("winter", "--===--headData--==---" + headData);
                if (headData != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            for (int i = 0; i < headData.size(); i++) {
                                HeadFragment headFragment = new HeadFragment().
                                        getHeadFragment(headData.get(i).getTitle().toString(),
                                                headData.get(i).getPic_src().toString(),
                                                headData.get(i).getWeb_url().toString());
                                listFragment.add(headFragment);
                                View poitView = new View(context);
                                LinearLayout.LayoutParams params = new
                                        LinearLayout.LayoutParams(15, 15);
                                params.leftMargin = 8;
                                poitView.setLayoutParams(params);
                                poitView.setBackgroundResource(R.drawable.point_hui);
                                pointLinear.addView(poitView);
                                //设置tag记录当前view
                                poitView.getTag(i);
                                //设置监听----点击后viewpager要联动
                                poitView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //得到位置，设置联动
                                        int position = (Integer) v.getTag();
                                        viewPager.setCurrentItem(position);
                                    }
                                });

                            }
                            viewPagerAdapter = new HeadViewPagerAdapter(fragmentManager, listFragment);
                            viewPager.setAdapter(viewPagerAdapter);
                            pointLinear.getChildAt(0).setBackgroundResource(R.drawable.point_h);
                        }
                    });
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                int changeNum = 0;
                while (true) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int finalChangeNum = changeNum;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem(finalChangeNum);
                        }
                    });
                    if (changeNum > 2) {
                        changeNum = 0;
                    } else {
                        changeNum++;
                    }
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
                pointLinear.getChildAt(position).setBackgroundResource(R.drawable.point_h);
                //将上次选择的导航圈还原
                pointLinear.getChildAt(prePosition).setBackgroundResource(R.drawable.point_hui);
                prePosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return headInflater;
    }
}

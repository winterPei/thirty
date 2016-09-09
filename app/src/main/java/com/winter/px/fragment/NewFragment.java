package com.winter.px.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.winter.px.NewsFragment.NewsDaFragment;
import com.winter.px.NewsFragment.NewsSTFFragment;
import com.winter.px.adapter.FragmentAdapter;
import com.winter.px.niceview.R;

import java.util.ArrayList;
import java.util.List;

import com.winter.px.NewsFragment.FirstFragment;

/**
 * Created by peiyangyang on 2016/9/3.
 */
public class NewFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    ViewPager viewPager;
    RadioGroup newsRadioGroup;
    List<Fragment> datas;
    FragmentAdapter adapter;
    FirstFragment firFragment;
    String SECOND_STRING = "http://api.fengniao.com/app_ipad/news_list.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&cid=296&page=";
    String THRID_STRING = "http://api.fengniao.com/app_ipad/news_list.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&cid=190&page=";
    String FOUR_STRING = "http://api.fengniao.com/app_ipad/news_list.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&cid=192&page=";
    String SECOND_HEAD = "http://api.fengniao.com/app_ipad//focus_pic.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&mid=19929";
    String THRID_HEAD = "http://api.fengniao.com/app_ipad//focus_pic.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&mid=19930";
    String FOUR_HEAD = "http://api.fengniao.com/app_ipad//focus_pic.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&mid=19931";
    NewsSTFFragment sFragment,tFragment,fFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        newsRadioGroup = (RadioGroup) view.findViewById(R.id.radioGroupNews);
        newsRadioGroup.setOnCheckedChangeListener(this);
        datas = new ArrayList<>();
        //页面加载Fragment
        firFragment = new FirstFragment();
        sFragment = new NewsSTFFragment().getInstance(SECOND_STRING,SECOND_HEAD);
        tFragment = new NewsSTFFragment().getInstance(THRID_STRING,THRID_HEAD);
        fFragment = new NewsSTFFragment().getInstance(FOUR_STRING,FOUR_HEAD);
        datas.add(firFragment);
        datas.add(sFragment);
        datas.add(tFragment);
        datas.add(fFragment);
        adapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), datas);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton;
                for (int i = 0; i < newsRadioGroup.getChildCount(); i++) {
                    radioButton = (RadioButton) newsRadioGroup.getChildAt(i);
                    if (i == position) {
                        radioButton.setTextColor(Color.BLUE);
                    } else {
                        radioButton.setTextColor(Color.GRAY);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.choicenessRB:
                viewPager.setCurrentItem(0);
                break;
            case R.id.equipmentRB:
                viewPager.setCurrentItem(1);
                break;
            case R.id.affectRB:
                viewPager.setCurrentItem(2);
                break;
            case R.id.collegeRB:
                viewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }
}

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
import com.winter.px.adapter.FragmentAdapter;
import com.winter.px.niceview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peiyangyang on 2016/9/3.
 */
public class PicFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {
    ViewPager viewPager;
    RadioGroup radioGroup;
    List<Fragment> dataList;
    FragmentAdapter adapter;
    NewsDaFragment picCustomFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pic, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
        adapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), dataList);
        viewPager.setAdapter(adapter);
        //viewPager设置监听方法
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton radioButton;
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    radioButton = (RadioButton) radioGroup.getChildAt(i);
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
        radioGroup.setOnCheckedChangeListener(this);

    }

    String[] urlStrings = {"http://api.fengniao.com/app_ipad/pic_bbs_list_v2.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&fid=101&page=",
            "http://api.fengniao.com/app_ipad/pic_bbs_list_v2.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&fid=125&page=",
            "http://api.fengniao.com/app_ipad/pic_bbs_list_v2.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&fid=16&page=",
            "http://api.fengniao.com//app_ipad/pic_bbs_list_v2.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&fid=24&page="};

    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            picCustomFragment = new NewsDaFragment().getInstance(urlStrings[i]);
            dataList.add(picCustomFragment);
        }


    }

    private void initView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.picViewPagerId);
        radioGroup = (RadioGroup) view.findViewById(R.id.picRadioGroupId);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.protraitRB:
                viewPager.setCurrentItem(0);
                break;
            case R.id.sceneryRB:
                viewPager.setCurrentItem(1);
                break;
            case R.id.zoologyRB:
                viewPager.setCurrentItem(2);
                break;
            case R.id.digitalRB:
                viewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
    }
}

package com.winter.px.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.winter.px.adapter.BBSRightListViewAdapter;
import com.winter.px.niceview.R;
import com.winter.px.okActivity.BbsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peiyangyang on 2016/9/5.
 */
public class BbsFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView leftListView, rightListView;
    List<Map<String, Object>> datas;
    SimpleAdapter leftAdapter;
    List<String> rightDatas;
    BBSRightListViewAdapter rightAdapter;
    private static int leftPosition = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bbs, null);
    }

    String[] leftText = {"全部论坛", "题材作品区", "全部摄影区", "二手交易区", "全国分站区", "器材讨论区", "论坛服务区"};
    int[] leftImage = {R.drawable.bbs_icon_0,
            R.drawable.bbs_icon_1,
            R.drawable.bbs_icon_2,
            R.drawable.bbs_icon_3,
            R.drawable.bbs_icon_4,
            R.drawable.bbs_icon_5,
            R.drawable.bbs_icon_6};
    int[] backImage = {
            R.drawable.bbs_item_main_class_bg_selected,
            R.drawable.bbs_item_main_class_bg_normal,
            R.drawable.bbs_item_main_class_bg_normal,
            R.drawable.bbs_item_main_class_bg_normal,
            R.drawable.bbs_item_main_class_bg_normal,
            R.drawable.bbs_item_main_class_bg_normal,
            R.drawable.bbs_item_main_class_bg_normal
    };
    String[][] rightText = {{"热帖", "精华帖", "最新帖子", "最新回复"},
            {"人像", "风光", "纪实", "人体", "儿童", "人体", "建筑", "生态", "宠物"},
            {"商业", "女性视觉", "新手", "数码", "黑白", "实验", "生活摄影", "高校", "手机", "葡萄酒"},
            {"交易警示", "二手交易", "器材维修"},
            {"北京", "上海", "武汉"},
            {"单反和镜头", "大中画幅", "便携数码"},
            {"活动区", "网友服务", "蜂鸟茶馆"}};
    String bbs_url = "http://api.fengniao.com/app_ipad/bbs_all_hot.php?appImei=000000000000000&osType=Android&osVersion=4.1.1&page=";
    int[][] bbs_position = {{0, 1, 2, 3},
            {4, 5, 6, 7, 8, 9, 10, 11, 12},
            {13, 14, 15, 16, 17, 18, 19, 20, 21, 22},
            {23, 24, 25}, {26, 27, 28},
            {29, 30, 31}, {32, 33, 34}};

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        leftListView = (ListView) view.findViewById(R.id.listViewBbsLeft);
        rightListView = (ListView) view.findViewById(R.id.listViewBbsRight);
        initData();
        leftAdapter = new SimpleAdapter(getActivity().getApplicationContext(),
                datas, R.layout.bbs_left_listview,
                new String[]{"image", "text", "back"},
                new int[]{R.id.leftBBSImageViewId, R.id.leftBBSTextViewId, R.id.itemImageBG});
        leftListView.setAdapter(leftAdapter);

        leftListView.setOnItemClickListener(this);

        rightDatas = new ArrayList<>();
        for (int i = 0; i < rightText[0].length; i++) {
            rightDatas.add(rightText[0][i]);
        }
        Log.d("asdf", "------" + rightDatas);
        rightAdapter = new BBSRightListViewAdapter(rightDatas, getActivity().getApplicationContext());
        rightListView.setAdapter(rightAdapter);
        //
        rightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int num, long id) {
                TextView titleView = (TextView) rightListView.getChildAt(num).findViewById(R.id.rightBBSTextViewId);
                Intent intent = new Intent(getActivity(), BbsActivity.class);
                intent.putExtra("bbsString", bbs_url + bbs_position[leftPosition][num]);
                intent.putExtra("bbsTitle", titleView.getText());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        datas = new ArrayList<>();
        HashMap<String, Object> map;
        for (int i = 0; i < leftImage.length; i++) {
            map = new HashMap<>();
            map.put("image", leftImage[i]);
            map.put("text", leftText[i]);
            map.put("back", backImage[i]);
            datas.add(map);
        }
    }

    //设置点击启动RightListView
    private void startRightAdapter(int num) {
        rightDatas.clear();
        String[] numString = rightText[num];
        for (int i = 0; i < numString.length; i++) {
            rightDatas.add(numString[i]);
        }
        rightAdapter.notifyDataSetChanged();
    }

    //左边listview设置监听
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        leftPosition = position;
        ImageView imageViewBG;
        TextView leftTextView;
        for (int i = 0; i < leftListView.getChildCount(); i++) {
            imageViewBG = (ImageView) leftListView.getChildAt(i).findViewById(R.id.itemImageBG);
            leftTextView = (TextView) leftListView.getChildAt(i).findViewById(R.id.leftBBSTextViewId);
            if (i == position) {
                startRightAdapter(i);
                leftTextView.setTextColor(Color.RED);
                imageViewBG.setImageResource(R.drawable.bbs_item_main_class_bg_selected);
            } else {
                leftTextView.setTextColor(Color.BLACK);
                imageViewBG.setImageResource(R.drawable.bbs_item_main_class_bg_normal);
            }
        }
    }
}

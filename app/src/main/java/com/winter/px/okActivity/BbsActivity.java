package com.winter.px.okActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.winter.px.adapter.BbsActivityAdapter;
import com.winter.px.bean.BbsBean;
import com.winter.px.niceview.R;
import com.winter.px.utils.DownLoadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peiyangyang on 2016/9/8.
 */
public class BbsActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    Button button;
    ListView listView;
    TextView textView;
    List<BbsBean.ListBean> datas;
    private static String BBS_URL = "";
    BbsActivityAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bbs_activity);
        Intent intent = getIntent();
        String titleString = intent.getStringExtra("bbsTitle");
        BBS_URL = intent.getStringExtra("bbsString");
        Log.d("1111111","BBS_URL:"+BBS_URL);
        datas = new ArrayList<>();
        initView();
        initData();
        textView.setText(titleString);
        button.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    datas = (List<BbsBean.ListBean>) msg.obj;
                    adapter = new BbsActivityAdapter(datas,getApplicationContext());
                    listView.setAdapter(adapter);
                    break;
            }

        }
    };

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String json_BBS = DownLoadUtils.getJsonString(BBS_URL);
                Log.d("2222222222", "json_BBS-----" + json_BBS);
                Gson gson = new Gson();
                BbsBean bbsBean = gson.fromJson(json_BBS, BbsBean.class);
                if (bbsBean!=null){
                    datas.addAll(bbsBean.getList());
                }
                Log.d("3333333333", "bbsBean-----" + bbsBean);
                Message mseeage = Message.obtain();
                mseeage.what = 1;
                mseeage.obj = datas;
                handler.sendMessage(mseeage);
            }
        }).start();


    }

    private void initView() {
        button = (Button) findViewById(R.id.bbsActivityBackButton);
        listView = (ListView) findViewById(R.id.bbsActivityList);
        textView = (TextView) findViewById(R.id.bbsActivityTitle);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String web_Url = datas.get(position).getWeb_url().toString();
        Log.d("pppppppp",""+web_Url);
        Intent intent = new Intent(this,WebViewActivity.class);
        intent.putExtra("web_url",web_Url);
        startActivity(intent);
    }
}

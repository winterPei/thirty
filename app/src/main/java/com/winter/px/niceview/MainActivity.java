package com.winter.px.niceview;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.winter.px.fragment.BbsFragment;
import com.winter.px.fragment.NewFragment;
import com.winter.px.fragment.PicFragment;
import com.winter.px.fragment.SetFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    RadioGroup radioGroup;
    FrameLayout frameLayout;
    FragmentTransaction transaction;
    NewFragment newFragment;
    PicFragment picFragment;
    BbsFragment bbsFragment;
    SetFragment setFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayoutId,newFragment);
        transaction.commit();
    }

    private void initFragment() {
        newFragment = new NewFragment();
        picFragment = new PicFragment();
        bbsFragment = new BbsFragment();
        setFragment = new SetFragment();

    }

    private void initView() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroupId);
        radioGroup.setOnCheckedChangeListener(this);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayoutId);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        transaction = getSupportFragmentManager().beginTransaction();
        switch (checkedId) {
            case R.id.newsButton:
                transaction.replace(R.id.frameLayoutId,newFragment);
                break;
            case R.id.picButton:
                transaction.replace(R.id.frameLayoutId,picFragment);
                break;
            case R.id.bbsButton:
                transaction.replace(R.id.frameLayoutId,bbsFragment);
                break;
            case R.id.setButton:
                transaction.replace(R.id.frameLayoutId,setFragment);
                break;
            default:
                break;


        }
        transaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            twiceChoiceBack();//二次选择是否退出
        }
        return false;
    }
    private static Boolean isExit = false;
    private void twiceChoiceBack() {
        Timer timer = null;
        if (isExit==false){
            isExit=true;//准备退出
            Toast.makeText(this,"再按一次退出程序",Toast.LENGTH_SHORT).show();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit=false;// 取消退出
                }
            },2000);//如果两秒没有按下返回键，启动定时器取消当前执行任务
        }else{
            finish();
            System.exit(0);
        }
    }
}

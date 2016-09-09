package com.winter.px.okActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.winter.px.niceview.R;

/**
 * Created by peiyangyang on 2016/9/9.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
Button backButton,subButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_activity_register);
        initView();
    }

    private void initView() {
        backButton = (Button) findViewById(R.id.setRegBack);
        backButton.setOnClickListener(this);
        subButton = (Button) findViewById(R.id.submitReg);
        subButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setRegBack:
                finish();
                break;
            case R.id.submitReg:
                Toast.makeText(getApplicationContext(),"BaLa,BaLa....暂未开放！",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

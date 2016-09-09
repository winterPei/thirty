package com.winter.px.okActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.winter.px.niceview.R;

/**
 * Created by peiyangyang on 2016/9/7.
 */
public class RegActivity extends AppCompatActivity implements View.OnClickListener {
Button backButton,regButton,loginButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_activity);
        backButton = (Button) findViewById(R.id.regBackButton);
        backButton.setOnClickListener(this);
        regButton = (Button) findViewById(R.id.regBottomButton);
        regButton.setOnClickListener(this);
        loginButton = (Button) findViewById(R.id.redLoginButton);
        loginButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.regBackButton:
               finish();
               break;
           case R.id.regBottomButton:
               Intent intent = new Intent(this,RegisterActivity.class);
               startActivity(intent);
               break;
           case R.id.redLoginButton:

               break;
       }
    }
}

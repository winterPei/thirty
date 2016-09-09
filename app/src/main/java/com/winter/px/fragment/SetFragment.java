package com.winter.px.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.winter.px.niceview.R;
import com.winter.px.okActivity.RegActivity;

/**
 * Created by peiyangyang on 2016/9/7.
 */
public class SetFragment extends Fragment implements View.OnClickListener {
    ImageView loadImageButton;
    private static boolean flag = true;
    RelativeLayout oneRea, twoRea, threeRea, fourRea, fiveRea, sixRea, sevenRea, eightRea;
    Intent intent;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_fragment, null);
        loadImageButton = (ImageView) view.findViewById(R.id.autoLoadButton);
        oneRea = (RelativeLayout) view.findViewById(R.id.setFirstRelative);
        twoRea = (RelativeLayout) view.findViewById(R.id.setSecondRelative);
        threeRea = (RelativeLayout) view.findViewById(R.id.setThirdRelative);
        fourRea = (RelativeLayout) view.findViewById(R.id.setFourRelative);
        fiveRea = (RelativeLayout) view.findViewById(R.id.setFiveRelative);
        sixRea = (RelativeLayout) view.findViewById(R.id.setSixRelative);
        sevenRea = (RelativeLayout) view.findViewById(R.id.setSevenRelative);
        eightRea = (RelativeLayout) view.findViewById(R.id.setEightRelative);
        oneRea.setOnClickListener(this);
        twoRea.setOnClickListener(this);
        threeRea.setOnClickListener(this);
        fourRea.setOnClickListener(this);
        fiveRea.setOnClickListener(this);
        sixRea.setOnClickListener(this);
        sevenRea.setOnClickListener(this);
        eightRea.setOnClickListener(this);

        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    loadImageButton.setImageResource(R.drawable.set_switch_on);
                    flag = false;
                } else {
                    loadImageButton.setImageResource(R.drawable.set_switch_off);
                    flag = true;
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setFirstRelative:
                intent = new Intent(getActivity(), RegActivity.class);
//                intent = new Intent("com.winter.register");
                startActivity(intent);

                break;

        }
    }
}

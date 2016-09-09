package com.winter.px.NewsFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.winter.px.niceview.R;
import com.winter.px.okActivity.NewsWebViewActivity;
import com.winter.px.utils.DownLoadUtils;

/**
 * Created by peiyangyang on 2016/9/6.
 */
public class HeadFragment extends Fragment {


    Handler fragmentHandler = new Handler();
    public HeadFragment getHeadFragment(String text, String url,String webUrl) {
        HeadFragment headFragment = new HeadFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        bundle.putString("url", url);
        bundle.putString("web_url",webUrl);
        headFragment.setArguments(bundle);
        return headFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.first_header, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView imageView = (ImageView) view.findViewById(R.id.headImageId);
        TextView textView = (TextView) view.findViewById(R.id.headMsgId);
        if (getArguments() != null) {
            final String imsgeUrl = getArguments().getString("url");
            final String textTitle = getArguments().getString("text");
            final String web_url = getArguments().getString("web_url");
            textView.setText(textTitle);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    byte[] imageByte = DownLoadUtils.getImageByte(imsgeUrl);
                    Log.d("---headGragment----","---imsgeUrl---"+imsgeUrl+"---");
                    if (imageByte!=null) {
                        final Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                        fragmentHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                }
            }).start();
            //头部轮播图片设置监听
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), NewsWebViewActivity.class);
                    intent.putExtra("web_url",web_url);
                    intent.putExtra("title",textTitle);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
            });
        }


    }
}

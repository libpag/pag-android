package com.tencent.libpag.sample.libpag_sample;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MotionEvent;

import org.libpag.PAGImageView;

public class MultiplePAGImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_pagimageview);
        callFire();
    }

    void fireImageView(int id, String path) {
        PAGImageView view = findViewById(id);
        view.setPath(path);
        view.setRepeatCount(-1);
        view.play();
    }

    void callFire() {
        int ids[] = {R.id.pagView1, R.id.pagView2, R.id.pagView3, R.id.pagView4, R.id.pagView5,
                R.id.pagView6, R.id.pagView7, R.id.pagView8, R.id.pagView9, R.id.pagView10,
                R.id.pagView11, R.id.pagView12, R.id.pagView13, R.id.pagView14, R.id.pagView15,
                R.id.pagView16, R.id.pagView17, R.id.pagView18, R.id.pagView19, R.id.pagView20,
                R.id.pagView21, R.id.pagView22};
        for (int i = 0; i < ids.length; i++) {
            fireImageView(ids[i], "assets://" + (i + 1) + ".pag");
        }
    }
}

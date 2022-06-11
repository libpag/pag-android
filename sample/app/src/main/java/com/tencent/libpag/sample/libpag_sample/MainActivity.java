package com.tencent.libpag.sample.libpag_sample;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import org.libpag.PAGFile;
import org.libpag.PAGImage;
import org.libpag.PAGText;
import org.libpag.PAGView;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements SimpleListAdapter.ItemClickListener {

    private static final String[] items = new String[]{
            "A Simple PAG Animation",
            "Text Replacement",
            "Image Replacement",
            "Render Multiple PAG Files on A PAGView",
            "Create PAGSurface through texture ID",
            "Render an interval of the pag file"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        RecyclerView rv = findViewById(R.id.rv_);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(new SimpleListAdapter(items, this));
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 5:
                goToAPIsDetail(position);
                break;
            case 4:
                goToTestDetail(position);
                break;

            default:
                break;
        }
    }

    private void goToAPIsDetail(int position) {
        Intent intent = new Intent(MainActivity.this, APIsDetailActivity.class);
        intent.putExtra("API_TYPE", position);
        startActivity(intent);
    }

    private void goToTestDetail(int position) {
        Intent intent = new Intent(MainActivity.this, TextureDemoActivity.class);
        intent.putExtra("API_TYPE", position);
        startActivity(intent);
    }
}

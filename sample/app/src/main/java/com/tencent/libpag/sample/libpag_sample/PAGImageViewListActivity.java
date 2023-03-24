package com.tencent.libpag.sample.libpag_sample;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

public class PAGImageViewListActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        PAGImageViewRecyclerViewFragment fragment = new PAGImageViewRecyclerViewFragment();
        String[] path = new String[22];
        for (int i = 0; i < 22; i++) {
            path[i] = "assets://" + (i + 1) + ".pag";
        }
        fragment.setPaths(path);
        transaction.add(android.R.id.content, fragment);
        transaction.commit();
    }
}

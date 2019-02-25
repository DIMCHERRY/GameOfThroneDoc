package com.littlered.gameofthronedoc.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.littlered.gameofthronedoc.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

/**
 * author : littleredDLZ
 * date : 2018-12-21 13:54
 */
public class BaseFragment extends Fragment {
    Activity mActivity;
    AppCompatActivity mAppCompatActivity;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();

    }


    public Toolbar initToolbar(int toolbarId, int title) {
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) mActivity;
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(toolbarId);
        mAppCompatActivity.setSupportActionBar(toolbar);
        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        return toolbar;
    }
}

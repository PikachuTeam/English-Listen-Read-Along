package com.essential.englishlistenreadalong;

import android.os.Bundle;
import android.view.View;

import com.essential.englishlistenreadalong.app.BaseContentFragment;

import tatteam.com.app_common.ui.fragment.BaseFragment;

/**
 * Created by admin on 2/23/2016.
 */
public class TestFragment extends BaseContentFragment {
    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_test;
    }
    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
    }
    @Override
    protected int getTitleStringID() {
        return R.string.home;
    }
}

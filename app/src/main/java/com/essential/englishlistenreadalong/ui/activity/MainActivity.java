package com.essential.englishlistenreadalong.ui.activity;

import android.view.MenuItem;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.TestFragment;
import com.essential.englishlistenreadalong.app.BaseMenuActivity;
import com.essential.englishlistenreadalong.ui.fragment.HomeScreenFragment;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.ui.fragment.BaseFragment;

public class MainActivity extends BaseMenuActivity {



    @Override
    protected BaseFragment getFragmentContent() {
        return new HomeScreenFragment();
    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    protected int getToolBarID() {
        return R.id.toolbar;
    }

    @Override
    protected int getDrawerLayoutID() {
        return R.id.drawer_layout;
    }

    @Override
    protected int getNavigationViewID() {
        return R.id.nav_view;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.app_home:
                break;
            case R.id.downloadded:
                break;
            case R.id.my_playlist:
                break;
            case R.id.favorite:
                break;
            case R.id.history:
                break;
            case R.id.more_app:
                AppCommon.getInstance().openMoreAppDialog(MainActivity.this);
                break;
        }

        setSelectedItemMenu(id);
        closeMenu();
        return true;
    }
}

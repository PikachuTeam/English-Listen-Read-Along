package com.essential.englishlistenreadalong.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;

import com.essential.englishlistenreadalong.R;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.sqlite.DatabaseLoader;
import tatteam.com.app_common.ui.activity.BaseSplashActivity;


/**
 * Created by Thanh on 23/02/2016.
 */
public class SplashScreenActivity extends BaseSplashActivity {

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void onCreateContentView() {
    }

    @Override
    protected void onInitAppCommon() {
        AppCommon.getInstance().initIfNeeded(getApplicationContext());
        AppCommon.getInstance().increaseLaunchTime();
        DatabaseLoader.getInstance().createIfNeeded(getApplicationContext(), "englishListening.db");
    }

    @Override
    protected void onFinishInitAppCommon() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

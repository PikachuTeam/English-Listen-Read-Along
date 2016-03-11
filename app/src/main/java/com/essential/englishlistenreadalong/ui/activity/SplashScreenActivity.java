package com.essential.englishlistenreadalong.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;

import com.essential.englishlistenreadalong.R;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.sqlite.DatabaseLoader;
import tatteam.com.app_common.ui.activity.BaseSplashActivity;
import tatteam.com.app_common.ui.activity.EssentialSplashActivity;


/**
 * Created by Thanh on 23/02/2016.
 */
public class SplashScreenActivity extends EssentialSplashActivity {


    @Override
    protected void onCreateContentView() {
        super.onCreateContentView();
//        importDatabase();
    }

    @Override
    protected void onInitAppCommon() {
        AppCommon.getInstance().initIfNeeded(this);

        DatabaseLoader.getInstance().createIfNeeded(getApplicationContext(), "englishListening.db");
    }


    private void switchToMainActivity() {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        this.finish();
    }

    @Override
    protected void onFinishInitAppCommon() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}

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
    private boolean isDatabaseImported = false;
    private boolean isWaitingInitData = false;

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.activity_splash_screen;
    }

    @Override
    protected void onCreateContentView() {
        importDatabase();
    }

    @Override
    protected void onInitAppCommon() {
        AppCommon.getInstance().initIfNeeded(this);


    }

    private void importDatabase() {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                DatabaseLoader.getInstance().createIfNeeded(SplashScreenActivity.this, "englishListening.db");
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                isDatabaseImported = true;
                if (isWaitingInitData) {
                    switchToMainActivity();
                }
            }
        };
        task.execute();
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

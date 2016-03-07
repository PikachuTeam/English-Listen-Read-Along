package com.essential.englishlistenreadalong.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.essential.englishlistenreadalong.ui.activity.MainActivity;

/**
 * Created by admin on 3/1/2016.
 */
public class EssentialBroadcastReceiver extends BroadcastReceiver {
    private MainActivity activity;

    public EssentialBroadcastReceiver() {
    }

    public EssentialBroadcastReceiver(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String ACTION = intent.getAction();
        switch (ACTION) {
            case EssentialUtils.PLAY:
                onReceivePlay();
                break;
            case EssentialUtils.RESUME_PAUSE:
                onReceiveResumePause();
                break;
            case EssentialUtils.NEXT:
                onReceiveNext();
                break;
            case EssentialUtils.PREVIOUS:
                onReceivePre();
                break;
            case EssentialUtils.STOP:
                onReceiveStop();
                break;
            case EssentialUtils.LOOP:
                onReceiveLoop();
                break;
            case EssentialUtils.ARLAM:
                onReceiveArlam();
                break;
        }
    }

    private void onReceiveLoop() {
    }

    public void onReceivePlay() {
        activity.playerController.play();
    }

    public void onReceiveNext() {
        activity.playerController.next();
    }

    public void onReceivePre() {
        activity.playerController.previous();
    }

    public void onReceiveResumePause() {
        activity.playerController.resumePause();
    }

    public void onReceiveStop() {
        activity.playerController.stop();
    }

    public void onReceiveArlam() {
        if (activity.playerController.player.isPlaying())
            activity.playerController.stop();

//        Intent intent = new Intent(EssentialUtils.ARLAM);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(
//                activity, 0, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
//                + (5 * 1000), pendingIntent);
    }

    public void resigterBroadCast() {
        activity.registerReceiver(this,
                new IntentFilter(EssentialUtils.PLAY));
        activity.registerReceiver(this,
                new IntentFilter(EssentialUtils.RESUME_PAUSE));
        activity.registerReceiver(this,
                new IntentFilter(EssentialUtils.NEXT));
        activity.registerReceiver(this,
                new IntentFilter(EssentialUtils.PREVIOUS));
        activity.registerReceiver(this,
                new IntentFilter(EssentialUtils.STOP));
        activity.registerReceiver(this,
                new IntentFilter(EssentialUtils.LOOP));
    }
}

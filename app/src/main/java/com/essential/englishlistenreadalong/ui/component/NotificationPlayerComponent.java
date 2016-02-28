package com.essential.englishlistenreadalong.ui.component;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

/**
 * Created by hoaba_000 on 28/02/2016.
 */
public class NotificationPlayerComponent {
    private MainActivity activity;
    private RemoteViews remoteViews;

    public NotificationPlayerComponent(final MainActivity activity) {
        this.activity = activity;
        remoteViews = new RemoteViews(activity.getPackageName(),
                R.layout.component_mediaplayer_notification);
        registerReceiver();
    }

    public void showNotification() {
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(activity)
                .setSmallIcon(R.drawable.play)
                .setTicker("Trai tim ben le")
                .setAutoCancel(false)
                .setContent(remoteViews);
        if (activity.playerController.player.isPlaying())
            remoteViews.setImageViewResource(R.id.icon_btn_play, R.drawable.pause);
        else remoteViews.setImageViewResource(R.id.icon_btn_play, R.drawable.play);

        remoteViews.setImageViewResource(R.id.iv_icon_categories_notify, R.drawable.icon_animal);
        NotificationManager notificationmanager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationmanager.notify(0, builder.build());
    }

    public void registerReceiver() {
        PendingIntent p1 = PendingIntent.getBroadcast(activity, 0,
                new Intent("play_notifi"), 0);
        PendingIntent p2 = PendingIntent.getBroadcast(activity, 0,
                new Intent("next_notifi"), 0);
        PendingIntent p3 = PendingIntent.getBroadcast(activity, 0,
                new Intent("pre_notifi"), 0);
        activity.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (activity.playerController.player.isPlaying())
                    activity.playerController.pause();
                else activity.playerController.resume();
            }
        }, new IntentFilter("play_notifi"));
        activity.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (activity.playerController.player.isPlaying())
                    activity.playerController.pause();
                else activity.playerController.resume();
            }
        }, new IntentFilter("next_notifi"));
        activity.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (activity.playerController.player.isPlaying())
                    activity.playerController.pause();
                else activity.playerController.resume();
            }
        }, new IntentFilter("pre_notifi"));

        remoteViews.setOnClickPendingIntent(R.id.btn_pre, p3);
        remoteViews.setOnClickPendingIntent(R.id.btn_play, p1);
        remoteViews.setOnClickPendingIntent(R.id.btn_next, p2);
    }
}

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
import com.essential.englishlistenreadalong.app.EssentialUtils;
import com.essential.englishlistenreadalong.app.PlayerChangeListener;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

/**
 * Created by hoaba_000 on 28/02/2016.
 */
public class NotificationPlayerComponent implements PlayerChangeListener {
    private MainActivity activity;
    private RemoteViews remoteViews;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationmanager;

    public NotificationPlayerComponent(final MainActivity activity) {
        this.activity = activity;
        remoteViews = new RemoteViews(activity.getPackageName(),
                R.layout.component_mediaplayer_notification);
        builder = (NotificationCompat.Builder) new NotificationCompat.Builder(activity);
        notificationmanager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        registerReceiver();

    }

    public void showNotification() {
        if (activity.playerController.player.isPlaying()) {
            builder.setSmallIcon(R.drawable.music_note)
                    .setTicker("Trai tim ben le")
                    .setAutoCancel(false)
                    .setContent(remoteViews);
            remoteViews.setImageViewResource(R.id.icon_btn_play, R.drawable.pause);
        } else {
            builder.setSmallIcon(R.drawable.music_note_off)
                    .setAutoCancel(false)
                    .setContent(remoteViews);
            remoteViews.setImageViewResource(R.id.icon_btn_play, R.drawable.play);
        }
        remoteViews.setImageViewResource(R.id.iv_icon_categories_notify, R.drawable.icon_animal);
        notificationmanager.notify(0, builder.build());
    }

    public void removeNotification() {
        remoteViews.removeAllViews(R.layout.component_mediaplayer_notification);
        notificationmanager.cancelAll();
    }

    public void registerReceiver() {
        PendingIntent p1 = PendingIntent.getBroadcast(activity, 0,
                new Intent(EssentialUtils.PLAY_PAUSE), 0);
        PendingIntent p2 = PendingIntent.getBroadcast(activity, 0,
                new Intent(EssentialUtils.NEXT), 0);
        PendingIntent p3 = PendingIntent.getBroadcast(activity, 0,
                new Intent(EssentialUtils.PREVIOUS), 0);
        PendingIntent p4 = PendingIntent.getBroadcast(activity, 0,
                new Intent(EssentialUtils.STOP), 0);
        remoteViews.setOnClickPendingIntent(R.id.btn_stop, p4);
        remoteViews.setOnClickPendingIntent(R.id.btn_pre, p3);
        remoteViews.setOnClickPendingIntent(R.id.btn_play, p1);
        remoteViews.setOnClickPendingIntent(R.id.btn_next, p2);
    }

    @Override
    public void onPlayTrack(int position) {

    }

    @Override
    public void onPauseTrack() {

    }

    @Override
    public void onResumeTrack() {

    }

    @Override
    public void onStopTrack() {
        removeNotification();
    }

    @Override
    public void onNextTrack() {

    }

    @Override
    public void onPreviousTrack() {

    }

    @Override
    public void onChangeLoopAndShuffle() {

    }
}

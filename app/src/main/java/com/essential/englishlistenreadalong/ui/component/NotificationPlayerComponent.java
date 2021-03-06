package com.essential.englishlistenreadalong.ui.component;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.musicplayer.EssentialUtils;
import com.essential.englishlistenreadalong.listener.PlayerChangeListener;
import com.essential.englishlistenreadalong.entity.Audio;
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

    public void showNotificationPlayer() {
        if (activity.playerController.isPreparing) {
            if (activity.playerController.isPauseWhenPreparing) {
                builder.setSmallIcon(R.drawable.music_note_off)
                        .setAutoCancel(false)
                        .setContent(remoteViews);
                remoteViews.setImageViewResource(R.id.icon_btn_play, R.drawable.play);
            } else {
                builder.setSmallIcon(R.drawable.music_note)
                        .setTicker(activity.playerController.getAudioPlaying().nameAudio)
                        .setAutoCancel(false)
                        .setContent(remoteViews);
                remoteViews.setImageViewResource(R.id.icon_btn_play, R.drawable.pause);
            }
        } else {
            if (activity.playerController.player.isPlaying()) {
                builder.setSmallIcon(R.drawable.music_note)
                        .setTicker(activity.playerController.getAudioPlaying().nameAudio)
                        .setAutoCancel(false)
                        .setContent(remoteViews);
                remoteViews.setImageViewResource(R.id.icon_btn_play, R.drawable.pause);

            } else {
                builder.setSmallIcon(R.drawable.music_note_off)
                        .setAutoCancel(false)
                        .setContent(remoteViews);
                remoteViews.setImageViewResource(R.id.icon_btn_play, R.drawable.play);
            }

        }

        remoteViews.setImageViewResource(R.id.iv_icon_categories_notify, activity.playerController.getAudioPlaying().
                        getIconCategoryImage()
        );
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notificationmanager.notify(EssentialUtils.NOTIFICATION_MEDIAPLAYER, notification);
    }

    public void showNotificationPlayerStart() {
        builder.setSmallIcon(R.drawable.music_note)
                .setTicker(activity.playerController.getAudioPlaying().nameAudio)
                .setAutoCancel(false)
                .setContent(remoteViews);
        remoteViews.setImageViewResource(R.id.icon_btn_play, R.drawable.pause);
        remoteViews.setImageViewResource(R.id.iv_icon_categories_notify, activity.playerController.getAudioPlaying().getIconCategoryImage());
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_NO_CLEAR;
        notificationmanager.notify(EssentialUtils.NOTIFICATION_MEDIAPLAYER, notification);
    }


    public void removeNotificationMediaPlayer() {
        remoteViews.removeAllViews(R.layout.component_mediaplayer_notification);
        notificationmanager.cancel(EssentialUtils.NOTIFICATION_MEDIAPLAYER);
    }

    public void registerReceiver() {
        PendingIntent pIPause = PendingIntent.getBroadcast(activity, 0,
                new Intent(EssentialUtils.RESUME_PAUSE), 0);
        PendingIntent pIStop = PendingIntent.getBroadcast(activity, 0,
                new Intent(EssentialUtils.STOP), 0);
        remoteViews.setOnClickPendingIntent(R.id.btn_stop, pIStop);
        remoteViews.setOnClickPendingIntent(R.id.btn_play, pIPause);
    }


    @Override
    public void onPlayTrack(Audio audio) {
//        remoteViews.setImageViewResource(R.id.iv_icon_categories_notify, activity.playerController.getAudioPlaying().getIconCategoryImage(idCategory));
        remoteViews.setTextViewText(R.id.tv_notifi_audio_name, activity.playerController.getAudioPlaying().nameAudio);
        showNotificationPlayerStart();
    }

    @Override
    public void onResumePauseTrack() {
        showNotificationPlayer();
    }

    @Override
    public void onStopTrack() {
        removeNotificationMediaPlayer();
    }


    @Override
    public void onStartDownload() {

    }
}

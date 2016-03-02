package com.essential.englishlistenreadalong.app;

import android.content.Context;
import android.media.MediaPlayer;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.entity.Track;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;
import com.essential.englishlistenreadalong.ui.component.NotificationPlayerComponent;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by admin on 2/24/2016.
 */
public class EssentialPlayer {
    private MainActivity activity;
    public MediaPlayer player;
    public int playingTrackID = 0;
    public boolean isShuffle;
    public int repeat = 0;
    public ArrayList<Track> playingList;
    private ArrayList<PlayerChangeListener> listListener;
    private NotificationPlayerComponent notificationPlayerComponent;

    public void addPlayerChangeListenner(PlayerChangeListener listener) {
        if (!isHad(listener))
            this.listListener.add(listener);
    }

    public boolean isHad(PlayerChangeListener listener) {
        for (int i = 0; i < listListener.size(); i++) {
            if (listListener.get(i) == listener) return true;
        }
        return false;
    }

    public EssentialPlayer(MainActivity activity) {
        this.activity = activity;
        listListener = new ArrayList<PlayerChangeListener>();
        player = new MediaPlayer();
        notificationPlayerComponent = new NotificationPlayerComponent(this.activity);
        addPlayerChangeListenner(notificationPlayerComponent);
    }

    public void stop() {
        player.stop();
        try {
            player.prepare();
            player.seekTo(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < listListener.size(); i++)
            if (listListener.get(i) != null) {
                listListener.get(i).onStopTrack();
            }
    }

    public void pause() {
        player.pause();
        for (int i = 0; i < listListener.size(); i++)
            if (listListener.get(i) != null) {
                listListener.get(i).onPauseTrack();
            }
        notificationPlayerComponent.showNotification();
    }

    public void resume() {
        player.start();
        for (int i = 0; i < listListener.size(); i++)
            if (listListener.get(i) != null) {
                listListener.get(i).onResumeTrack();
            }
        notificationPlayerComponent.showNotification();
    }


    public void next() {

    }

    public void previous() {
    }


}

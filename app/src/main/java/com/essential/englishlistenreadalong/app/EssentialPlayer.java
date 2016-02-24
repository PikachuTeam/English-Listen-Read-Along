package com.essential.englishlistenreadalong.app;

import android.content.Context;
import android.media.MediaPlayer;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.essential.englishlistenreadalong.entity.Track;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

import java.util.ArrayList;

/**
 * Created by admin on 2/24/2016.
 */
public class EssentialPlayer {
    private MainActivity activity;
    private MediaPlayer player;
    private int playingTrackID = 0;
    public ArrayList<Track> playingList;
    public PlayerChangeListener listener;

    public void setPlayerChangeListenner(PlayerChangeListener listenner) {
        listenner = this.listener;
    }

    public EssentialPlayer(MainActivity activity) {
        this.activity = activity;
        player = new MediaPlayer();


    }

    public void stop() {
        player.stop();
        if (listener != null) {
            listener.onStopTrack();
        }
    }

    public void pause() {
        player.pause();
        if (listener != null) {
            listener.onPauseTrack();
        }
    }

    public void resume() {
        player.start();
        if (listener != null) {
            listener.onResumeTrack();
        }
    }


    public void next() {

    }

    public void previous() {
    }


}

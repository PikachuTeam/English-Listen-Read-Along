package com.essential.englishlistenreadalong.app;

import android.content.Context;
import android.media.MediaPlayer;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.entity.Track;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

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
        player = new MediaPlayer();
        listListener = new ArrayList<PlayerChangeListener>();
    }

    public void stop() {
        player.stop();
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
    }

    public void resume() {
        player.start();
        for (int i = 0; i < listListener.size(); i++)
            if (listListener.get(i) != null) {
                listListener.get(i).onResumeTrack();
            }
    }


    public void next() {

    }

    public void previous() {
    }


}

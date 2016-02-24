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
    private PhoneStateListener phoneStateListener;
    private boolean isMediaPlayerPaused = false;
    private TelephonyManager telephonyManager;
    private int playingTrackID = 0;
    public ArrayList<Track> playingList;
    public PlayerChangeListener listener;

    public void setPlayerChangeListenner(PlayerChangeListener listenner) {
        listenner = this.listener;
    }

    public EssentialPlayer(MainActivity activity) {
        activity = this.activity;
        player = new MediaPlayer();
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    player.pause();
                    isMediaPlayerPaused = true;
                } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                    player.start();
                    isMediaPlayerPaused = false;
                } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    player.pause();
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };

        telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

    }

    public void stop() {
        player.stop();
        if (listener != null) {
            listener.onStop();
        }
    }

    public void pause() {
        player.pause();
        if (listener != null) {
            listener.onPause();
        }
    }

    public void resume() {
        player.start();
        if (listener != null) {
            listener.onResume();
        }
    }


    public void next() {

    }

    public void previous() {
    }


}

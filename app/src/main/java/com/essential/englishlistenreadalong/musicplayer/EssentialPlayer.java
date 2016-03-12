package com.essential.englishlistenreadalong.musicplayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import com.essential.englishlistenreadalong.entity.Audio;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by admin on 2/24/2016.
 */
public class EssentialPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private MainActivity activity;
    public MediaPlayer player;
    public boolean isRepeat = false;
    public boolean isPreparing = false;
    public boolean isPauseWhenPreparing = false;
    public boolean isStopped = false;
    public ArrayList<Audio> listAudioPlaying;
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
        listListener = new ArrayList<PlayerChangeListener>();
        player = new MediaPlayer();

    }

    public void setUpNewPlaylist(ArrayList<Audio> listAudioPlaying) {
        this.listAudioPlaying = listAudioPlaying;
    }

    public Audio getAudioPlaying() {
        for (int i = 0; i < listAudioPlaying.size(); i++) {
            if (listAudioPlaying.get(i).playing) return listAudioPlaying.get(i);
        }
        return null;
    }

    public int getAudioPlayingPositionInList() {
        for (int i = 0; i < listAudioPlaying.size(); i++) {
            if (listAudioPlaying.get(i).playing) return i;
        }
        return 0;
    }

    public void play() {
        player.reset();
        resetBoolean();
        player.setOnCompletionListener(null);
        if (getAudioPlaying().isDownload == 0) {
            playOnline(getAudioPlaying());
        } else playOffline(getAudioPlaying());
        player.setOnPreparedListener(this);
        player.prepareAsync();
        isPreparing = true;
        for (int i = 0; i < listListener.size(); i++)
            if (listListener.get(i) != null) {
                listListener.get(i).onPlayTrack(getAudioPlaying());
            }
    }

    public void playOnline(final Audio audio) {
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            player.setDataSource(audio.url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playOffline(Audio audio) {
    }

    public void stop() {
        player.stop();
        player.setOnCompletionListener(null);
        player.reset();
        isStopped = true;
        for (int i = 0; i < listListener.size(); i++)
            if (listListener.get(i) != null) {
                listListener.get(i).onStopTrack();
            }
    }

    public void resetBoolean() {
        isPauseWhenPreparing = false;
        isPreparing = false;
        isStopped = false;
    }

    public void resumePause() {
        if (isStopped) play();
        else {
            if (isPreparing) {
                isPauseWhenPreparing = !isPauseWhenPreparing;
            } else {
                isPauseWhenPreparing = false;
                if (player.isPlaying()) {
                    player.pause();
                } else player.start();
            }
            for (int i = 0; i < listListener.size(); i++)
                if (listListener.get(i) != null) {
                    listListener.get(i).onResumePauseTrack();
                }
        }
    }


    public void next() {
        if (getAudioPlayingPositionInList() != listAudioPlaying.size() - 1) {
            setNewAudioPlayingPosition(getAudioPlayingPositionInList() + 1);
        } else setNewAudioPlayingPosition(0);
        play();
    }

    public void previous() {
        if (getAudioPlayingPositionInList() != 0) {
            setNewAudioPlayingPosition(getAudioPlayingPositionInList() - 1);
        }
        play();
    }

    public void setNewAudioPlayingPosition(int position) {

        for (int i = 0; i < listAudioPlaying.size(); i++) {
            listAudioPlaying.get(i).playing = false;
        }
        listAudioPlaying.get(position).playing = true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (isRepeat == true) {
            play();
        } else {
            if (getAudioPlayingPositionInList() == listAudioPlaying.size() - 1) {
                stop();
            } else {
                setNewAudioPlayingPosition(getAudioPlayingPositionInList() + 1);
                play();
            }

        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (!isPauseWhenPreparing) {
            player.start();
        }
        isPreparing = false;
        player.setOnCompletionListener(this);
        for (int i = 0; i < listListener.size(); i++)
            if (listListener.get(i) != null) {
                listListener.get(i).onResumePauseTrack();
            }
    }
}

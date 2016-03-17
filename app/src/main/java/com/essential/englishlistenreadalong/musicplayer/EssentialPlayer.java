package com.essential.englishlistenreadalong.musicplayer;

import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import com.essential.englishlistenreadalong.entity.Audio;
import com.essential.englishlistenreadalong.listener.PlayerChangeListener;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by admin on 2/24/2016.
 */
public class EssentialPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnBufferingUpdateListener {
    private MainActivity activity;
    public MediaPlayer player;
    public boolean isPreparing = false;
    public boolean isPauseWhenPreparing = false;
    public boolean isStopped = false;
    public ArrayList<Audio> listAudioPlaying;
    private ArrayList<PlayerChangeListener> listListener;
    private int SEEK_TIME = 5000;
    private SharedPreferences pre;
    private SharedPreferences.Editor edit;
    private String REPEAT = "repeat";


    public EssentialPlayer(MainActivity activity) {
        this.activity = activity;
        listListener = new ArrayList<PlayerChangeListener>();
        pre = activity.getSharedPreferences("data", activity.MODE_PRIVATE);
        edit = pre.edit();
        player = new MediaPlayer();

    }

    public void changeRepeatMode() {
        edit.putBoolean(REPEAT, !getRepeatMode());
        edit.commit();
    }

    public boolean getRepeatMode() {
        return pre.getBoolean(REPEAT, false);
    }

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
        player.setOnPreparedListener(this);
        player.prepareAsync();
        isPreparing = true;
    }

    public void playOffline(Audio audio) {
        String mediaPath = "sdcard/" + audio.idAudio + ".mp3";
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        Uri uri = Uri.parse(mediaPath);
        try {
            player.setDataSource(activity.getApplicationContext(), uri);

        } catch (IOException e) {
            e.printStackTrace();
        }
        player.setOnPreparedListener(this);
        try {
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isPreparing = true;
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


    public void seekForward() {
        int currentPosition = player.getCurrentPosition();
        if (currentPosition + SEEK_TIME <= player.getDuration()) {
            player.seekTo(currentPosition + SEEK_TIME);
        } else player.seekTo(player.getDuration());
    }

    public void seekBackward() {
        int currentPosition = player.getCurrentPosition();
        if (currentPosition - SEEK_TIME >= 0) {
            player.seekTo(currentPosition - SEEK_TIME);
        } else player.seekTo(0);
    }

    public void setNewAudioPlayingPosition(int position) {

        for (int i = 0; i < listAudioPlaying.size(); i++) {
            listAudioPlaying.get(i).playing = false;
        }
        listAudioPlaying.get(position).playing = true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (pre.getBoolean(REPEAT, false) == true) {
            player.seekTo(0);
            player.start();
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
        player.setOnBufferingUpdateListener(this);
        for (int i = 0; i < listListener.size(); i++)
            if (listListener.get(i) != null) {
                listListener.get(i).onResumePauseTrack();
            }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        activity.fullPlayer.updateSeekBar();
        activity.fullPlayer.updateBufferingSeekBar(percent);
    }
}

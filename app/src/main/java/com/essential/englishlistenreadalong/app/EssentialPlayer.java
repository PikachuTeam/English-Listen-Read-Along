package com.essential.englishlistenreadalong.app;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.essential.englishlistenreadalong.entity.Audio;
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
    public boolean isloop = false;
    public boolean isStreaming = false;
    public int repeat = 0;
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
        if (getAudioPlaying().isDownload == 0) {
            playOnline(getAudioPlaying());
        } else playOffline(getAudioPlaying());
        for (int i = 0; i < listListener.size(); i++)
            if (listListener.get(i) != null) {
                listListener.get(i).onPlayTrack(getAudioPlaying());
            }
    }

    public void playOnline(Audio audio) {
        isStreaming = true;
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            player.setDataSource(audio.url);

            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                    player.setOnPreparedListener(null);
                }
            });
            player.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void playOffline(Audio audio) {
        isStreaming = false;
    }

    public void stop() {
        player.stop();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                player.seekTo(0);
                player.setOnPreparedListener(null);
            }
        });
        if (isStreaming) {
            player.prepareAsync();
        } else try {
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < listListener.size(); i++)
            if (listListener.get(i) != null) {
                listListener.get(i).onStopTrack();
            }
    }


    public void resumePause() {
        if (player.isPlaying()) player.pause();
        else
            player.start();
        for (int i = 0; i < listListener.size(); i++)
            if (listListener.get(i) != null) {
                listListener.get(i).onResumePauseTrack();
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

}

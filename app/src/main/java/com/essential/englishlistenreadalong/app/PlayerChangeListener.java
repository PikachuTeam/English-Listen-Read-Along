package com.essential.englishlistenreadalong.app;

/**
 * Created by admin on 2/24/2016.
 */
public interface PlayerChangeListener {
    void onPlayTrack(int position);

    void onPauseTrack();

    void onResumeTrack();

    void onStopTrack();

    void onNextTrack();

    void onPreviousTrack();

    void onChangeLoopAndShuffle();


}

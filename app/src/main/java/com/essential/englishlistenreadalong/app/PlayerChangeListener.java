package com.essential.englishlistenreadalong.app;

/**
 * Created by admin on 2/24/2016.
 */
public interface PlayerChangeListener {
    void onPlay(int position);
    void onStop();
    void onPause();
    void onResume();
}

package com.essential.englishlistenreadalong.listener;

import com.essential.englishlistenreadalong.entity.Audio;

/**
 * Created by admin on 2/24/2016.
 */
public interface PlayerChangeListener {
    void onPlayTrack(Audio audio);

    void onResumePauseTrack();

    void onStopTrack();

    void onStartDownload();


}

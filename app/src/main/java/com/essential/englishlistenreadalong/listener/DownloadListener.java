package com.essential.englishlistenreadalong.listener;

import com.essential.englishlistenreadalong.entity.Audio;

/**
 * Created by admin on 3/17/2016.
 */
public interface DownloadListener {
    void onProgressDownload(Audio audio);
    void onNotifyDataChange(Boolean isHander);
}

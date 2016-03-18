package com.essential.englishlistenreadalong.database;

import android.os.AsyncTask;
import android.os.Handler;

import com.essential.englishlistenreadalong.entity.Audio;
import com.essential.englishlistenreadalong.listener.DownloadListener;
import com.essential.englishlistenreadalong.musicplayer.EssentialUtils;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by admin on 3/17/2016.
 */
public class DownloadManager {
    private MainActivity activity;
    private ArrayList<DownloadListener> listListener;
    private Handler mHandler;
    public int downloading = 0;

    public DownloadManager(MainActivity activity) {
        this.activity = activity;
        listListener = new ArrayList<DownloadListener>();
        mHandler = new Handler();
    }

    public void sendMessageUpdateUI() {
        for (int i = 0; i < listListener.size(); i++) {
            listListener.get(i).onNotifyDataChange(true);
        }
    }

    public void downloadAudio(final Audio audio) {
        runnableNotifyData();
        downloading++;
        Ion.with(activity)
                .load(audio.url)
                .progress(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        int progress = (int) ((downloaded * 100) / total);
                        if (progress >= 0)
                            audio.downloadPercent = progress;
                        else if (progress < 0) {
                            audio.downloadPercent = 0;
                        }
                        audio.isDownload = 2;
                        if (progress <= 100)
                            for (int i = 0; i < listListener.size(); i++) {
                                listListener.get(i).onProgressDownload(audio);
                            }
                    }
                })// write to a file
                .write(new File("/sdcard/"+ EssentialUtils.FOLDER_NAME + audio.idAudio + ".mp3"))
                        // run a callback on completion
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File result) {
                        if (audio.downloadPercent == 100) {
                            DataSource.updateDownloaded(audio.idAudio);
                            audio.isDownload = 1;
                            for (int i = 0; i < listListener.size(); i++) {
                                listListener.get(i).onProgressDownload(audio);
                            }
                        }
                        stopNotifidatachange();
                    }
                });
    }

    public void runnableNotifyData() {
        if (downloading == 0)
            mHandler.post(mRunnable);
        downloading++;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < listListener.size(); i++) {
                listListener.get(i).onNotifyDataChange(false);
            }
            mHandler.postDelayed(this, 500);
        }
    };

    public void stopNotifidatachange() {
        downloading--;
        if (downloading == 0) {
            AsyncTask asyncTask = new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);
                    mHandler.removeCallbacks(mRunnable);
                }
            };
            asyncTask.execute("");
        }
    }

    public void addDownloadListener(DownloadListener listener) {
        this.listListener.add(listener);
    }

}

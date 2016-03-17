package com.essential.englishlistenreadalong.ui.component;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.listener.PlayerChangeListener;
import com.essential.englishlistenreadalong.entity.Audio;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;


/**
 * Created by admin on 2/25/2016.
 */
public class FullPlayerComponent implements PlayerChangeListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    private LinearLayout btnBackDown;
    private TextView tvTitleInPlaylistFull;
    private TextView tvCategoriesInPlaylistFull;
    private LinearLayout btnSetting;
    private LinearLayout ln1;
    private LinearLayout btnDownloadInFullMode;
    private LinearLayout btnShare;
    private LinearLayout btnFavorite;
    private LinearLayout ln2;
    private LinearLayout page1Selected;
    private LinearLayout page2Selected;
    private LinearLayout btnRepeat;
    private LinearLayout btnPreviousFullMode;
    private LinearLayout btnPlayFullMode;
    private LinearLayout btnNextFullMode;
    private LinearLayout btnArlarm;
    private LinearLayout fullPlayer;
    private TextView currentTime, totalTime;
    private SeekBar seekBar;
    private RelativeLayout trickClickListenerLayout;
    private ImageView iconPlay, iconRepeat, iconArlarm, iconFavorite;
    private MainActivity activity;
    private Handler mHandler;

    public FullPlayerComponent(MainActivity mainActivity) {
        this.activity = mainActivity;
        mHandler = new Handler();
        setUp();

    }

    private void setUp() {
        iconPlay = (ImageView) activity.findViewById(R.id.icon_play_full_mode);
        iconArlarm = (ImageView) activity.findViewById(R.id.icon_arlam);
        iconRepeat = (ImageView) activity.findViewById(R.id.icon_repeat);
        iconFavorite = (ImageView) activity.findViewById(R.id.icon_favorite_full_mode);
        fullPlayer = (LinearLayout) activity.findViewById(R.id.full_player);
        btnBackDown = (LinearLayout) activity.findViewById(R.id.btn_back_down);
        tvTitleInPlaylistFull = (TextView) activity.findViewById(R.id.tv_Title_in_playlist_full);
        tvCategoriesInPlaylistFull = (TextView) activity.findViewById(R.id.tv_Categories_in_playlist_full);
        btnSetting = (LinearLayout) activity.findViewById(R.id.btn_setting);
        ln1 = (LinearLayout) activity.findViewById(R.id.ln1);
        btnDownloadInFullMode = (LinearLayout) activity.findViewById(R.id.btn_download_in_full_mode);
        btnShare = (LinearLayout) activity.findViewById(R.id.btn_share);
        btnFavorite = (LinearLayout) activity.findViewById(R.id.btn_add_to_favorite_in_full_mode);
        ln2 = (LinearLayout) activity.findViewById(R.id.ln2);
        page1Selected = (LinearLayout) activity.findViewById(R.id.page1_selected);
        page2Selected = (LinearLayout) activity.findViewById(R.id.page2_selected);
        btnRepeat = (LinearLayout) activity.findViewById(R.id.btn_repeat_full_mode);
        trickClickListenerLayout = (RelativeLayout) activity.findViewById(R.id.onClick_disable);
        btnPreviousFullMode = (LinearLayout) activity.findViewById(R.id.btn_previous_full_mode);
        btnPlayFullMode = (LinearLayout) activity.findViewById(R.id.btn_play_full_mode);
        btnNextFullMode = (LinearLayout) activity.findViewById(R.id.btn_next_full_mode);
        btnArlarm = (LinearLayout) activity.findViewById(R.id.btn_arlam_full_mode);
        seekBar = (SeekBar) activity.findViewById(R.id.seekBar);
        currentTime = (TextView) activity.findViewById(R.id.current_Time);
        totalTime = (TextView) activity.findViewById(R.id.total_Time);
        seekBar.setOnSeekBarChangeListener(this);
        trickClickListenerLayout.setOnClickListener(this);
        trickClickListenerLayout.setSoundEffectsEnabled(false);
        btnBackDown.setOnClickListener(FullPlayerComponent.this);
        btnSetting.setOnClickListener(FullPlayerComponent.this);
        btnPlayFullMode.setOnClickListener(FullPlayerComponent.this);
        btnPreviousFullMode.setOnClickListener(FullPlayerComponent.this);
        btnNextFullMode.setOnClickListener(FullPlayerComponent.this);
        btnRepeat.setOnClickListener(FullPlayerComponent.this);
        btnArlarm.setOnClickListener(FullPlayerComponent.this);
        fullPlayer.setVisibility(View.INVISIBLE);
        if (activity.playerController.getRepeatMode())
            iconRepeat.setBackgroundResource(R.drawable.repeat_once);
        else iconRepeat.setBackgroundResource(R.drawable.repeat);
    }

    public void updateSeekBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    public void updateBufferingSeekBar(int percent) {
            seekBar.setSecondaryProgress(percent);

    }

    private Runnable mUpdateTimeTask = new Runnable() {

        public void run() {

            long totalDuration = activity.playerController.player.getDuration();
            long currentDuration = activity.playerController.player.getCurrentPosition();

            totalTime.setText(getStringTime(totalDuration));
            currentTime.setText(getStringTime(currentDuration));
            int progress = (int) (getProgressPercentage(currentDuration, totalDuration));
            seekBar.setProgress(progress);

            mHandler.postDelayed(this, 100);
        }
    };


    public boolean isShow() {
        if (fullPlayer.getVisibility() == View.VISIBLE) return true;
        else return false;
    }

    public void show() {
        activity.smallPlayer.setOnclickListener(null);
        ObjectAnimator anim = ObjectAnimator.ofFloat(fullPlayer, "y", fullPlayer.getY() + fullPlayer.getHeight(), fullPlayer.getY());
        anim.setDuration(300);
        anim.start();
        fullPlayer.setVisibility(View.VISIBLE);
        tvTitleInPlaylistFull.setText(activity.playerController.getAudioPlaying().nameAudio);
    }

    public void hide() {
        final ObjectAnimator anim = ObjectAnimator.ofFloat(fullPlayer, "y", fullPlayer.getY(), fullPlayer.getY() + fullPlayer.getHeight());
        anim.setDuration(300);

        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                activity.smallPlayer.setOnclickListener(activity.smallPlayer);
                fullPlayer.setY(0);
                fullPlayer.setVisibility(View.INVISIBLE);
                activity.smallPlayer.show();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        anim.start();
    }


    @Override
    public void onPlayTrack(Audio audio) {
        iconPlay.setBackgroundResource(R.drawable.pause_circle);
        tvTitleInPlaylistFull.setText(activity.playerController.getAudioPlaying().nameAudio);
        tvCategoriesInPlaylistFull.setText(activity.playerController.getAudioPlaying().getCategoryName());
    }


    @Override
    public void onResumePauseTrack() {
        if (activity.playerController.isPreparing) {
            if (activity.playerController.isPauseWhenPreparing) {
                iconPlay.setBackgroundResource(R.drawable.play_circle);
            } else {
                iconPlay.setBackgroundResource(R.drawable.pause_circle);
            }
        } else {
            if (activity.playerController.player.isPlaying()) {
                iconPlay.setBackgroundResource(R.drawable.pause_circle);
            } else {
                iconPlay.setBackgroundResource(R.drawable.play_circle);
            }

        }


    }

    @Override
    public void onStopTrack() {
        iconPlay.setBackgroundResource(R.drawable.play_circle);
        seekBar.setSecondaryProgress(0);

    }


    @Override
    public void onStartDownload() {


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_back_down:
                hide();
                break;
            case R.id.btn_setting:
                break;
            case R.id.btn_play_full_mode:
                activity.sendMessageOnPauseResume();
                break;
            case R.id.btn_previous_full_mode:
                activity.sendMessageBackward();
                break;
            case R.id.btn_next_full_mode:
                activity.sendMessageForward();
                break;
            case R.id.btn_repeat_full_mode:
                activity.playerController.changeRepeatMode();
                if (activity.playerController.getRepeatMode())
                    iconRepeat.setBackgroundResource(R.drawable.repeat_once);
                else iconRepeat.setBackgroundResource(R.drawable.repeat);
                break;
            case R.id.btn_arlam_full_mode:
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateTimeTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        int totalDuration = activity.playerController.player.getDuration();
        int currentDuration = progressToTimer(seekBar.getProgress(), totalDuration);
        activity.playerController.player.seekTo(currentDuration);
        updateSeekBar();
    }

    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);
        percentage = (((double) currentSeconds) / totalSeconds) * 100;
        return percentage.intValue();
    }

    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        return currentDuration * 1000;
    }

    private String getStringTime(long duration) {
        String newTime;
        long totalSec = duration / 1000;
        long minute = totalSec / 60;
        long sec = totalSec % 60;
        if (minute > 100) return ("--:--");
        if (minute < 10) {
            if (sec < 10) {
                newTime = "0" + minute + ":0" + sec;
                return newTime;
            } else {
                newTime = "0" + minute + ":" + sec;
                return newTime;
            }
        } else {
            if (sec < 10) {
                newTime = minute + ":0" + sec;
                return newTime;
            } else {
                newTime = minute + ":" + sec;
                return newTime;
            }
        }


    }

}


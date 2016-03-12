package com.essential.englishlistenreadalong.ui.component;

import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.musicplayer.CustomSeekBar;
import com.essential.englishlistenreadalong.musicplayer.PlayerChangeListener;
import com.essential.englishlistenreadalong.entity.Audio;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

/**
 * Created by admin on 2/24/2016.
 */
public class SmallPlayerComponent implements PlayerChangeListener, View.OnClickListener {
    private MainActivity activity;
    public LinearLayout btnPlay, btnNext, btnPre, player;
    private ImageView iconPlay, iconCategory, iconNext, iconPrevious;
    private TextView tvTitle, tvCategory;
    private LinearLayout viewSmallPlayer;
    private ObjectAnimator animRotateIconCategory;
    private Runnable runnable;
    private Handler seekbarHandler = new Handler();
    private CustomSeekBar customSeekbar;

    public SmallPlayerComponent(MainActivity activity) {
        this.activity = activity;
        setup();
    }


    private void setup() {
        viewSmallPlayer = (LinearLayout) activity.findViewById(R.id.mediaplayer_small_view);
        btnPlay = (LinearLayout) activity.findViewById(R.id.btn_play_small_player);
        btnNext = (LinearLayout) activity.findViewById(R.id.btn_next_small_player);
        btnPre = (LinearLayout) activity.findViewById(R.id.btn_previous_small_player);
        player = (LinearLayout) activity.findViewById(R.id.view_player_small);
        iconPlay = (ImageView) activity.findViewById(R.id.iv_icon_play_small_player);
        iconNext = (ImageView) activity.findViewById(R.id.iv_icon_next_small_player);
        iconPrevious = (ImageView) activity.findViewById(R.id.iv_icon_previous_small_player);
        iconCategory = (ImageView) activity.findViewById(R.id.iv_icon_categories);
        tvCategory = (TextView) activity.findViewById(R.id.tv_Categories_in_playlist_small);
        tvTitle = (TextView) activity.findViewById(R.id.tv_Title_in_playlist_small);

        Typeface UTM_Cafeta = Typeface.createFromAsset(activity.getAssets(), "fonts/cafeta.ttf");
        tvTitle.setTypeface(UTM_Cafeta);
        tvCategory.setTypeface(UTM_Cafeta);

        setOnclickListener(SmallPlayerComponent.this);
        viewSmallPlayer.setVisibility(View.INVISIBLE);

        animRotateIconCategory = ObjectAnimator.ofFloat(iconCategory, "rotation", 0.0f, 360f);
        animRotateIconCategory.setDuration(10000);
        animRotateIconCategory.setRepeatCount(Integer.MAX_VALUE);
        customSeekbar = (CustomSeekBar) activity.findViewById(R.id.seekBar_Custom);
        customSeekbar.setup();
    }

    public void setOnclickListener(View.OnClickListener listener) {
        btnPlay.setOnClickListener(listener);
        btnNext.setOnClickListener(listener);
        btnPre.setOnClickListener(listener);
        player.setOnClickListener(listener);
    }

    private void updateSeekBar() {

        if (runnable == null) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    float percent = (float) activity.playerController.player.getCurrentPosition() / (float) activity.playerController.player.getDuration();
                    if (percent > 0) {
                        customSeekbar.updateIndicator(percent);

                    }
                    seekbarHandler.postDelayed(runnable, 30);
                }
            };
            seekbarHandler.postDelayed(runnable, 30);

        }
    }


    public void resumeRotate() {
        if (!animRotateIconCategory.isStarted()) {
            animRotateIconCategory.start();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (animRotateIconCategory.isPaused()) {
                animRotateIconCategory.resume();
            }
        }
    }

    public void pauseRotate() {
        if (animRotateIconCategory.isStarted())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                animRotateIconCategory.pause();
            } else animRotateIconCategory.end();

    }

    public void stopRotate() {
        animRotateIconCategory.end();
    }


    @Override
    public void onPlayTrack(Audio audio) {
        if (viewSmallPlayer.getVisibility() != View.VISIBLE) show();
        iconCategory.setBackgroundResource(activity.playerController.getAudioPlaying().getIconCategoryImage());
        iconPlay.setBackgroundResource(R.drawable.pause_circle);
        tvTitle.setText(activity.playerController.getAudioPlaying().nameAudio);
        tvCategory.setText(activity.playerController.getAudioPlaying().getCategoryName());
        resumeRotate();
        customSeekbar.updateIndicator(0);

    }


    @Override
    public void onResumePauseTrack() {
        if (activity.playerController.isPreparing) {
            if (activity.playerController.isPauseWhenPreparing) {
                iconPlay.setBackgroundResource(R.drawable.play_circle);
                pauseRotate();
            } else {
                iconPlay.setBackgroundResource(R.drawable.pause_circle);
                resumeRotate();
            }
        } else {
            if (activity.playerController.player.isPlaying()) {
                iconPlay.setBackgroundResource(R.drawable.pause_circle);
                resumeRotate();

            } else {
                iconPlay.setBackgroundResource(R.drawable.play_circle);
                pauseRotate();
            }

        }
        updateSeekBar();
    }

    @Override
    public void onStopTrack() {
        iconPlay.setBackgroundResource(R.drawable.play_circle);
        stopRotate();
        customSeekbar.updateIndicator(0);
    }


    @Override
    public void onStartDownload() {


    }

    public void show() {
        if (viewSmallPlayer.getVisibility() != View.VISIBLE) {
            activity.marginLayout.setVisibility(View.VISIBLE);
            ObjectAnimator anim = ObjectAnimator.ofFloat(viewSmallPlayer, "y", viewSmallPlayer.getY() + viewSmallPlayer.getHeight(), viewSmallPlayer.getY());
            anim.setDuration(250);
            anim.start();
            viewSmallPlayer.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_play_small_player:
                activity.sendMessageOnPauseResume();
                break;
            case R.id.btn_next_small_player:
                activity.playerController.next();
                break;
            case R.id.btn_previous_small_player:
                activity.playerController.previous();
                break;
            case R.id.view_player_small:
                activity.fullPlayer.show();
                break;
        }
    }
}

package com.essential.englishlistenreadalong.ui.component;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.app.PlayerChangeListener;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

/**
 * Created by admin on 2/25/2016.
 */
public class FullPlayerComponent implements PlayerChangeListener, View.OnClickListener {
    private LinearLayout btnBackDown;
    private TextView tvTitleInPlaylistFull;
    private TextView tvCategoriesInPlaylistFull;
    private LinearLayout btnSetting;
    private LinearLayout ln1;
    private LinearLayout btnDownloadInFullMode;
    private LinearLayout btnAddMyList;
    private LinearLayout btnAddToFavoriteInFullMode;
    private LinearLayout ln2;
    private LinearLayout page1Selected;
    private LinearLayout page2Selected;
    private LinearLayout btnShuffleFullMode;
    private LinearLayout btnPreviousFullMode;
    private LinearLayout btnPlayFullMode;
    private LinearLayout btnNextFullMode;
    private LinearLayout btnRepeatFullMode;
    private LinearLayout fullPlayer;
    private ImageView iconPlay, iconShuffle, iconRepeat;
    private MainActivity activity;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-02-25 15:31:20 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    public FullPlayerComponent(MainActivity mainActivity) {
        this.activity = mainActivity;
        setUp();

    }


    private void setUp() {
        iconPlay = (ImageView) activity.findViewById(R.id.icon_play_full_mode);
        iconRepeat = (ImageView) activity.findViewById(R.id.icon_repeat_full_mode);
        iconShuffle = (ImageView) activity.findViewById(R.id.icon_shuffle_full_mode);
        fullPlayer = (LinearLayout) activity.findViewById(R.id.full_player);
        btnBackDown = (LinearLayout) activity.findViewById(R.id.btn_back_down);
        tvTitleInPlaylistFull = (TextView) activity.findViewById(R.id.tv_Title_in_playlist_full);
        tvCategoriesInPlaylistFull = (TextView) activity.findViewById(R.id.tv_Categories_in_playlist_full);
        btnSetting = (LinearLayout) activity.findViewById(R.id.btn_setting);
        ln1 = (LinearLayout) activity.findViewById(R.id.ln1);
        btnDownloadInFullMode = (LinearLayout) activity.findViewById(R.id.btn_download_in_full_mode);
        btnAddMyList = (LinearLayout) activity.findViewById(R.id.btn_add_my_list);
        btnAddToFavoriteInFullMode = (LinearLayout) activity.findViewById(R.id.btn_add_to_favorite_in_full_mode);
        ln2 = (LinearLayout) activity.findViewById(R.id.ln2);
        page1Selected = (LinearLayout) activity.findViewById(R.id.page1_selected);
        page2Selected = (LinearLayout) activity.findViewById(R.id.page2_selected);
        btnShuffleFullMode = (LinearLayout) activity.findViewById(R.id.btn_shuffle_full_mode);
        btnPreviousFullMode = (LinearLayout) activity.findViewById(R.id.btn_previous_full_mode);
        btnPlayFullMode = (LinearLayout) activity.findViewById(R.id.btn_play_full_mode);
        btnNextFullMode = (LinearLayout) activity.findViewById(R.id.btn_next_full_mode);
        btnRepeatFullMode = (LinearLayout) activity.findViewById(R.id.btn_repeat_full_mode);
        btnBackDown.setOnClickListener(FullPlayerComponent.this);
        btnSetting.setOnClickListener(FullPlayerComponent.this);
        btnPlayFullMode.setOnClickListener(FullPlayerComponent.this);
        btnPreviousFullMode.setOnClickListener(FullPlayerComponent.this);
        btnNextFullMode.setOnClickListener(FullPlayerComponent.this);
        btnShuffleFullMode.setOnClickListener(FullPlayerComponent.this);
        btnRepeatFullMode.setOnClickListener(FullPlayerComponent.this);
        fullPlayer.setVisibility(View.INVISIBLE);

    }

    public void show() {
        activity.smallPlayer.setOnclickListener(null);
        ObjectAnimator anim = ObjectAnimator.ofFloat(fullPlayer, "y", fullPlayer.getY() + fullPlayer.getHeight(), fullPlayer.getY());
        anim.setDuration(300);
        anim.start();
        fullPlayer.setVisibility(View.VISIBLE);

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
    public void onPlayTrack(int position) {

    }

    @Override
    public void onPauseTrack() {
        iconPlay.setBackgroundResource(R.drawable.play_circle);
    }

    @Override
    public void onResumeTrack() {
        iconPlay.setBackgroundResource(R.drawable.pause_circle);

    }

    @Override
    public void onStopTrack() {

    }


    @Override
    public void onNextTrack() {

    }

    @Override
    public void onPreviousTrack() {

    }

    @Override
    public void onChangeLoopAndShuffle() {
        switch (activity.playerController.repeat) {
            case 0:
                iconRepeat.setBackgroundResource(R.drawable.repeat);
                iconRepeat.setAlpha((float) 0.5);
                break;
            case 1:
                iconRepeat.setBackgroundResource(R.drawable.repeat);
                break;
            case 2:
                iconRepeat.setBackgroundResource(R.drawable.repeat_once);

                break;
        }
        if (activity.playerController.isShuffle) {
            iconShuffle.setBackgroundResource(R.drawable.shuffle_variant);
        } else {
            iconShuffle.setBackgroundResource(R.drawable.shuffle_variant);
            iconShuffle.setAlpha((float) 0.5);
        }
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
                activity.sendMessageOnPlay();
                break;
            case R.id.btn_previous_full_mode:
                break;
            case R.id.btn_next_full_mode:
                break;
            case R.id.btn_shuffle_full_mode:
                break;
            case R.id.btn_repeat_full_mode:
                break;
        }
    }
}


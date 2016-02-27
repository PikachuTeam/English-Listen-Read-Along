package com.essential.englishlistenreadalong.ui.component;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.app.PlayerChangeListener;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

/**
 * Created by admin on 2/24/2016.
 */
public class SmallPlayerComponent implements PlayerChangeListener, View.OnClickListener {
    private MainActivity activity;
    public LinearLayout btnPlay, btnNext, btnPre, player;
    private ImageView iconPlay, iconCategory, iconNext, iconPrevious;
    private TextView tvTitle, tvCategory;
    private RelativeLayout viewSmallPlayer;
    private ObjectAnimator animRotateIconCategory;

    public SmallPlayerComponent(MainActivity activity) {
        this.activity = activity;
        setup();
    }


    private void setup() {
        viewSmallPlayer = (RelativeLayout) activity.findViewById(R.id.small_player);
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

        setOnclickListener(SmallPlayerComponent.this);
        viewSmallPlayer.setVisibility(View.INVISIBLE);
        animRotateIconCategory = ObjectAnimator.ofFloat(iconCategory, "rotation", 0.0f, 360f);
        animRotateIconCategory.setDuration(10000);
        animRotateIconCategory.setRepeatCount(Integer.MAX_VALUE);
    }

    public void setOnclickListener(View.OnClickListener listener) {
        btnPlay.setOnClickListener(listener);
        btnNext.setOnClickListener(listener);
        btnPre.setOnClickListener(listener);
        player.setOnClickListener(listener);
    }

    @Override
    public void onPlayTrack(int position) {
//activity.playerController.playingList.get(position)
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

    private void sendMessage() {
        Intent intent = new Intent("play_pause");
        // You can also include some extra data.
        intent.putExtra("message", "This is my message!");
        LocalBroadcastManager.getInstance(this.activity).sendBroadcast(intent);
    }

    @Override
    public void onPauseTrack() {
        iconPlay.setBackgroundResource(R.drawable.play_circle);
        pauseRotate();

    }

    @Override
    public void onResumeTrack() {
        iconPlay.setBackgroundResource(R.drawable.pause_circle);
        resumeRotate();

    }

    @Override
    public void onStopTrack() {
        iconPlay.setBackgroundResource(R.drawable.play_circle);
    }

    @Override
    public void onNextTrack() {

    }

    @Override
    public void onPreviousTrack() {

    }

    @Override
    public void onChangeLoopAndShuffle() {


    }

    public void show() {
        if (viewSmallPlayer.getVisibility() != View.VISIBLE) {
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
                sendMessage();
                break;
            case R.id.btn_next_small_player:
                break;
            case R.id.btn_previous_small_player:
                break;
            case R.id.view_player_small:
                activity.fullPlayer.show();
                break;
        }
    }
}

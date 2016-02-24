package com.essential.englishlistenreadalong.ui.component;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

/**
 * Created by admin on 2/24/2016.
 */
public class SmallPlayerComponent {
    private MainActivity activity;
    public LinearLayout btnPlay, btnNext, btnPre, player;
    private ImageView iconPlay, iconCategory;
    private TextView tvTitle, tvCategory;

    public SmallPlayerComponent(MainActivity activity) {
        activity = this.activity;
        setup();
    }


    public void setup() {
        btnPlay = (LinearLayout) activity.findViewById(R.id.btn_play_small_player);
        btnNext = (LinearLayout) activity.findViewById(R.id.btn_next_small_player);
        btnPre = (LinearLayout) activity.findViewById(R.id.btn_previous_small_player);
        player = (LinearLayout) activity.findViewById(R.id.view_player_small);


        iconPlay = (ImageView) activity.findViewById(R.id.iv_icon_play_small_player);
        iconCategory = (ImageView) activity.findViewById(R.id.iv_icon_categories);

        tvCategory = (TextView) activity.findViewById(R.id.tv_Categories);
        tvTitle = (TextView) activity.findViewById(R.id.tv_Title);
        btnPlay.setOnClickListener(activity);
        btnPre.setOnClickListener(activity);
        btnNext.setOnClickListener(activity);
        player.setOnClickListener(activity);

    }
    public void updateUI() {

    }
}

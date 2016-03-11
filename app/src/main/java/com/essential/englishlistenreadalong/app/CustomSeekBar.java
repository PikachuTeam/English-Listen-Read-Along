package com.essential.englishlistenreadalong.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.essential.englishlistenreadalong.R;

/**
 * Created by Thanh on 20/09/2015.
 */
public class CustomSeekBar extends RelativeLayout {
    private RelativeLayout indicatorView;

    public CustomSeekBar(Context context) {
        super(context);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setup() {
        this.post(new Runnable() {
            @Override
            public void run() {
                setupIndicator();
            }
        });
    }

    private void setupIndicator() {
        indicatorView = new RelativeLayout(getContext());
        indicatorView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT));
        indicatorView.setBackgroundResource(R.color.colorPrimaryLight);

        CustomSeekBar.this.addView(indicatorView);
    }


    public void updateIndicator(float percent) {
        if (indicatorView != null)
            indicatorView.setLayoutParams(new LayoutParams((int) (percent * (float) this.getWidth()), ViewGroup.LayoutParams.MATCH_PARENT));
    }


}

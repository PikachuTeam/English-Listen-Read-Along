package com.essential.englishlistenreadalong.app;

import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

import tatteam.com.app_common.ui.fragment.BaseFragment;

/**
 * Created by admin on 2/23/2016.
 */
public abstract class BaseContentFragment extends BaseFragment {
    public abstract String getTitleString();

    private MainActivity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (MainActivity) getActivity();
        updateToolBar();
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
    }

    public void updateToolBar() {
        activity.toolbar.setTitle(getTitleString());
        activity.toolbar.setNavigationIcon(R.drawable.menu);
        activity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openMenu();
            }
        });
    }

    public void showNotification(int stringID) {
        BaseMenuActivity activity = (BaseMenuActivity) getActivity();
        activity.showNotification(stringID);
    }

    public void setPlayerChangeListener(PlayerChangeListener listener) {
        MainActivity activity = (MainActivity) getActivity();
        activity.playerController.addPlayerChangeListenner(listener);
    }


}

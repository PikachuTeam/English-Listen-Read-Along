package com.essential.englishlistenreadalong.app;

import android.os.Bundle;

import com.essential.englishlistenreadalong.ui.activity.MainActivity;

import tatteam.com.app_common.ui.fragment.BaseFragment;

/**
 * Created by admin on 2/23/2016.
 */
public abstract class BaseContentFragment extends BaseFragment {
    protected abstract int getTitleStringID();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity activity = (MainActivity) getActivity();
        activity.updateToolBar(getTitleStringID());
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

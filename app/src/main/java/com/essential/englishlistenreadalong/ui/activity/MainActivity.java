package com.essential.englishlistenreadalong.ui.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.TestFragment;
import com.essential.englishlistenreadalong.app.BaseMenuActivity;
import com.essential.englishlistenreadalong.app.EssentialBroadcastReceiver;
import com.essential.englishlistenreadalong.app.EssentialUtils;
import com.essential.englishlistenreadalong.ui.fragment.FavoriteScreenFragment;
import com.essential.englishlistenreadalong.ui.fragment.HomeScreenFragment;
import com.essential.englishlistenreadalong.app.EssentialPlayer;
import com.essential.englishlistenreadalong.ui.component.FullPlayerComponent;
import com.essential.englishlistenreadalong.ui.component.NotificationPlayerComponent;
import com.essential.englishlistenreadalong.ui.component.SmallPlayerComponent;

import tatteam.com.app_common.ui.fragment.BaseFragment;

public class MainActivity extends BaseMenuActivity {
    private TelephonyManager telephonyManager;
    private PhoneStateListener phoneStateListener;
    public SmallPlayerComponent smallPlayer;
    public FullPlayerComponent fullPlayer;
    public EssentialPlayer playerController;
    public RelativeLayout marginLayout;
    public EssentialBroadcastReceiver essentialBroadcastReceiver;

    @Override
    protected void onCreateContentView() {
        super.onCreateContentView();
        essentialBroadcastReceiver = new EssentialBroadcastReceiver(MainActivity.this);
        essentialBroadcastReceiver.resigterBroadCast();
        marginLayout = (RelativeLayout) findViewById(R.id.layout_trick);
        playerController = new EssentialPlayer(this);
        smallPlayer = new SmallPlayerComponent(MainActivity.this);
        fullPlayer = new FullPlayerComponent(MainActivity.this);
        playerController.addPlayerChangeListenner(smallPlayer);
        playerController.addPlayerChangeListenner(fullPlayer);
        playerController.player = MediaPlayer.create(MainActivity.this, R.raw.traitimbenle);

        setLockMenu(true);
    }

    public void setupTelephony() {
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    playerController.pause();

                } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                    playerController.resume();
                } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    playerController.pause();
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };

        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        if (telephonyManager != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    public void sendMessageOnPlay() {
        Intent intent = new Intent(EssentialUtils.PLAY_PAUSE);
        sendBroadcast(intent);
    }

    public void sendMessageOnNext() {
        Intent intent = new Intent(EssentialUtils.NEXT);
        sendBroadcast(intent);
    }

    public void sendMessageOnPrevious() {
        Intent intent = new Intent(EssentialUtils.PREVIOUS);
        sendBroadcast(intent);
    }

    public void sendMessageOnStop() {
        Intent intent = new Intent(EssentialUtils.STOP);
        sendBroadcast(intent);
    }

    public void sendMessageShuffleRepeatChange() {
        Intent intent = new Intent(EssentialUtils.SHUFFLE_REPEAT);
        sendBroadcast(intent);
    }


    @Override
    protected BaseFragment getFragmentContent() {
        return new HomeScreenFragment();
    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fragment_container;
    }

    @Override
    protected int getToolBarID() {
        return R.id.toolbar;
    }

    @Override
    protected int getDrawerLayoutID() {
        return R.id.drawer_layout;
    }

    @Override
    protected int getNavigationViewID() {
        return R.id.nav_view;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (!item.isChecked()) {
            switch (id) {
                case R.id.app_home:
                    replaceContentFragment(getFragmentContainerId(), new HomeScreenFragment(), getString(R.string.home));
                    break;
                case R.id.downloadded:
                    replaceContentFragment(getFragmentContainerId(), new TestFragment(), getString(R.string.downloaded));
                    break;
                case R.id.my_playlist:
                    break;
                case R.id.favorite:
                    replaceContentFragment(getFragmentContainerId(), new FavoriteScreenFragment(), getString(R.string.favorite));
                    break;
                case R.id.history:
                    break;
                case R.id.more_app:
                    break;
            }

            setSelectedItemMenu(id);
        }
        closeMenu();
        return true;
    }

    @Override
    protected void onDestroy() {
        playerController.stop();
        unregisterReceiver(essentialBroadcastReceiver);
        super.onDestroy();
    }


}

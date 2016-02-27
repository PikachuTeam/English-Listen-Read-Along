package com.essential.englishlistenreadalong.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.MenuItem;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.TestFragment;
import com.essential.englishlistenreadalong.app.BaseMenuActivity;
import com.essential.englishlistenreadalong.app.EssentialPlayer;
import com.essential.englishlistenreadalong.ui.component.FullPlayerComponent;
import com.essential.englishlistenreadalong.ui.component.SmallPlayerComponent;

import tatteam.com.app_common.ui.fragment.BaseFragment;

public class MainActivity extends BaseMenuActivity {
    private TelephonyManager telephonyManager;
    private PhoneStateListener phoneStateListener;
    public SmallPlayerComponent smallPlayer;
    public FullPlayerComponent fullPlayer;
    public EssentialPlayer playerController;
    public final String NEXT_PREVIOUS = "next_previous";
    public final String PLAY_PAUSE = "play_pause";
    public final String SEEK = "seek";
    public final String REPEAT_SHUFFLE = "repeat_shuffle";


    @Override
    protected void onCreateContentView() {
        super.onCreateContentView();
        resigterBroadCast();
        playerController = new EssentialPlayer(this);
        smallPlayer = new SmallPlayerComponent(MainActivity.this);
        fullPlayer = new FullPlayerComponent(MainActivity.this);
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


    private void resigterBroadCast() {
        LocalBroadcastManager.getInstance(this).registerReceiver(playPauseReceiver,
                new IntentFilter(PLAY_PAUSE));
//        LocalBroadcastManager.getInstance(this).registerReceiver(nextPreviousReceiver,
//                new IntentFilter(NEXT_PREVIOUS));
//        LocalBroadcastManager.getInstance(this).registerReceiver(seekReceiver,
//                new IntentFilter(SEEK));
//        LocalBroadcastManager.getInstance(this).registerReceiver(repeatShuffleReceive,
//                new IntentFilter(REPEAT_SHUFFLE));
    }

    private BroadcastReceiver playPauseReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            // String message = intent.getStringExtra("message");
            String message = intent.getStringExtra("message");
            playerController.addPlayerChangeListenner(smallPlayer);
            playerController.addPlayerChangeListenner(fullPlayer);
            if (playerController.player.isPlaying())
                playerController.pause();
            else
                playerController.resume();
        }
    };


    @Override
    protected BaseFragment getFragmentContent() {
        return new TestFragment();
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
        switch (id) {
            case R.id.app_home:
                break;
            case R.id.downloadded:
                break;
            case R.id.my_playlist:
                break;
            case R.id.favorite:
                break;
            case R.id.history:
                break;
            case R.id.more_app:
                break;
        }

        setSelectedItemMenu(id);
        closeMenu();
        return true;
    }

    @Override
    protected void onDestroy() {
        playerController.stop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(playPauseReceiver);
        super.onDestroy();
    }
}

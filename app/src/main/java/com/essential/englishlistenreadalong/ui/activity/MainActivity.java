package com.essential.englishlistenreadalong.ui.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.app.BaseMenuActivity;
import com.essential.englishlistenreadalong.database.DownloadManager;
import com.essential.englishlistenreadalong.musicplayer.EssentialBroadcastReceiver;
import com.essential.englishlistenreadalong.musicplayer.EssentialUtils;
import com.essential.englishlistenreadalong.ui.component.NotificationPlayerComponent;
import com.essential.englishlistenreadalong.ui.fragment.DownloadedScreenFragment;
import com.essential.englishlistenreadalong.ui.fragment.FavoriteScreenFragment;
import com.essential.englishlistenreadalong.ui.fragment.HomeScreenFragment;
import com.essential.englishlistenreadalong.musicplayer.EssentialPlayer;
import com.essential.englishlistenreadalong.ui.component.fullplayer.FullPlayerComponent;
import com.essential.englishlistenreadalong.ui.component.SmallPlayerComponent;
import com.essential.englishlistenreadalong.ui.fragment.RecentScreenFragment;

import java.io.File;

import tatteam.com.app_common.AppCommon;
import tatteam.com.app_common.ui.fragment.BaseFragment;

public class MainActivity extends BaseMenuActivity {
    private TelephonyManager telephonyManager;
    private PhoneStateListener phoneStateListener;
    public SmallPlayerComponent smallPlayer;
    public FullPlayerComponent fullPlayer;
    public DownloadManager downloadManager;
    public EssentialPlayer playerController;
    public RelativeLayout marginLayout;
    public EssentialBroadcastReceiver essentialBroadcastReceiver;
    public NotificationPlayerComponent notificationPlayerComponent;
    public String oldTitle;

    @Override
    protected void onCreateContentView() {
        super.onCreateContentView();
        essentialBroadcastReceiver = new EssentialBroadcastReceiver(MainActivity.this);
        essentialBroadcastReceiver.resigterBroadCast();
        marginLayout = (RelativeLayout) findViewById(R.id.layout_trick);
        playerController = new EssentialPlayer(this);
        notificationPlayerComponent = new NotificationPlayerComponent(MainActivity.this);
        downloadManager = new DownloadManager(MainActivity.this);
        smallPlayer = new SmallPlayerComponent(MainActivity.this);
        fullPlayer = new FullPlayerComponent(MainActivity.this);
        downloadManager.addDownloadListener(fullPlayer);
        playerController.addPlayerChangeListenner(notificationPlayerComponent);
        playerController.addPlayerChangeListenner(smallPlayer);
        playerController.addPlayerChangeListenner(fullPlayer);
//        playerController.player = MediaPlayer.create(MainActivity.this, R.raw.traitimbenle);
        createFolderAudioIfNeed();
        setLockMenu(true);
    }

    public void createFolderAudioIfNeed() {
        File myFolder = new File("/sdcard/" + EssentialUtils.FOLDER_NAME + "/");
// have the object build the directory structure, if needed.
        myFolder.mkdirs();
    }

    public void setupTelephony() {
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == TelephonyManager.CALL_STATE_RINGING) {
                    playerController.resumePause();

                } else if (state == TelephonyManager.CALL_STATE_IDLE) {
                    playerController.resumePause();
                } else if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                    playerController.resumePause();
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
        Intent intent = new Intent(EssentialUtils.PLAY);
        sendBroadcast(intent);
    }

    public void sendMessageOnPauseResume() {
        Intent intent = new Intent(EssentialUtils.RESUME_PAUSE);
        sendBroadcast(intent);
    }

    public void sendMessageBackward() {
        Intent intent = new Intent(EssentialUtils.SEEK_BACKWARD);
        sendBroadcast(intent);
    }

    public void sendMessageForward() {
        Intent intent = new Intent(EssentialUtils.SEEK_FORWARD);
        sendBroadcast(intent);
    }

    public void sendMessageStop() {
        Intent intent = new Intent(EssentialUtils.STOP);
        sendBroadcast(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fullPlayer.isShow()) fullPlayer.hide();
        else {
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                BaseFragment currentFragment = getCurrentFragment();
                if (currentFragment != null) {
                    currentFragment.onBackPressed();
                } else {
                    super.onBackPressed();
                }
            } else {
                super.onBackPressed();
            }
        }
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
    protected int getTitleViewID() {
        return R.id.mTittle;
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
                    replaceContentFragment(getFragmentContainerId(), new DownloadedScreenFragment(), getString(R.string.downloaded));
                    break;
                case R.id.favorite:
                    replaceContentFragment(getFragmentContainerId(), new FavoriteScreenFragment(), getString(R.string.favorite));
                    break;
                case R.id.history:
                    replaceContentFragment(getFragmentContainerId(), new RecentScreenFragment(), getString(R.string.history));
                    break;
                case R.id.more_app:
                    AppCommon.getInstance().openMoreAppDialog(this);

                    break;
            }

            setSelectedItemMenu(id);
        }
        closeMenu();
        return true;
    }


    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendMessageStop();
        notificationPlayerComponent.removeNotificationMediaPlayer();
        unregisterReceiver(essentialBroadcastReceiver);
    }


}

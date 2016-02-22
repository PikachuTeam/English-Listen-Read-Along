package com.essential.englishlistenreadalong.app;

import android.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.essential.englishlistenreadalong.R;

import tatteam.com.app_common.ui.activity.BaseActivity;

/**
 * Created by admin on 2/23/2016.
 */
public abstract class BaseMenuActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;

    protected abstract int getToolBarID();

    protected abstract int getDrawerLayoutID();

    protected abstract int getNavigationViewID();

    @Override
    protected void onCreateContentView() {
        setupToolBar();
        setupMenu();
    }

    public void setupToolBar() {
        toolbar = (Toolbar) findViewById(getToolBarID());
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenu();
            }
        });

    }

    public void setupMenu() {
        drawer = (DrawerLayout) findViewById(getDrawerLayoutID());
        navigationView = (NavigationView) findViewById(getNavigationViewID());
        navigationView.setNavigationItemSelectedListener(this);
        setSelectedItemMenu(R.id.app_home);
    }

    public void closeMenu() {
        drawer.closeDrawers();
    }

    public void openMenu() {
        drawer.openDrawer(GravityCompat.START);
    }

    public void setSelectedMenu(int id) {
        navigationView.setCheckedItem(id);
    }

    public void setSelectedItemMenu(int id) {
        if (id != R.id.more_app) setSelectedMenu(id);

    }

    public void setLockMenu(boolean isLock) {
        if (isLock) drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        else drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public void updateToolBar(int titleID) {
        setTitle(titleID);
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() == 0) {

            toolbar.setNavigationIcon(R.drawable.menu);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMenu();
                }
            });
        } else {
            toolbar.setNavigationIcon(R.drawable.backspace);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popFragment();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            closeMenu();
        } else {
            super.onBackPressed();
        }
    }
}

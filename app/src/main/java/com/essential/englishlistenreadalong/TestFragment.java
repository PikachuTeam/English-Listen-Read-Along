package com.essential.englishlistenreadalong;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.essential.englishlistenreadalong.app.BaseContentFragment;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

/**
 * Created by admin on 2/23/2016.
 */
public class TestFragment extends BaseContentFragment  {
    Button a;
    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_test;
    }

    @Override
    public String getTitleString() {
        return getResources().getString(R.string.downloaded);
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        final MainActivity activity = (MainActivity) getActivity();
        a = (Button) rootView.findViewById(R.id.btn_test);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.smallPlayer.show();
            }
        });
    }




}

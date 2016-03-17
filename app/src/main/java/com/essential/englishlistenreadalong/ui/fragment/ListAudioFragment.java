package com.essential.englishlistenreadalong.ui.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.database.DataSource;
import com.essential.englishlistenreadalong.entity.Audio;
import com.essential.englishlistenreadalong.entity.SubCategory;
import com.essential.englishlistenreadalong.listener.DownloadListener;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

import java.util.ArrayList;

import tatteam.com.app_common.ui.fragment.BaseFragment;

/**
 * Created by Thanh on 24/02/2016.
 */
public class ListAudioFragment extends BaseFragment implements DownloadListener {
    private ArrayList<SubCategory> subCategoryArrayList = new ArrayList<>();
    private ArrayList<Audio> listAudioCheckedHeader = new ArrayList<>();
    ArrayList<Audio> listAudio = new ArrayList<>();
    private ListView lvAudio;
    private ListAudioAdapter adapter;
    private String title;
    private String old_title;
    private int idCategory;
    private int downloading = 0;

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_list_audio;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();

        idCategory = bundle.getInt("idCategory");
        title = DataSource.getCategory(idCategory).getNameCategories();
        subCategoryArrayList = DataSource.getListSubCategories(idCategory);
        if (subCategoryArrayList.size() == 0) {
            listAudioCheckedHeader = DataSource.getListAudioNoSub(idCategory);
            adapter = new ListAudioAdapter(getActivity(), listAudioCheckedHeader);
        } else {
            for (int i = 0; i < subCategoryArrayList.size(); i++) {
                listAudio = DataSource.getListAudio(subCategoryArrayList.get(i).getIdSubCategory());
                listAudio.get(0).header = true;
                for (int j = 0; j < listAudio.size(); j++) {
                    listAudioCheckedHeader.add(listAudio.get(j));
                }
            }
            adapter = new ListAudioAdapter(getActivity(), listAudioCheckedHeader);
        }
        updateToolBar();
        MainActivity activity = (MainActivity) getActivity();
        activity.downloadManager.addDownloadListener(this);


    }

    @Override
    public void onBackPressed() {
        final MainActivity activity = (MainActivity) getActivity();
        activity.mTitle.setText(old_title);
        activity.toolbar.setNavigationIcon(R.drawable.menu);
        activity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.openMenu();
            }
        });
        super.onBackPressed();
    }

    private void updateToolBar() {

        final MainActivity activity = (MainActivity) getActivity();
        old_title = activity.mTitle.getText().toString();
        activity.mTitle.setText(title);
        activity.toolbar.setNavigationIcon(R.drawable.backspace);
        activity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.toolbar.setTitle(old_title);
                activity.toolbar.setNavigationIcon(R.drawable.menu);
                activity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activity.openMenu();
                    }
                });
                activity.onBackPressed();

            }
        });
    }

    public void setNewAudioPlaying(int position) {
        for (int i = 0; i < listAudioCheckedHeader.size(); i++) {
            listAudioCheckedHeader.get(i).playing = false;
        }
        listAudioCheckedHeader.get(position).playing = true;
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        lvAudio = (ListView) rootView.findViewById(R.id.lvListAudio);
        lvAudio.setAdapter(adapter);

    }


    private class ListAudioAdapter extends BaseAdapter {
        ArrayList<Audio> audios;
        Context mContext;
        LayoutInflater inflater;


        public ListAudioAdapter(Context context, ArrayList<Audio> audios) {
            this.mContext = context;
            this.audios = audios;
            inflater = LayoutInflater.from(this.mContext);

        }

        @Override
        public int getCount() {
            return audios.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public void updateData(ArrayList<Audio> list) {
            this.audios = list;
            notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_audio, null);
                myViewHolder = new MyViewHolder();
                myViewHolder.tvNameAudio = (TextView) convertView.findViewById(R.id.tvNameAudio);
                myViewHolder.tvDuration = (TextView) convertView.findViewById(R.id.tvSubAudio);
                myViewHolder.imgDownload = (ImageView) convertView.findViewById(R.id.imgDownload);
                myViewHolder.imgFavorite = (ImageView) convertView.findViewById(R.id.imgPlaying);
                myViewHolder.imgIconCateory = (ImageView) convertView.findViewById(R.id.icon_categori_item);
                myViewHolder.tvSub = (TextView) convertView.findViewById(R.id.tvHeader);
                myViewHolder.tvDownload = (TextView) convertView.findViewById(R.id.tv_download);

                myViewHolder.btnDownLoad = (LinearLayout) convertView.findViewById(R.id.btn_download_in_list);
                myViewHolder.itemClick = (LinearLayout) convertView.findViewById(R.id.item_in_list);
                myViewHolder.imgIconCateory.setVisibility(View.GONE);
                Typeface UTM_Cafeta = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cafeta.ttf");
                myViewHolder.tvNameAudio.setTypeface(UTM_Cafeta);
                myViewHolder.tvSub.setTypeface(UTM_Cafeta);
                myViewHolder.tvDuration.setTypeface(UTM_Cafeta);
                myViewHolder.imgFavorite.setVisibility(View.VISIBLE);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.tvNameAudio.setText(audios.get(position).nameAudio);

            switch (audios.get(position).isDownload) {
                case 0:
                    myViewHolder.imgDownload.setVisibility(View.VISIBLE);
                    myViewHolder.imgDownload.setBackgroundResource(R.drawable.download1);
                    myViewHolder.tvDownload.setVisibility(View.GONE);
                    break;
                case 1:
                    myViewHolder.imgDownload.setVisibility(View.VISIBLE);
                    myViewHolder.imgDownload.setBackgroundResource(R.drawable.check);
                    myViewHolder.tvDownload.setVisibility(View.GONE);
                    break;
                case 2:
                    myViewHolder.imgDownload.setVisibility(View.GONE);
                    myViewHolder.tvDownload.setVisibility(View.VISIBLE);
                    myViewHolder.tvDownload.setText(audios.get(position).downloadPercent + "%");
                    break;

            }
            if (audios.get(position).header) {
                String title = DataSource.getSubCategory(audios.get(position).idSubCategory).getNameSubCategory();
                myViewHolder.tvSub.setVisibility(View.VISIBLE);
                myViewHolder.tvSub.setText(title);
            } else {
                myViewHolder.tvSub.setVisibility(View.GONE);
            }
            myViewHolder.itemClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity activity = (MainActivity) getActivity();
                    setNewAudioPlaying(position);
                    activity.playerController.setUpNewPlaylist(listAudioCheckedHeader);
                    activity.sendMessageOnPlay();
                }
            });
            if (audios.get(position).isFavorite > 0) {
                myViewHolder.imgFavorite.setBackgroundResource(R.drawable.heart);
            } else myViewHolder.imgFavorite.setBackgroundResource(R.drawable.heart_outline);
            if (audios.get(position).isDownload == 0) {
                myViewHolder.btnDownLoad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity activity = (MainActivity) getActivity();
                        activity.downloadManager.downloadAudio(audios.get(position));
                    }
                });
            } else {
                myViewHolder.btnDownLoad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MainActivity activity = (MainActivity) getActivity();
                        if (audios.get(position).downloadPercent < 100 && audios.get(position).downloadPercent > 0)
                            activity.showNotification(R.string.this_song_is_downloading);
                        else activity.showNotification(R.string.this_song_was_downloaded);

                    }
                });
            }

            return convertView;
        }

        private class MyViewHolder {
            TextView tvNameAudio;
            TextView tvDuration;
            ImageView imgDownload;
            ImageView imgFavorite;
            ImageView imgIconCateory;
            TextView tvSub;
            TextView tvDownload;
            LinearLayout itemClick;
            LinearLayout btnDownLoad;
        }
    }

    @Override
    public void onProgressDownload(Audio audio) {
        for (int i = 0; i < listAudioCheckedHeader.size(); i++) {
            if (listAudioCheckedHeader.get(i).idAudio == audio.idAudio) {
                listAudioCheckedHeader.get(i).downloadPercent = audio.downloadPercent;
                listAudioCheckedHeader.get(i).isDownload = audio.isDownload;
                break;
            }
        }
    }

    @Override
    public void onNotifyDataChange() {
        adapter.updateData(listAudioCheckedHeader);

    }
}

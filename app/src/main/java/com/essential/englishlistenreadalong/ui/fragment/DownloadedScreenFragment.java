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
import com.essential.englishlistenreadalong.app.BaseContentFragment;
import com.essential.englishlistenreadalong.database.DataSource;
import com.essential.englishlistenreadalong.entity.Audio;
import com.essential.englishlistenreadalong.listener.DownloadListener;
import com.essential.englishlistenreadalong.musicplayer.EssentialUtils;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Thanh on 07/03/2016.
 */
public class DownloadedScreenFragment extends BaseContentFragment implements DownloadListener {
    ArrayList<Audio> downloadedArraylist;
    ListDownloadedAdapter adapter;
    ListView lvDownloaded;
    TextView tvNoitem;

    @Override
    public String getTitleString() {
        return getResources().getString(R.string.downloaded);
    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_downloaded_screen;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        lvDownloaded = (ListView) rootView.findViewById(R.id.lvDownloaded);
        tvNoitem = (TextView) rootView.findViewById(R.id.tv_no_item);
        processArraylist();
        adapter = new ListDownloadedAdapter(getActivity(), downloadedArraylist);
        lvDownloaded.setAdapter(adapter);
        MainActivity activity = (MainActivity) getActivity();
        activity.downloadManager.addDownloadListener(DownloadedScreenFragment.this);
    }

    public void deleteAudio(Audio audio) {
        DataSource.updateDeleted(audio.idAudio);
        if (DataSource.isFileExists(audio.idAudio + "")) {
            File file = new File("/sdcard/" + EssentialUtils.FOLDER_NAME + audio.idAudio + ".mp3");
            file.delete();
        }
        MainActivity activity = (MainActivity) getActivity();
        activity.showNotification(R.string.this_song_was_deleted);
        processArraylist();
        adapter.updateUI();
    }

    private void processArraylist() {
        downloadedArraylist = new ArrayList<>();
        downloadedArraylist = DataSource.getListDownloaded();
        if (downloadedArraylist.size() > 0) {
            tvNoitem.setVisibility(View.GONE);
            char firstChar = downloadedArraylist.get(0).nameAudio.charAt(0);
            downloadedArraylist.get(0).headerDownloaded = true;
            for (int i = 1; i < downloadedArraylist.size(); i++) {
                if (firstChar != downloadedArraylist.get(i).nameAudio.charAt(0)) {
                    downloadedArraylist.get(i).headerDownloaded = true;
                    firstChar = downloadedArraylist.get(i).nameAudio.charAt(0);
                }
            }
        } else tvNoitem.setVisibility(View.VISIBLE);
    }

    public void setNewAudioPlaying(int position) {
        for (int i = 0; i < downloadedArraylist.size(); i++) {
            downloadedArraylist.get(i).playing = false;
        }
        downloadedArraylist.get(position).playing = true;
    }

    @Override
    public void onProgressDownload(Audio audio) {

    }

    @Override
    public void onNotifyDataChange(Boolean isHander) {
        if (isHander)
            adapter.updateUI();
    }

    private class ListDownloadedAdapter extends BaseAdapter {
        ArrayList<Audio> audios;
        Context mContext;
        LayoutInflater inflater;
        int idCategory;

        public ListDownloadedAdapter(Context context, ArrayList<Audio> audios) {
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

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_audio, null);
                myViewHolder = new MyViewHolder();
                myViewHolder.tvHeader = (TextView) convertView.findViewById(R.id.tvHeader);
                myViewHolder.imgIconCategory = (ImageView) convertView.findViewById(R.id.icon_categori_item);
                myViewHolder.imgFavorite = (ImageView) convertView.findViewById(R.id.imgPlaying);
                myViewHolder.imgDel = (ImageView) convertView.findViewById(R.id.imgDownload);
                myViewHolder.tvNameAudio = (TextView) convertView.findViewById(R.id.tvNameAudio);
                myViewHolder.tvSubCategory = (TextView) convertView.findViewById(R.id.tvSubAudio);
                myViewHolder.btnFavorite = (LinearLayout) convertView.findViewById(R.id.btn_Favorite);
                myViewHolder.itemClick = (LinearLayout) convertView.findViewById(R.id.item_in_list);
                myViewHolder.btnDelete = (LinearLayout) convertView.findViewById(R.id.btn_download_in_list);
                Typeface UTM_Cafeta = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cafeta.ttf");
                myViewHolder.tvHeader.setTypeface(UTM_Cafeta);
                myViewHolder.tvNameAudio.setTypeface(UTM_Cafeta);
                myViewHolder.tvSubCategory.setTypeface(UTM_Cafeta);

                myViewHolder.imgDel.setVisibility(View.VISIBLE);
                myViewHolder.imgDel.setBackgroundResource(R.drawable.delete);
                myViewHolder.imgFavorite.setVisibility(View.VISIBLE);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }

            myViewHolder.tvNameAudio.setText(audios.get(position).nameAudio);
            if (audios.get(position).headerDownloaded) {
                myViewHolder.tvHeader.setText(audios.get(position).nameAudio.charAt(0) + "");
                myViewHolder.tvHeader.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.tvHeader.setVisibility(View.GONE);
            }

            if (audios.get(position).idSubCategory > 18) {
                myViewHolder.imgIconCategory.setBackgroundResource(audios.get(position).getIconCategoryImage());
                myViewHolder.tvSubCategory.setText(DataSource.getSubCategory(audios.get(position).idSubCategory).getNameSubCategory());
            } else {
                idCategory = audios.get(position).idSubCategory;
                myViewHolder.imgIconCategory.setBackgroundResource(audios.get(position).getIconCategoryImage());
            }
            if (audios.get(position).isFavorite > 0) {
                myViewHolder.imgFavorite.setBackgroundResource(R.drawable.heart);
            } else myViewHolder.imgFavorite.setBackgroundResource(R.drawable.heart_outline);
            myViewHolder.btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity activity = (MainActivity) getActivity();
                    DataSource.changeFavorite(audios.get(position).idAudio);
                    if (DataSource.getFavorite(audios.get(position).idAudio) > 0)
                        activity.showNotification(R.string.added_to_favorite);
                    else activity.showNotification(R.string.remove_from_favorite);
                    updateUI();
                    activity.downloadManager.sendMessageUpdateUI();


                }
            });
            myViewHolder.itemClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity activity = (MainActivity) getActivity();
                    setNewAudioPlaying(position);
                    activity.playerController.setUpNewPlaylist(audios);
                    activity.sendMessageOnPlay();
                    DataSource.updateRecent(activity.playerController.getAudioPlaying());
                }
            });
            myViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteAudio(audios.get(position));
                }
            });
            return convertView;
        }

        public void updateUI() {
            processArraylist();
            audios = downloadedArraylist;
            notifyDataSetChanged();
        }

        private class MyViewHolder {
            TextView tvHeader;
            ImageView imgIconCategory;
            TextView tvNameAudio;
            ImageView imgFavorite;
            ImageView imgDel;
            TextView tvSubCategory;
            LinearLayout btnFavorite, btnDelete;
            LinearLayout itemClick;
        }
    }

}

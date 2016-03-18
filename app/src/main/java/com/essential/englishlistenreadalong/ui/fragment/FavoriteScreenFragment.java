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
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

import java.util.ArrayList;

/**
 * Created by Thanh on 05/03/2016.
 */
public class FavoriteScreenFragment extends BaseContentFragment implements DownloadListener {
    private ArrayList<Audio> favoriteArraylist = new ArrayList<>();
    private ListFavoriteAdapter adapter;
    ListView lvFavorite;
    TextView tvNoitem;

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_favorite_screen;
    }

    @Override
    public String getTitleString() {
        return getResources().getString(R.string.favorite);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        lvFavorite = (ListView) rootView.findViewById(R.id.lvFavorite);
        tvNoitem = (TextView) rootView.findViewById(R.id.tv_no_item);
        processArraylist();
        adapter = new ListFavoriteAdapter(getActivity(), favoriteArraylist);
        lvFavorite.setAdapter(adapter);
        MainActivity activity = (MainActivity) getActivity();
        activity.downloadManager.addDownloadListener(FavoriteScreenFragment.this);
    }

    private void processArraylist() {
        favoriteArraylist = DataSource.getListFavorite();
        if (favoriteArraylist.size() > 0) {
            tvNoitem.setVisibility(View.GONE);
            char firstChar = favoriteArraylist.get(0).nameAudio.charAt(0);
            favoriteArraylist.get(0).headerFavorite = true;
            for (int i = 1; i < favoriteArraylist.size(); i++) {
                if (firstChar != favoriteArraylist.get(i).nameAudio.charAt(0)) {
                    favoriteArraylist.get(i).headerFavorite = true;
                    firstChar = favoriteArraylist.get(i).nameAudio.charAt(0);
                }
            }
        } else tvNoitem.setVisibility(View.VISIBLE);
    }

    public void setNewAudioPlaying(int position) {
        for (int i = 0; i < favoriteArraylist.size(); i++) {
            favoriteArraylist.get(i).playing = false;
        }
        favoriteArraylist.get(position).playing = true;
    }

    @Override
    public void onProgressDownload(Audio audio) {

    }


    @Override
    public void onNotifyDataChange(Boolean isHander) {
        if (isHander)
            adapter.updateUI();
    }

    private class ListFavoriteAdapter extends BaseAdapter {
        ArrayList<Audio> audios;
        Context mContext;
        LayoutInflater inflater;
        int idCategory;

        public ListFavoriteAdapter(Context context, ArrayList<Audio> audios) {
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
                myViewHolder.imgFavorite = (ImageView) convertView.findViewById(R.id.imgDownload);
                myViewHolder.imgDownload = (ImageView) convertView.findViewById(R.id.imgPlaying);
                myViewHolder.itemClick = (LinearLayout) convertView.findViewById(R.id.item_in_list);
                myViewHolder.tvNameAudio = (TextView) convertView.findViewById(R.id.tvNameAudio);
                myViewHolder.btnFavorite = (LinearLayout) convertView.findViewById(R.id.btn_download_in_list);
                myViewHolder.tvSubCategory = (TextView) convertView.findViewById(R.id.tvSubAudio);
                myViewHolder.imgDownload.setBackgroundResource(R.drawable.heart);
                Typeface UTM_Cafeta = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cafeta.ttf");
                myViewHolder.tvHeader.setTypeface(UTM_Cafeta);
                myViewHolder.tvNameAudio.setTypeface(UTM_Cafeta);
                myViewHolder.tvSubCategory.setTypeface(UTM_Cafeta);
                myViewHolder.imgFavorite.setVisibility(View.VISIBLE);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }

            myViewHolder.tvNameAudio.setText(audios.get(position).nameAudio);
            if (audios.get(position).headerFavorite) {
                myViewHolder.tvHeader.setText(audios.get(position).nameAudio.charAt(0) + "");
                myViewHolder.tvHeader.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.tvHeader.setVisibility(View.GONE);
            }

            if (audios.get(position).idSubCategory > 18) {
                idCategory = DataSource.getSubCategory(audios.get(position).idSubCategory).getIdCategory();
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
            return convertView;

        }

        public void updateUI() {
            processArraylist();
            audios = favoriteArraylist;
            notifyDataSetChanged();
        }


        private class MyViewHolder {
            TextView tvHeader;
            LinearLayout itemClick;
            ImageView imgIconCategory;
            TextView tvNameAudio;
            ImageView imgFavorite;
            ImageView imgDownload;
            TextView tvSubCategory;
            LinearLayout btnFavorite;
        }
    }

}

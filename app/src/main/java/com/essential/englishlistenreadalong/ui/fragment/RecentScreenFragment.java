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
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

import java.util.ArrayList;

/**
 * Created by Thanh on 07/03/2016.
 */
public class RecentScreenFragment extends BaseContentFragment {
    ArrayList<Audio> recentArraylist = new ArrayList<>();
    ListRecentAdapter adapter;
    ListView lvRecent;

    @Override
    public String getTitleString() {
        return getResources().getString(R.string.history);
    }

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_recent_screen;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recentArraylist = DataSource.getListRecent();
        adapter = new ListRecentAdapter(getActivity(), recentArraylist);
        MainActivity activity = (MainActivity) getActivity();
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        lvRecent = (ListView) rootView.findViewById(R.id.lvRecent);
        lvRecent.setAdapter(adapter);
    }

    private class ListRecentAdapter extends BaseAdapter {
        ArrayList<Audio> audios;
        Context mContext;
        LayoutInflater inflater;
        int idCategory;

        public ListRecentAdapter(Context context, ArrayList<Audio> audios) {
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
                myViewHolder.tvNameRecentAudio = (TextView) convertView.findViewById(R.id.tvNameAudio);
                myViewHolder.itemClick = (LinearLayout) convertView.findViewById(R.id.item_in_list);
                myViewHolder.btnFavorite = (LinearLayout) convertView.findViewById(R.id.btn_download_in_list);
                myViewHolder.tvSubRecent = (TextView) convertView.findViewById(R.id.tvSubAudio);
                myViewHolder.imgFavorite = (ImageView) convertView.findViewById(R.id.imgDownload);
                myViewHolder.imgIconCategoryRc = (ImageView) convertView.findViewById(R.id.icon_categori_item);
                Typeface UTM_Cafeta = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cafeta.ttf");
                myViewHolder.tvNameRecentAudio.setTypeface(UTM_Cafeta);
                myViewHolder.tvSubRecent.setTypeface(UTM_Cafeta);
                myViewHolder.imgFavorite.setVisibility(View.VISIBLE);

                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }

            myViewHolder.tvNameRecentAudio.setText(audios.get(position).nameAudio);
            if (audios.get(position).idSubCategory > 18) {
                myViewHolder.imgIconCategoryRc.setBackgroundResource(audios.get(position).getIconCategoryImage());
                myViewHolder.tvSubRecent.setText(DataSource.getSubCategory(audios.get(position).idSubCategory).getNameSubCategory());
            } else {
                idCategory = audios.get(position).idSubCategory;
                myViewHolder.imgIconCategoryRc.setBackgroundResource(audios.get(position).getIconCategoryImage());
            }
            if (audios.get(position).isFavorite > 0) {
                myViewHolder.imgFavorite.setBackgroundResource(R.drawable.heart);
            } else myViewHolder.imgFavorite.setBackgroundResource(R.drawable.heart_outline);
            myViewHolder.itemClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity activity = (MainActivity) getActivity();
                    setNewAudioPlaying(position);
                    activity.playerController.setUpNewPlaylist(audios);
                    activity.sendMessageOnPlay();
                }
            });
            myViewHolder.btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DataSource.changeFavorite(audios.get(position).idAudio);
                    recentArraylist = DataSource.getListRecent();
                    audios = recentArraylist;
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        public void setNewAudioPlaying(int position) {
            for (int i = 0; i < audios.size(); i++) {
                audios.get(i).playing = false;
            }
            audios.get(position).playing = true;
        }


        private class MyViewHolder {
            TextView tvNameRecentAudio;
            TextView tvSubRecent;
            LinearLayout itemClick;
            LinearLayout btnFavorite;
            ImageView imgIconCategoryRc;
            ImageView imgFavorite;
        }
    }


}


package com.essential.englishlistenreadalong.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.app.BaseContentFragment;
import com.essential.englishlistenreadalong.database.DataSource;
import com.essential.englishlistenreadalong.entity.Audio;

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
        adapter = new ListRecentAdapter(getActivity(),recentArraylist);
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
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_recent_row_item, null);
                myViewHolder = new MyViewHolder();

                myViewHolder.tvNameRecentAudio = (TextView) convertView.findViewById(R.id.tvNameAudioRecent);
                myViewHolder.tvSubRecent = (TextView) convertView.findViewById(R.id.tvRecentSub);
                myViewHolder.imgDownload = (ImageView) convertView.findViewById(R.id.imgDownloadRecent);
                myViewHolder.imgPlaying = (ImageView) convertView.findViewById(R.id.imgPlayingRecent);
                myViewHolder.imgIconCategoryRc = (ImageView) convertView.findViewById(R.id.imgIconCategoryRc);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }

            myViewHolder.tvNameRecentAudio.setText(audios.get(position).nameAudio);
            if (audios.get(position).idSubCategory > 18) {
                myViewHolder.imgIconCategoryRc.setBackgroundResource(audios.get(position).getIconCategoryImage());
                myViewHolder.tvSubRecent.setText(DataSource.getSubCategory(audios.get(position).idSubCategory).getNameSubCategory());
            }else {
                idCategory = audios.get(position).idSubCategory;
                myViewHolder.imgIconCategoryRc.setBackgroundResource(audios.get(position).getIconCategoryImage());
            }

            return convertView;
        }

        private class MyViewHolder {
            TextView tvNameRecentAudio;
            TextView tvSubRecent;
            ImageView imgIconCategoryRc;
            ImageView imgDownload;
            ImageView imgPlaying;
        }
    }
}

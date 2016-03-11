package com.essential.englishlistenreadalong.ui.fragment;

import android.content.Context;
import android.graphics.Typeface;
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
public class DownloadedScreenFragment extends BaseContentFragment {
    ArrayList<Audio> downloadedArraylist = new ArrayList<>();
    ListDownloadedAdapter adapter;
    ListView lvDownloaded;

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
        downloadedArraylist = DataSource.getListDownloaded();
        processArraylist();
        adapter = new ListDownloadedAdapter(getActivity(), downloadedArraylist);
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        lvDownloaded = (ListView) rootView.findViewById(R.id.lvDownloaded);
        lvDownloaded.setAdapter(adapter);
    }

    private void processArraylist() {
        char firstChar = downloadedArraylist.get(0).nameAudio.charAt(0);
        downloadedArraylist.get(0).headerDownloaded = true;
        for (int i = 1; i < downloadedArraylist.size(); i++) {
            if (firstChar != downloadedArraylist.get(i).nameAudio.charAt(0)) {
                downloadedArraylist.get(i).headerDownloaded = true;
                firstChar = downloadedArraylist.get(i).nameAudio.charAt(0);
            }
        }
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
        public View getView(int position, View convertView, ViewGroup parent) {
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
                Typeface UTM_Cafeta = Typeface.createFromAsset(getActivity().getAssets(), "fonts/cafeta.ttf");
                myViewHolder.tvHeader.setTypeface(UTM_Cafeta);
                myViewHolder.tvNameAudio.setTypeface(UTM_Cafeta);
                myViewHolder.tvSubCategory.setTypeface(UTM_Cafeta);
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
            return convertView;
        }

        private class MyViewHolder {
            TextView tvHeader;
            ImageView imgIconCategory;
            TextView tvNameAudio;
            ImageView imgFavorite;
            ImageView imgDel;
            TextView tvSubCategory;
        }
    }
}

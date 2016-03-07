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
        adapter = new ListDownloadedAdapter(getActivity(),downloadedArraylist);
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        lvDownloaded = (ListView) rootView.findViewById(R.id.lvDownloaded);
        lvDownloaded.setAdapter(adapter);
    }

    private void processArraylist(){
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
                convertView = inflater.inflate(R.layout.list_downloaded_row_item, null);
                myViewHolder = new MyViewHolder();
                myViewHolder.tvHeader = (TextView) convertView.findViewById(R.id.tvHeaderDownload);
                myViewHolder.imgIconCategory = (ImageView) convertView.findViewById(R.id.imgIconCategoryDL);
                myViewHolder.imgPlaying = (ImageView) convertView.findViewById(R.id.imgPlayingDownload);
                myViewHolder.imgDel = (ImageView) convertView.findViewById(R.id.imgDeleteDownload);
                myViewHolder.tvNameAudio = (TextView) convertView.findViewById(R.id.tvNameAudioDownload);
                myViewHolder.tvSubCategory = (TextView) convertView.findViewById(R.id.tvDownloadSub);
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
                idCategory = DataSource.getSubCategory(audios.get(position).idSubCategory).getIdCategory();
                myViewHolder.imgIconCategory.setBackgroundResource(audios.get(position).getIconCategoryImage(idCategory));
                myViewHolder.tvSubCategory.setText(DataSource.getSubCategory(audios.get(position).idSubCategory).getNameSubCategory());
            }else {
                idCategory = audios.get(position).idSubCategory;
                myViewHolder.imgIconCategory.setBackgroundResource(audios.get(position).getIconCategoryImage(idCategory));
            }
            return convertView;
        }

        private class MyViewHolder {
            TextView tvHeader;
            ImageView imgIconCategory;
            TextView tvNameAudio;
            ImageView imgPlaying;
            ImageView imgDel;
            TextView tvSubCategory;
        }
    }
}

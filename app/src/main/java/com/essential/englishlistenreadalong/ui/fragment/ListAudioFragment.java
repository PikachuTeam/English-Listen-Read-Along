package com.essential.englishlistenreadalong.ui.fragment;

import android.content.Context;
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
import com.essential.englishlistenreadalong.entity.SubCategory;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

import java.util.ArrayList;

/**
 * Created by Thanh on 24/02/2016.
 */
public class ListAudioFragment extends BaseContentFragment {
    private ArrayList<SubCategory> subCategoryArrayList = new ArrayList<>();
    private ArrayList<Audio> listAudioCheckedHeader = new ArrayList<>();
    ArrayList<Audio> listAudio = new ArrayList<>();
    private ListView lvAudio;
    private ListAudioAdapter adapter;

    private int idCategory;

    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_list_audio;
    }

    @Override
    public String getTitleString() {
        return "List Audio";
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        idCategory = bundle.getInt("idCategory");
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

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_audio_row_item, null);
                myViewHolder = new MyViewHolder();
                myViewHolder.tvNameAudio = (TextView) convertView.findViewById(R.id.tvNameAudio);
                myViewHolder.tvDuration = (TextView) convertView.findViewById(R.id.tvMinDuration);
                myViewHolder.imgDownload = (ImageView) convertView.findViewById(R.id.imgDownload);
                myViewHolder.imgPlaying = (ImageView) convertView.findViewById(R.id.imgPlaying);
                myViewHolder.tvSub = (TextView) convertView.findViewById(R.id.tvSub);
                myViewHolder.btnDownLoad = (LinearLayout) convertView.findViewById(R.id.btn_download_in_list);
                myViewHolder.itemClick = (LinearLayout) convertView.findViewById(R.id.item_in_list);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.tvNameAudio.setText(audios.get(position).nameAudio);

            if (audios.get(position).isDownload == 1) {
                myViewHolder.imgDownload.setVisibility(View.INVISIBLE);
            }
            if (audios.get(position).header == true) {
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
            return convertView;
        }


        private class MyViewHolder {
            TextView tvNameAudio;
            TextView tvDuration;
            ImageView imgDownload;
            ImageView imgPlaying;
            TextView tvSub;
            LinearLayout itemClick;
            LinearLayout btnDownLoad;
        }
    }
}

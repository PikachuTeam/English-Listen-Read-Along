package com.essential.englishlistenreadalong.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.app.BaseContentFragment;
import com.essential.englishlistenreadalong.database.DataSource;
import com.essential.englishlistenreadalong.entity.Audio;
import com.essential.englishlistenreadalong.entity.SubCategory;

import java.util.ArrayList;

import tatteam.com.app_common.ui.fragment.BaseFragment;

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
            listAudio = DataSource.getListAudioNoSub(idCategory);
            adapter = new ListAudioAdapter(getActivity(), listAudio);
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
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_audio_row_item, null);
                myViewHolder = new MyViewHolder();
                myViewHolder.tvNameAudio = (TextView) convertView.findViewById(R.id.tvNameAudio);
                myViewHolder.tvMinDuration = (TextView) convertView.findViewById(R.id.tvMinDuration);
                myViewHolder.tvSecDuration = (TextView) convertView.findViewById(R.id.tvSecDuration);
                myViewHolder.imgDownload = (ImageView) convertView.findViewById(R.id.imgDownload);
                myViewHolder.imgPlaying = (ImageView) convertView.findViewById(R.id.imgPlaying);
                myViewHolder.tvSub = (TextView) convertView.findViewById(R.id.tvSub);
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.tvNameAudio.setText(audios.get(position).nameAudio);
            myViewHolder.tvMinDuration.setText("12");
            myViewHolder.tvSecDuration.setText("25");

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
            return convertView;
        }

        private class MyViewHolder {
            TextView tvNameAudio;
            TextView tvMinDuration;
            TextView tvSecDuration;
            ImageView imgDownload;
            ImageView imgPlaying;
            TextView tvSub;
        }
    }
}

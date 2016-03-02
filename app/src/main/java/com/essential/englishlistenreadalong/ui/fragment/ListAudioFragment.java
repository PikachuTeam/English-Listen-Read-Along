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

import java.util.ArrayList;

import tatteam.com.app_common.ui.fragment.BaseFragment;

/**
 * Created by Thanh on 24/02/2016.
 */
public class ListAudioFragment extends BaseContentFragment {
    private ArrayList<Audio> audioArrayList = new ArrayList<>();
    private ListView lvAudio;
    private ListAudioAdapter adapter;

    private int idSubCategory;
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
        audioArrayList = DataSource.getListAudio(idSubCategory);
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        idSubCategory = bundle.getInt("idSub");

        lvAudio = (ListView) rootView.findViewById(R.id.lvAudio);
        adapter = new ListAudioAdapter(getActivity(),audioArrayList);
        lvAudio.setAdapter(adapter);
    }

    private class ListAudioAdapter extends BaseAdapter{
        ArrayList<Audio> audios;
        Context mContext;
        LayoutInflater inflater;

        public ListAudioAdapter (Context context,ArrayList<Audio> audios){
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

            if(convertView ==null){
                convertView = inflater.inflate(R.layout.list_audio_row_item,null);
                myViewHolder = new MyViewHolder();
                convertView.setTag(myViewHolder);

                myViewHolder.tvNameAudio = (TextView) convertView.findViewById(R.id.tvNameAudio);
                myViewHolder.tvMinDuration = (TextView) convertView.findViewById(R.id.tvMinDuration);
                myViewHolder.tvSecDuration = (TextView) convertView.findViewById(R.id.tvSecDuration);
                myViewHolder.imgDownload = (ImageButton) convertView.findViewById(R.id.imgDownload);
                myViewHolder.imgPlaying = (ImageView) convertView.findViewById(R.id.imgPlaying);
            }
            else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }

            myViewHolder.tvNameAudio.setText(audios.get(position).getNameAuido());
            myViewHolder.tvMinDuration.setText("12");
            myViewHolder.tvSecDuration.setText("25");
            if (audios.get(position).isDownload()==1){
                myViewHolder.imgDownload.setVisibility(View.INVISIBLE);
            }
            myViewHolder.imgDownload.setImageResource(R.drawable.download);
            myViewHolder.imgPlaying.setImageResource(R.drawable.play);

            return convertView;
        }
        private class MyViewHolder{
            TextView tvNameAudio;
            TextView tvMinDuration;
            TextView tvSecDuration;
            ImageButton imgDownload;
            ImageView imgPlaying;
        }
    }
}

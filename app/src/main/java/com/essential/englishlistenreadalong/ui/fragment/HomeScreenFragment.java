package com.essential.englishlistenreadalong.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.app.BaseContentFragment;
import com.essential.englishlistenreadalong.database.DataSource;
import com.essential.englishlistenreadalong.entity.Categories;
import com.essential.englishlistenreadalong.entity.SubCategory;

import java.util.ArrayList;

import tatteam.com.app_common.ui.fragment.BaseFragment;

/**
 * Created by Thanh on 21/02/2016.
 */
public class HomeScreenFragment extends BaseContentFragment implements AdapterView.OnItemClickListener {

    private ListCategoriesAdapter adapter;
    private ListView lvCategories;
    private ArrayList<Categories> categoriesArrayList = new ArrayList<>();
    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_home_screen;
    }

    @Override
    public String getTitleString() {
        return getResources().getString(R.string.home);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoadData();
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {
        lvCategories = (ListView) rootView.findViewById(R.id.lvCategories);

        adapter = new ListCategoriesAdapter(getActivity(),categoriesArrayList);
        lvCategories.setAdapter(adapter);
        lvCategories.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListAudioFragment fragment=new ListAudioFragment();
        Bundle bundle = new Bundle();
        int idCategory = categoriesArrayList.get(position).getIdCategories();
        bundle.putInt("idCategory", idCategory);
        fragment.setArguments(bundle);
        replaceFragment(fragment, "ListAudio");
    }

    private void LoadData() {
        categoriesArrayList = DataSource.getListCategories();
    }

    private class ListCategoriesAdapter extends BaseAdapter {
        Context mContext;
        ArrayList<Categories> categories;
        LayoutInflater inflater;

        public ListCategoriesAdapter(Context context, ArrayList<Categories> categories) {
            this.mContext = context;
            this.categories = categories;
            inflater = LayoutInflater.from(this.mContext);
        }

        @Override
        public int getCount() {
            return categories.size();
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
            int idCategory = categories.get(position).getIdCategories();
            if(convertView == null){
                convertView = inflater.inflate(R.layout.list_categories_row_item,null);
                myViewHolder = new MyViewHolder();
                convertView.setTag(myViewHolder);
            }else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }

            myViewHolder.tvNameCategory = (TextView) convertView.findViewById(R.id.tvNameCategory);
            myViewHolder.imgCategories = (ImageView) convertView.findViewById(R.id.imgCategory);

            myViewHolder.tvNameCategory.setText(categories.get(position).getNameCategories());

            if (idCategory ==1){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.america_stories);
            }else if (idCategory == 2){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.american_history);
            }else if (idCategory == 3){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.animal);
            }else if (idCategory == 4){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.health);
            }else if (idCategory == 5){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.how);
            }else if (idCategory == 6){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.medical);
            }else if (idCategory == 7){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.place);
            }else if (idCategory == 8){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.sports);
            }else if (idCategory == 9){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.people);
            }else if (idCategory == 10){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.things);
            }else if (idCategory == 11){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.study_in_usa);
            }else if (idCategory == 12){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.space_exploration);
            }else if (idCategory == 13){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.word_and_story);
            }else if (idCategory == 14){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.america_mosaic);
            }else if (idCategory == 15){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.this_is_american);
            }else if (idCategory == 16){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.english);
            }else if (idCategory == 17){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.event);
            }else if (idCategory == 18){
                myViewHolder.imgCategories.setBackgroundResource(R.drawable.music);
            }
            return convertView;
        }

        private class MyViewHolder{
            ImageView imgCategories;
            TextView tvNameCategory;
        }
    }
}

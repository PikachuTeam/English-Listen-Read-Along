package com.essential.englishlistenreadalong.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.app.BaseContentFragment;
import com.essential.englishlistenreadalong.database.DataSource;
import com.essential.englishlistenreadalong.entity.SubCategory;

import java.util.ArrayList;

import tatteam.com.app_common.ui.fragment.BaseFragment;

/**
 * Created by Thanh on 22/02/2016.
 */
public class SubCategoryFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private int idCategory;
    private ArrayList<SubCategory> subCategoryArrayList= new ArrayList<>();
    private ListSubCategoriesAdapter adapter;

    private ListView lvSubCategories;
    @Override
    protected int getLayoutResIdContentView() {
        return R.layout.fragment_sub_screen;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        idCategory = bundle.getInt("idCategory");
        subCategoryArrayList = DataSource.getListSubCategories(idCategory);
    }

    @Override
    protected void onCreateContentView(View rootView, Bundle savedInstanceState) {


        lvSubCategories = (ListView) rootView.findViewById(R.id.lvSubCategories);
        adapter = new ListSubCategoriesAdapter(getActivity(),subCategoryArrayList);
        lvSubCategories.setAdapter(adapter);
        lvSubCategories.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListAudioFragment fragment = new ListAudioFragment();
        Bundle bundle = new Bundle();
        int idSubCategory = subCategoryArrayList.get(position).getIdSubCategory();
        bundle.getInt("idSub",idSubCategory);
        fragment.setArguments(bundle);
        replaceFragment(fragment,"ListAudio");
    }


    private class ListSubCategoriesAdapter extends BaseAdapter{
        Context mContext;
        ArrayList<SubCategory> subCategories;
        LayoutInflater inflater;

        public ListSubCategoriesAdapter(Context context,ArrayList<SubCategory> subCategories){
            this.mContext = context;
            this.subCategories = subCategories;
            inflater = LayoutInflater.from(this.mContext);
        }
        @Override
        public int getCount() {
            return subCategories.size();
        }

        @Override
        public Object getItem(int position) {
            return subCategories.get(position);
        }

        @Override
        public long getItemId(int position) {
            return subCategories.get(position).getIdSubCategory();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder myViewHolder;

            if(convertView == null){
                convertView = inflater.inflate(R.layout.list_subcategories_row_item,null);
                myViewHolder = new MyViewHolder();
                myViewHolder.tvNameSub = (TextView) convertView.findViewById(R.id.tvNameSubCategory);
                myViewHolder.tvTotal = (TextView) convertView.findViewById(R.id.tvTotalAudio);
                convertView.setTag(myViewHolder);
            }else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }

            myViewHolder.tvNameSub.setText(subCategories.get(position).getNameSubCategory());
            myViewHolder.tvTotal.setText(subCategories.get(position).getTotalOfAudio()+"");
            return convertView;
        }
        private class MyViewHolder{
            TextView tvNameSub;
            TextView tvTotal;
        }
    }
}

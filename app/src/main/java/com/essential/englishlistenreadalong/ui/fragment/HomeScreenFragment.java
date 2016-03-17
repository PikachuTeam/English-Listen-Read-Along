package com.essential.englishlistenreadalong.ui.fragment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.app.BaseContentFragment;
import com.essential.englishlistenreadalong.database.DataSource;
import com.essential.englishlistenreadalong.entity.Categories;
import com.essential.englishlistenreadalong.ui.activity.MainActivity;

import java.util.ArrayList;

/**
 * Created by Thanh on 21/02/2016.
 */
public class HomeScreenFragment extends BaseContentFragment implements AdapterView.OnItemClickListener {

    private ListCategoriesAdapter adapter;
    private GridView lvCategories;
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
        lvCategories = (GridView) rootView.findViewById(R.id.lvCategories);

        adapter = new ListCategoriesAdapter(getActivity(), categoriesArrayList);
        lvCategories.setAdapter(adapter);
        lvCategories.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListAudioFragment fragment = new ListAudioFragment();
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
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_categories_row_item, null);
                myViewHolder = new MyViewHolder();
                convertView.setTag(myViewHolder);
            } else {
                myViewHolder = (MyViewHolder) convertView.getTag();
            }
            myViewHolder.tvNameCategory = (TextView) convertView.findViewById(R.id.tvNameCategory);
            myViewHolder.imgCategories = (ImageView) convertView.findViewById(R.id.imgCategory);
            Typeface UTM_Bebas = Typeface.createFromAsset(getActivity().getAssets(), "fonts/bebas.ttf");
            myViewHolder.tvNameCategory.setTypeface(UTM_Bebas);

            myViewHolder.tvNameCategory.setText(categories.get(position).getNameCategories());
            myViewHolder.imgCategories.setBackgroundResource(categories.get(position).getImageID());
            return convertView;
        }

        private class MyViewHolder {
            ImageView imgCategories;
            TextView tvNameCategory;
        }
    }
}

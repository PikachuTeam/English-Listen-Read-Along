package com.essential.englishlistenreadalong.entity;

import com.essential.englishlistenreadalong.R;
import com.essential.englishlistenreadalong.database.DataSource;

/**
 * Created by Thanh on 24/02/2016.
 */
public class Audio {
    public int idAudio;
    public int idSubCategory;
    public String nameAudio;
    public int isDownload = 0;
    public int isFavorite = 0;
    public int lastOpen = 0;
    public String url;
    public boolean header = false;
    public boolean headerFavorite = false;
    public boolean playing = false;

    public int getIdSubCategory() {
        Categories categories = DataSource.getCategory(idSubCategory);
        if (categories.idCategoriesParent == 0) return categories.getIdCategories();
        else return categories.idCategoriesParent;
    }

    public String getCategoryName() {
        Categories categories = DataSource.getCategory(idSubCategory);
        if (categories.idCategoriesParent == 0) return categories.getNameCategories();
        else return DataSource.getCategory(categories.idCategoriesParent).getNameCategories();
    }

    public int getIconCategoryImage() {
        switch (getIdSubCategory()) {
            case 1:
                return R.drawable.icon_america_stories;
            case 2:
                return R.drawable.icon_america_history;
            case 3:
                return R.drawable.icon_animal;
            case 4:
                return R.drawable.icon_health;
            case 5:
                return R.drawable.icon_howto;
            case 6:
                return R.drawable.icon_medical;
            case 7:
                return R.drawable.icon_place;
            case 8:
                return R.drawable.icon_sports;
            case 9:
                return R.drawable.icon_people;
            case 10:
                return R.drawable.icon_things;
            case 11:
                return R.drawable.icon_study_in_usa;
            case 12:
                return R.drawable.icon_space_exploration;
            case 13:
                return R.drawable.icon_words_and_story;
            case 14:
                return R.drawable.icon_america_mosaic;
            case 15:
                return R.drawable.icon_this_is_american;
            case 16:
                return R.drawable.icon_english;
            case 17:
                return R.drawable.icon_even;
            case 18:
                return R.drawable.icon_music;
        }
        return 0;
    }

}

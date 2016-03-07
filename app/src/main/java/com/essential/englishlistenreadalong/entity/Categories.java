package com.essential.englishlistenreadalong.entity;

import com.essential.englishlistenreadalong.R;

/**
 * Created by Thanh on 21/02/2016.
 */
public class Categories {
    private int idCategories;
    private String nameCategories;
    public int idCategoriesParent;

    public int getImageID() {
        switch (idCategories) {
            case 1:
                return R.drawable.america_stories;
            case 2:
                return R.drawable.american_history;
            case 3:
                return R.drawable.animal;
            case 4:
                return R.drawable.health;
            case 5:
                return R.drawable.how;
            case 6:
                return R.drawable.medical;
            case 7:
                return R.drawable.place;
            case 8:
                return R.drawable.sports;
            case 9:
                return R.drawable.people;
            case 10:
                return R.drawable.things;
            case 11:
                return R.drawable.study_in_usa;
            case 12:
                return R.drawable.space_exploration;
            case 13:
                return R.drawable.word_and_story;
            case 14:
                return R.drawable.america_mosaic;
            case 15:
                return R.drawable.this_is_american;
            case 16:
                return R.drawable.english;
            case 17:
                return R.drawable.event;
            case 18:
                return R.drawable.music;
        }
        return 0;
    }

    public int getIconID() {
        switch (idCategories) {
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

    public Categories() {

    }

    public int getIdCategories() {
        return idCategories;
    }

    public void setIdCategories(int idCategories) {
        this.idCategories = idCategories;
    }

    public String getNameCategories() {
        return nameCategories;
    }

    public void setNameCategories(String nameCategories) {
        this.nameCategories = nameCategories;
    }

}

package com.essential.englishlistenreadalong.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.essential.englishlistenreadalong.entity.Audio;
import com.essential.englishlistenreadalong.entity.Categories;
import com.essential.englishlistenreadalong.entity.SubCategory;

import java.lang.reflect.Array;
import java.util.ArrayList;

import tatteam.com.app_common.sqlite.BaseDataSource;

/**
 * Created by Thanh on 25/02/2016.
 */
public class DataSource extends BaseDataSource {
    private static SQLiteDatabase sqLiteDatabase = openConnection();

    public static ArrayList<Categories> getListCategories() {
        ArrayList<Categories> categoriesArrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Categories where ParentID = 0", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Categories categories = new Categories();
            categories.setIdCategories(cursor.getInt(0));
            categories.setNameCategories(cursor.getString(2));
            categories.idCategoriesParent = cursor.getInt(1);
            categoriesArrayList.add(categories);
            cursor.moveToNext();
        }
        cursor.close();
        return categoriesArrayList;
    }

    public static ArrayList<SubCategory> getListSubCategories(int idCategory) {
        ArrayList<SubCategory> subCategoryArrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Categories where ParentID =" + idCategory, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SubCategory subCategory = new SubCategory();
            subCategory.setIdSubCategory(cursor.getInt(0));
            subCategory.setNameSubCategory(cursor.getString(2));
            subCategory.setTotalOfAudio(cursor.getInt(3));
            subCategoryArrayList.add(subCategory);
            cursor.moveToNext();
        }
        cursor.close();
        return subCategoryArrayList;
    }

    public static ArrayList<Audio> getListAudio(int idSubCategory) {
        ArrayList<Audio> audioArrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Articles where CategoryID =" + idSubCategory, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Audio audio = new Audio();
            audio.idAudio = (cursor.getInt(0));
            audio.idSubCategory = idSubCategory;
            audio.nameAudio = (cursor.getString(2));
            audio.url = (cursor.getString(4));
            audio.isFavorite = cursor.getInt(5);
            audio.isDownload = (cursor.getInt(6));
            audioArrayList.add(audio);
            cursor.moveToNext();
        }
        cursor.close();
        return audioArrayList;
    }

    public static SubCategory getSubCategory(int idSubCategory) {
        SubCategory subCategory = new SubCategory();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from Categories where ID = " + idSubCategory, null);
        cursor.moveToFirst();
        subCategory.setIdSubCategory(idSubCategory);
        subCategory.setIdCategory(cursor.getInt(1));
        subCategory.setNameSubCategory(cursor.getString(2));
        subCategory.setTotalOfAudio(cursor.getInt(3));

        cursor.moveToNext();
        cursor.close();
        return subCategory;
    }

    public static ArrayList<Audio> getListAudioNoSub(int idCategory) {
        ArrayList<Audio> audioArrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Articles where CategoryID =" + idCategory, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Audio audio = new Audio();
            audio.idAudio = (cursor.getInt(0));
            audio.idSubCategory = cursor.getInt(1);
            audio.nameAudio = (cursor.getString(2));
            audio.url = (cursor.getString(4));
            audio.isFavorite = cursor.getInt(5);
            audio.isDownload = (cursor.getInt(6));
            audioArrayList.add(audio);
            cursor.moveToNext();
        }
        cursor.close();
        return audioArrayList;
    }

    public static ArrayList<Audio> getListFavorite() {
        ArrayList<Audio> favoriteArraylist = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Articles where IsFavorite = 1 order by Title asc", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Audio audio = new Audio();
            audio.idAudio = cursor.getInt(0);
            audio.idSubCategory = cursor.getInt(1);
            audio.nameAudio = cursor.getString(2);
            audio.url = cursor.getString(4);
            audio.isFavorite = cursor.getInt(5);
            audio.isDownload = cursor.getInt(6);
            favoriteArraylist.add(audio);
            cursor.moveToNext();
        }
        cursor.close();
        return favoriteArraylist;
    }

    public static Categories getCategory(int idCategory) {
        Categories categories = new Categories();
        Cursor cursor = sqLiteDatabase.rawQuery(" select * from Categories where ID = " + idCategory, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            categories.setIdCategories(idCategory);
            categories.idCategoriesParent = cursor.getInt(1);
            categories.setNameCategories(cursor.getString(2));
            cursor.moveToNext();
        }
        cursor.close();

        return categories;
    }

    public static ArrayList<Audio> getListRecent(){
        ArrayList<Audio> recentArraylist = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Articles where LastOpen not null order by LastOpen desc",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Audio audio = new Audio();
            audio.idAudio = cursor.getInt(0);
            audio.idSubCategory = cursor.getInt(1);
            audio.nameAudio = cursor.getString(2);
            audio.url = cursor.getString(4);
            audio.isFavorite = cursor.getInt(5);
            audio.isDownload = cursor.getInt(6);
            recentArraylist.add(audio);
            cursor.moveToNext();
        }
        cursor.close();
        return recentArraylist;
    }

    public static ArrayList<Audio> getListDownloaded(){
        ArrayList<Audio> downloadedArraylist = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Articles where IsDownloaded not null order by Title asc",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Audio audio = new Audio();
            audio.idAudio = cursor.getInt(0);
            audio.idSubCategory = cursor.getInt(1);
            audio.nameAudio = cursor.getString(2);
            audio.url = cursor.getString(4);
            audio.isFavorite = cursor.getInt(5);
            audio.isDownload = cursor.getInt(6);
            downloadedArraylist.add(audio);
            cursor.moveToNext();
        }
        cursor.close();
        return downloadedArraylist;
    }

}

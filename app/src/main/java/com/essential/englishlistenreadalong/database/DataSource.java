package com.essential.englishlistenreadalong.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.essential.englishlistenreadalong.entity.Audio;
import com.essential.englishlistenreadalong.entity.Categories;
import com.essential.englishlistenreadalong.entity.SubCategory;
import com.essential.englishlistenreadalong.musicplayer.EssentialUtils;

import java.io.File;
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
            audio.isDownload = cursor.getInt(6);
            audio.lastOpen = cursor.getInt(7);
            checkFileExists(audio);
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
            audio.isDownload = cursor.getInt(6);
            audio.lastOpen = cursor.getInt(7);
            checkFileExists(audio);
            audioArrayList.add(audio);
            cursor.moveToNext();
        }
        cursor.close();
        return audioArrayList;
    }

    public static ArrayList<Audio> getListFavorite() {
        ArrayList<Audio> favoriteArraylist = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Articles where IsFavorite >0 order by Title asc", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Audio audio = new Audio();
            audio.idAudio = cursor.getInt(0);
            audio.idSubCategory = cursor.getInt(1);
            audio.nameAudio = cursor.getString(2);
            audio.url = cursor.getString(4);
            audio.isFavorite = cursor.getInt(5);
            audio.isDownload = cursor.getInt(6);
            audio.lastOpen = cursor.getInt(7);
            checkFileExists(audio);
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

    public static ArrayList<Audio> getListRecent() {
        ArrayList<Audio> recentArraylist = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Articles where LastOpen >0 order by LastOpen desc limit 30", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Audio audio = new Audio();
            audio.idAudio = cursor.getInt(0);
            audio.idSubCategory = cursor.getInt(1);
            audio.nameAudio = cursor.getString(2);
            audio.url = cursor.getString(4);
            audio.isFavorite = cursor.getInt(5);
            audio.isDownload = cursor.getInt(6);
            audio.lastOpen = cursor.getInt(7);
            checkFileExists(audio);
            recentArraylist.add(audio);
            cursor.moveToNext();
        }
        cursor.close();
        return recentArraylist;
    }

    public static ArrayList<Audio> getListDownloaded() {
        ArrayList<Audio> downloadedArraylist = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Articles where IsDownloaded >0 order by Title asc", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Audio audio = new Audio();
            audio.idAudio = cursor.getInt(0);
            audio.idSubCategory = cursor.getInt(1);
            audio.nameAudio = cursor.getString(2);
            audio.url = cursor.getString(4);
            audio.isFavorite = cursor.getInt(5);
            audio.isDownload = cursor.getInt(6);
            audio.lastOpen = cursor.getInt(7);
            checkFileExists(audio);
            if (audio.isDownload == 1)
                downloadedArraylist.add(audio);
            cursor.moveToNext();
        }
        cursor.close();
        return downloadedArraylist;
    }

    public static void updateDownloaded(int idAudio) {
        String id = "" + idAudio;
        Cursor cursor = sqLiteDatabase.rawQuery("UPDATE Articles SET IsDownloaded = 1 WHERE id = ?", new String[]{id});
        cursor.moveToFirst();
        cursor.close();
    }

    public static void changeFavorite(int idAudio) {
        String id = "" + idAudio;
        Cursor cursor;
        if (getFavorite(idAudio) > 0) {
            cursor = sqLiteDatabase.rawQuery("UPDATE Articles SET IsFavorite = 0 WHERE id = ?", new String[]{id});
        } else {
            cursor = sqLiteDatabase.rawQuery("UPDATE Articles SET IsFavorite = 1 WHERE id = ?", new String[]{id});

        }
        cursor.moveToFirst();
        cursor.close();
    }

    public static int getFavorite(int idAudio) {
        int isFavorite;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT IsFavorite FROM Articles where id = ?", new String[]{idAudio + ""});
        cursor.moveToFirst();
        isFavorite = cursor.getInt(0);
        cursor.close();
        return isFavorite;
    }

    public static void updateDeleted(int idAudio) {
        String id = "" + idAudio;
        Cursor cursor = sqLiteDatabase.rawQuery("UPDATE Articles SET IsDownloaded = 0 WHERE id = ?", new String[]{id});
        cursor.moveToFirst();
        cursor.close();
    }

    private static void checkFileExists(Audio audio) {

        File extStore = Environment.getExternalStorageDirectory();
        File myFile = new File(extStore.getAbsolutePath() + "/" + EssentialUtils.FOLDER_NAME + "/" + audio.idAudio + ".mp3");

        if (myFile.exists() && audio.isDownload == 1) {
            audio.isDownload = 1;
        } else {
            audio.isDownload = 0;
        }

    }

    public static boolean isFileExists(String name) {
        File extStore = Environment.getExternalStorageDirectory();
        File myFile = new File(extStore.getAbsolutePath() + "/" + EssentialUtils.FOLDER_NAME + "/" + name + ".mp3");

        if (myFile.exists())
            return true;

        return false;
    }

    public static void updateRecent(Audio audio) {
        int newRecent = getMaxRecent() + 1;
        String value = newRecent + "";
        Cursor cursor = sqLiteDatabase.rawQuery("UPDATE  Articles SET LastOpen= ? WHERE id =?", new String[]{value, audio.idAudio + ""});
        cursor.moveToFirst();
        cursor.close();

    }

    public static int getMaxRecent() {
        int max;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM  Articles WHERE LastOpen >0 order by LastOpen desc limit 1", null);
        if (cursor.getCount() == 0) return 0;
        cursor.moveToFirst();

        max = cursor.getInt(7);

        cursor.close();
        return max;

    }
}

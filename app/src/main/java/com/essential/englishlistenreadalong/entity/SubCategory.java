package com.essential.englishlistenreadalong.entity;

/**
 * Created by Thanh on 22/02/2016.
 */
public class SubCategory {
    private int idSubCategory;
    private String nameSubCategory;
    private int totalOfAudio;
    private int idCategory;
    public SubCategory(){

    }

    public int getIdSubCategory() {
        return idSubCategory;
    }

    public String getNameSubCategory() {
        return nameSubCategory;
    }

    public void setIdSubCategory(int idSubCategory) {
        this.idSubCategory = idSubCategory;
    }

    public void setNameSubCategory(String nameSubCategory) {
        this.nameSubCategory = nameSubCategory;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getTotalOfAudio() {
        return totalOfAudio;
    }

    public void setTotalOfAudio(int totalOfAudio) {
        this.totalOfAudio = totalOfAudio;
    }
}

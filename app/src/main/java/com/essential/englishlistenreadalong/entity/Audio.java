package com.essential.englishlistenreadalong.entity;

/**
 * Created by Thanh on 24/02/2016.
 */
public class Audio {
    public int idAuio;
    public String nameAuido;
    public int isDownload = 0;
    private String url;

    public Audio(){
    }

    public int getId() {
        return idAuio;
    }

    public void setIdAuio(int idAuio) {
        this.idAuio = idAuio;
    }

    public String getNameAuido() {
        return nameAuido;
    }

    public void setNameAuido(String nameAuido) {
        this.nameAuido = nameAuido;
    }

    public int isDownload() {
        return isDownload;
    }

    public void setIsDownload(int isDownload) {
        this.isDownload = isDownload;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

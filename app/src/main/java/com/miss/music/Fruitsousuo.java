package com.miss.music;

import android.graphics.Bitmap;

public class Fruitsousuo  {

    private String image;
    private String name;
    private  String songid;
    private String gesou;
    public String getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
    public String getgesou() {
        return gesou;
    }
    public  String getSongid(){return songid;}
    public Fruitsousuo(String image, String name, String gesou,String songid) {
        this.image = image;
        this.name = name;
        this.gesou = gesou;
        this.songid=songid;
    }

}

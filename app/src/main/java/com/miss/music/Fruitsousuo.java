package com.miss.music;

import android.graphics.Bitmap;

public class Fruitsousuo  {

    private Bitmap image;
    private String name;
    private  String songid;
    private String gesou;
    public Bitmap getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
    public String getgesou() {
        return gesou;
    }
    public  String getSongid(){return songid;}
    public Fruitsousuo(Bitmap image, String name, String gesou,String songid) {
        this.image = image;
        this.name = name;
        this.gesou = gesou;
        this.songid=songid;
    }

}

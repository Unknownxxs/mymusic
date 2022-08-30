package com.miss.music;

import android.graphics.Bitmap;

import java.util.List;

public class fruitfaxian {
    private List<Bitmap> image;
private List<String>  gedanid;
    private List<String> name;

    public List<Bitmap>  getImage() {
        return image;
    }

    public List<String> getName() {
        return name;
    }

    public List<String> getGedanid() {
        return gedanid;
    }

    public fruitfaxian(List<Bitmap> image, List<String> name, List<String> gedanid) {
        this.image = image;
        this.name = name;
        this.gedanid=gedanid;

    }
}

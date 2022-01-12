package com.sarfaraj.wallpaper_app.Models;

public class Category_Model {
    String category_txt,category_img_url;

    public Category_Model(String category_txt, String category_img_url) {
        this.category_txt = category_txt;
        this.category_img_url = category_img_url;
    }

    public String getCategory_txt() {
        return category_txt;
    }

    public void setCategory_txt(String category_txt) {
        this.category_txt = category_txt;
    }

    public String getCategory_img_url() {
        return category_img_url;
    }

    public void setCategory_img_url(String category_img_url) {
        this.category_img_url = category_img_url;
    }
}

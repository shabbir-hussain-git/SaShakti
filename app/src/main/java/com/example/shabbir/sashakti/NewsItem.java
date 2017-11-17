package com.example.shabbir.sashakti;

/**
 * Created by Shabbir Hussain on 3/29/2017.
 */

public class NewsItem {
    String title,description;
    String bitmap,url;

    public String getUrl() {
        return url;
    }

    public String getBitmap() {
        return bitmap;
    }

    NewsItem(String title, String description, String bitmap, String url){
        this.title=title;
        this.description=description;
        this.bitmap=bitmap;
        this.url=url;

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

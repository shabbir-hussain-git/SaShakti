package com.example.shabbir.sashakti;

/**
 * Created by shabbir on 11/16/2017.
 */
public class General {
    String name;
    int imageResource;

    General(String name,int imageResource){
        this.name=name;
        this.imageResource=imageResource;
    }
    public String getName() {
        return name;
    }

    public int getImageResource() {
        return imageResource;
    }

    public String getCaseInName(){
        return name.toLowerCase();
    }
}

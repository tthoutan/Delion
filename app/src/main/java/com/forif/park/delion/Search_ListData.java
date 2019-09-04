package com.forif.park.delion;

import android.graphics.drawable.Drawable;

/**
 * Created by park on 2015-08-06.
 */
public class Search_ListData {
    private String SearchNameText;
    private String SearchIdText;
    private String SearchPnumText;
    private String SearchLauText;
    private Drawable SearchImage;

    public Search_ListData(String Name, String Id, String Pnum, Drawable Image, String Lau){
        SearchNameText = Name;
        SearchIdText = Id;
        SearchPnumText = Pnum;
        SearchImage = Image;
        SearchLauText = Lau;
    }
    public String getSearchNameText(){return SearchNameText;}
    public String getSearchIdText(){return SearchIdText;}
    public String getSearchPnumText(){return SearchPnumText;}
    public Drawable getSearchImage(){return SearchImage;}
    public String getSearchLauText(){return SearchLauText;}
}

package com.forif.park.delion;

import android.graphics.drawable.Drawable;

/**
 * Created by park on 2015-08-06.
 */
    public class Convenient_ListData {
    private Drawable cStoreIcon;
    private String cStoreText;
    private String cStoreId;
    private String cStoreUrl;

    public Convenient_ListData(Drawable Icon, String Text, String id, String url){
        cStoreIcon = Icon;
        cStoreText = Text;
        cStoreId = id;
        cStoreUrl = url;
    }
    public Drawable getcStoreIcon(){return cStoreIcon;}
    public String getcStoreText(){return cStoreText;}
    public String getcStoreId(){return cStoreId;}
    public String getcStoreUrl(){return cStoreUrl;}
}

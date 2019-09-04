package com.forif.park.delion;

import android.graphics.drawable.Drawable;

/**
 * Created by park on 2015-08-06.
 */
public class Delivery_ListData {
    private Drawable mMenuIcon;
    private String mMenuText;

    public Delivery_ListData(Drawable Icon, String Text){
        mMenuIcon = Icon;
        mMenuText = Text;
    }
    public Drawable getmMenuIcon(){return mMenuIcon;}
    public String getmMenuText(){return mMenuText;}
}

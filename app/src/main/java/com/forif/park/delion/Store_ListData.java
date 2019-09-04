package com.forif.park.delion;

import android.graphics.drawable.Drawable;

/**
 * Created by park on 2015-08-06.
 */
public class Store_ListData {
    private Drawable mStoreIcon;
    private String mStoreText;
    private Drawable mCallIcon;
    private String mStoreId;
    private String mPnumber;

    public Store_ListData(Drawable Icon, String Text, Drawable call, String Id, String pnumber){
        mStoreIcon = Icon;
        mStoreText = Text;
        mCallIcon = call;
        mStoreId = Id;
        mPnumber = pnumber;

    }
    public Drawable getmStoreIcon(){return mStoreIcon;}
    public String getmStoreText(){return mStoreText;}
    public Drawable getmCallIcon(){return mCallIcon;}
    public String getmStoreId(){return mStoreId;}
    public String getmPnumber(){return mPnumber;}
}

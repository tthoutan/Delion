package com.forif.park.delion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by park on 2015-08-06.
 */
public class Delivery_ListViewAdapter extends BaseAdapter {


    private Delivery_ListData mListCheck; //검사용으로 쓰이는 변수이기 때문에 Check
    private Context mContext; //Context변수 mContext 선언

    private ArrayList<Delivery_ListData> mListData; //커스터마이징 자료형을 어레이로 받는 어레이리스트 변수 mListData 선언

    public Delivery_ListViewAdapter(Context mContext){
        super();
        this.mContext = mContext;
        mListData = new ArrayList<Delivery_ListData>();
    }
    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Delivery_ViewHolder holder;
        if(v == null){
            holder = new Delivery_ViewHolder();
            v= ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.delivery_listview_item, null);
            holder.mIcon = (ImageView)v.findViewById(R.id.Main_Menu_Icon);
            holder.mText = (TextView)v.findViewById(R.id.Main_Menu_Text);
            v.setTag(holder);
        }else{
            holder = (Delivery_ViewHolder)v.getTag();
        }

        mListCheck = mListData.get(position);
        if (mListCheck.getmMenuIcon() != null){
            holder.mIcon.setVisibility(View.VISIBLE);
            holder.mIcon.setImageDrawable(mListCheck.getmMenuIcon());
        }else{
            holder.mIcon.setVisibility(View.GONE);
        }
        holder.mText.setText(mListCheck.getmMenuText());

        return v;
    }
    public void addItem(Delivery_ListData MenuData) {
        mListData.add(MenuData);
    }


    //온클릭 함수


}

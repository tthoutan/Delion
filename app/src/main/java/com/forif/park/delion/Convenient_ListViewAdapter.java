package com.forif.park.delion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.forif.park.delion.R;import java.util.ArrayList;

/**
 * Created by park on 2015-08-06.
 */
public class Convenient_ListViewAdapter extends BaseAdapter {


    private Convenient_ListData cListCheck; //검사용으로 쓰이는 변수이기 때문에 Check
    private Context cContext; //Context변수 mContext 선언

    private ArrayList<Convenient_ListData> cListData; //커스터마이징 자료형을 어레이로 받는 어레이리스트 변수 mListData 선언

    public Convenient_ListViewAdapter(Context cContext){
        super();
        this.cContext = cContext;
        cListData = new ArrayList<Convenient_ListData>();
    }
    @Override
    public int getCount() {
        return cListData.size();
    }

    @Override
    public Object getItem(int position) {
        return cListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Convenient_ViewHolder holder;
        if(v == null){
            holder = new Convenient_ViewHolder();
            v= ((LayoutInflater)cContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.conveninet_store_listview_item, null);
            holder.cIcon = (ImageView)v.findViewById(R.id.convenient_store_icon);
            holder.cText = (TextView)v.findViewById(R.id.conveninet_store_text);
            holder.cId = (TextView)v.findViewById(R.id.convenient_store_id);
            holder.cUrl = (TextView)v.findViewById(R.id.convenient_store_url);
            v.setTag(holder);
        }else{
            holder = (Convenient_ViewHolder)v.getTag();
        }

        cListCheck = cListData.get(position);
        if (cListCheck.getcStoreIcon() != null){
            holder.cIcon.setVisibility(View.VISIBLE);
            holder.cIcon.setImageDrawable(cListCheck.getcStoreIcon());
        }else{
            holder.cIcon.setVisibility(View.GONE);
        }
        holder.cText.setText(cListCheck.getcStoreText());
        holder.cId.setText(cListCheck.getcStoreId());
        holder.cUrl.setText(cListCheck.getcStoreUrl());

        return v;
    }
    public void addItem(Convenient_ListData MenuData) {
        cListData.add(MenuData);
    }


    //온클릭 함수


}

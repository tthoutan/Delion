package com.forif.park.delion;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.forif.park.delion.R;import java.util.ArrayList;

public class Store_ListViewAdapter extends BaseAdapter {

    private Store_ListData sListCheck;
    private Context sContext;
    private ArrayList<Store_ListData> sListData;

    public Store_ListViewAdapter(Context sContext){
        super();
        this.sContext = sContext;
        sListData = new ArrayList<Store_ListData>();
    }
    @Override
    public int getCount() {
        return sListData.size();
    }

    @Override
    public Object getItem(int position) {
        return sListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Store_ViewHolder holder;
        if(v == null){
            holder = new Store_ViewHolder();
            v= ((LayoutInflater)sContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.store_listview_item, null);
            holder.sIcon = (ImageView)v.findViewById(R.id.Store_Label_Icon);
            holder.sText = (TextView)v.findViewById(R.id.Store_Name_Text);
            holder.Calling = (ImageView)v.findViewById(R.id.Calling);
            holder.sId = (TextView)v.findViewById(R.id.main_store_id);
            holder.pnumber = (TextView)v.findViewById(R.id.main_pnumber);
            v.setTag(holder);
        }else{
            holder = (Store_ViewHolder)v.getTag();
        }

        sListCheck = sListData.get(position);
        if (sListCheck.getmStoreIcon() != null){
            holder.sIcon.setVisibility(View.VISIBLE);
            holder.sIcon.setImageDrawable(sListCheck.getmStoreIcon());
        }else{
            holder.sIcon.setVisibility(View.GONE);
        }
        holder.sId.setText(sListCheck.getmStoreId());
        holder.sText.setText(sListCheck.getmStoreText());
        holder.pnumber.setText(sListCheck.getmPnumber());
        holder.Calling.setImageDrawable(sListCheck.getmCallIcon());

        final TextView finalV = (TextView)v.findViewById(R.id.main_pnumber);

        holder.Calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView pn = finalV;
                String pnumber = pn.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+ pnumber));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sContext.startActivity(intent);

            }
        });
        return v;
    }
    public void addItem(Store_ListData StoreData) {
        sListData.add(StoreData);
    }





}
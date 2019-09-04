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

/**
 * Created by park on 2015-08-06.
 */
public class Search_ListViewAdapter extends BaseAdapter {


    private Search_ListData SearchListCheck; //검사용으로 쓰이는 변수이기 때문에 Check
    private Context SearchContext; //Context변수 mContext 선언

    private ArrayList<Search_ListData> SearchListData; //커스터마이징 자료형을 어레이로 받는 어레이리스트 변수 mListData 선언

    public Search_ListViewAdapter(Context SearchContext){
        super();
        this.SearchContext = SearchContext;
        SearchListData = new ArrayList<Search_ListData>();
    }
    @Override
    public int getCount() {
        return SearchListData.size();
    }

    @Override
    public Object getItem(int position) {
        return SearchListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Search_ViewHolder holder;
        if(v == null){
            holder = new Search_ViewHolder();
            v= ((LayoutInflater)SearchContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.search_listview_item, null);
            holder.SearchId = (TextView)v.findViewById(R.id.search_id);
            holder.SearchName = (TextView)v.findViewById(R.id.search_name);
            holder.SearchPnum = (TextView)v.findViewById(R.id.search_pnum);
            holder.SearchImage = (ImageView)v.findViewById(R.id.search_image);
            holder.SearchLau = (TextView)v.findViewById(R.id.search_lau);
            v.setTag(holder);
        }else{
            holder = (Search_ViewHolder)v.getTag();
        }

        SearchListCheck = SearchListData.get(position);
        if (SearchListCheck.getSearchImage() != null){
            holder.SearchImage.setVisibility(View.VISIBLE);
            holder.SearchImage.setImageDrawable(SearchListCheck.getSearchImage());
        }else{
            holder.SearchImage.setVisibility(View.GONE);
        }
        holder.SearchName.setText(SearchListCheck.getSearchNameText());
        holder.SearchId.setText(SearchListCheck.getSearchIdText());
        holder.SearchPnum.setText(SearchListCheck.getSearchPnumText());
        holder.SearchLau.setText(SearchListCheck.getSearchLauText());

        final TextView finalV = (TextView)v.findViewById(R.id.search_pnum);
        holder.SearchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView pntv = finalV;
                String pnum = pntv.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+ pnum));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SearchContext.startActivity(intent);

            }
        });
        return v;
    }
    public void addItem(Search_ListData MenuData) {
        SearchListData.add(MenuData);
    }


    //온클릭 함수


}

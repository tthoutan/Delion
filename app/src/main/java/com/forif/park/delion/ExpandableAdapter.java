package com.forif.park.delion;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forif.park.delion.R;import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by park on 2015-08-19.
 */
public class ExpandableAdapter extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    private ArrayList<String> groupList = null;
    private HashMap<String, ArrayList<String>> childList = null;
    private LayoutInflater inflater = null;
    private Crazy_ViewHolder viewHolder = null;

    public ExpandableAdapter(Context c, ArrayList<String> groupList, HashMap<String, ArrayList<String>> childList){
        super();
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
    }

    @Override
    public String getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v= convertView;
        String groupName = groupList.get(groupPosition);
        if(v==null){
            viewHolder = new Crazy_ViewHolder();
            v = inflater.inflate(R.layout.menu_elv_item, parent, false);
            viewHolder.part_text = (TextView)v.findViewById(R.id.part_text);
            viewHolder.part_image = (ImageView)v.findViewById(R.id.part_image);
            v.setTag(viewHolder);
        }else{
            viewHolder = (Crazy_ViewHolder)v.getTag();
        }

        if(isExpanded){
            viewHolder.part_image.setBackgroundColor(Color.GRAY);
        }else{
            viewHolder.part_image.setBackgroundColor(Color.WHITE);
        }

        viewHolder.part_text.setText(groupName);


        return v;
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return childList.get(groupList.get(groupPosition)).get(childPosition);
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return childList.get(groupList.get(groupPosition)).size();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getRealChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
            String childName = this.getChild(groupPosition, childPosition);
        if(v==null){
            viewHolder = new Crazy_ViewHolder();
            v = inflater.inflate(R.layout.menu_elv_item, null);
            viewHolder.menu_text = (TextView)v.findViewById(R.id.menu_text);
            viewHolder.menu_text.setSingleLine(true);
            viewHolder.menu_text.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            viewHolder.menu_text.setSelected(true);
            v.setTag(viewHolder);
        }else{
            viewHolder = (Crazy_ViewHolder)v.getTag();
        }

        viewHolder.menu_text.setText(childName);
        return v;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class Crazy_ViewHolder{
        public ImageView part_image;
        public TextView part_text;
        public TextView menu_text;
    }
}

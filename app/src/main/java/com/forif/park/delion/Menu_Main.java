package com.forif.park.delion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.Integer;import java.lang.Override;import java.lang.String;import java.lang.Throwable;import java.util.ArrayList;
import java.util.HashMap;


public class Menu_Main extends AppCompatActivity {
    private ArrayList<String> mGroupList = null;
    private HashMap<String ,ArrayList<String>> mChildList = null;

    private AnimatedExpandableListView CrazyListView;
    private ArrayList<String> searchgroup;
    private ArrayList<String> resultgroup;

    ImageView Circle3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String aa = getIntent().getStringExtra("id");

        String base_url = "http://211.110.61.135/forif/index.php/";
        String m =  "main/basiclist/";
        String s = "shop/shop_list/";
        String sm = "shop/shop_menu/";
        String l = "lifeinfo/life_list/";
        String lm = "lifeinfo/life_detail/";
        String number = "0";

        //
        final TextView tv1,tv2,tv3,pnumber ;
        tv1 = (TextView)findViewById(R.id.text1);
        tv2 = (TextView)findViewById(R.id.text2);
//        tv3 = (TextView)findViewById(R.id.text3);
        pnumber = (TextView)findViewById(R.id.menu_pnumber);

        String url = base_url + sm + aa;
        //


        setLayout();
        CrazyListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (CrazyListView.isGroupExpanded(groupPosition)){
                    CrazyListView.collapseGroupWithAnimation(groupPosition);
                }else{
                    CrazyListView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }
        });
        mGroupList = new ArrayList<String>();
        mChildList = new HashMap<String, ArrayList<String>>();
        searchgroup = new ArrayList<String>();
        resultgroup = new ArrayList<String>();
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String content = null;
                try {
                    content = new String(responseBody, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {

                    JSONObject jsonObject = new JSONObject();
                    JSONObject jsonOb = new JSONObject();

                    int count = 0;
                    JSONArray jsonArray = new JSONArray(content);

//                    mChildListContent = new ArrayList<String>();


                    for (int j = 0; j < jsonArray.length(); j++) {
                        boolean group = true;
                        JSONObject json = jsonArray.getJSONObject(j);
                        String test = json.getString("extender_menu");
                        searchgroup.add(test);
                        for (int k = 0; k < j; k++) {
                            if (searchgroup.get(j).equals(searchgroup.get(k))) {
                                group = false;
                            }
                        }

                        if (group == true) {
                            resultgroup.add(searchgroup.get(j));
                            count++;
                        }
                    }

                    for (int c = 0; c < count; c++) {
                        mGroupList.add(resultgroup.get(c));
//                        Toast.makeText(Menu_Main.this, resultgroup.get(c), Toast.LENGTH_SHORT).show();
                    }

                    for (int i = 0; i < 1; i++) {

                        JSONObject json = jsonArray.getJSONObject(i);

//                        String t = json.getString("id");
                        String t1 = json.getString("phone");
//                        String t2 = json.getString("img");
                        String t3 = json.getString("name");
                        String t4 = json.getString("opentime");
//                        String t6 = json.getString("extender_menu");
//                        String t7 = json.getString("menu");
//                        String t8 = json.getString("price");

                        tv1.setText("영업시간\n"+t4);
                        tv2.setText(t3);
//                        tv3.setText("");
//                        TextView v = (TextView) findViewById(R.id.title);
//                        v.setText(t3);
                        getSupportActionBar().setTitle(t3);
                        pnumber.setText(t1);


                    }

                    for (int ob = 0; ob < resultgroup.size(); ob++) {

                        JSONArray cell = new JSONArray();
                        int cc = 0;
                        for (int cl = 0; cl < jsonArray.length(); cl++) {
                            JSONObject json = jsonArray.getJSONObject(cl);
                            String t6 = json.getString("extender_menu");
                            String t7 = json.getString("menu");
                            String t8 = json.getString("price");
                            if (t6.equals(resultgroup.get(ob))) {
                                JSONObject obj = new JSONObject();
                                obj.put("menu" + Integer.toString(cc), t7+" : "+t8+"원");
                                cell.put(obj);
                                cc++;
                            }
                        }

                        jsonObject.put(resultgroup.get(ob), cell);
                    }

                    ArrayList<ArrayList<String>> arrlist = new ArrayList<ArrayList<String>>();

                    for (int arr = 0; arr < resultgroup.size(); arr++) {

                        JSONArray cell;
                        cell = jsonObject.getJSONArray(resultgroup.get(arr));
                        arrlist.add(new ArrayList<String>());
                        for (int js = 0; js < cell.length(); js++) {
                            jsonOb = cell.getJSONObject(js);
                            arrlist.get(arr).add(jsonOb.getString("menu" + Integer.toString(js)));
                        }

                        mChildList.put(mGroupList.get(arr), arrlist.get(arr));

                    }
                    CrazyListView.setAdapter(new ExpandableAdapter(getApplicationContext(), mGroupList, mChildList));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
            }

        });

        Circle3 = (ImageView)findViewById(R.id.circle3);

        Circle3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pnumber = pnumber.getText().toString();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+Pnumber));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        else if(id == R.id.action_inquire){
            Intent intent = new Intent(getApplicationContext(), Inquire_Main.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setLayout() {
        CrazyListView = (AnimatedExpandableListView) findViewById(R.id.elv_crazy);
    }





}

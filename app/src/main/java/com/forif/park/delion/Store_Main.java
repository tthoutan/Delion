package com.forif.park.delion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;


public class Store_Main extends ActionBarActivity {
    private ListView sListView = null;
    private Store_ListViewAdapter sAdapter = null;



//    @Override
//    protected void onResume(){
//        super.onResume();
//
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        sAdapter = new Store_ListViewAdapter(getApplicationContext());
        sListView = (ListView)findViewById(R.id.Store_List_View);
        sListView.setAdapter(sAdapter);
        sListView.setOnItemClickListener(new ListClickHandler());




        //
        String base_url = "http://211.110.61.135/forif/index.php/";

        String s = "shop/shop_list/";

        //
        int a = getIntent().getExtras().getInt("position");
        String aa = String.valueOf(a);

        switch (aa){
            case "1" : getSupportActionBar().setTitle("치킨");
                break;
            case "2" : getSupportActionBar().setTitle("중국집");
                break;
            case "3" : getSupportActionBar().setTitle("피자");
                break;
            case "4" : getSupportActionBar().setTitle("한식");
                break;
            case "5" : getSupportActionBar().setTitle("분식");
                break;
            case "6" : getSupportActionBar().setTitle("패스트푸드");
                break;
            case "7" : getSupportActionBar().setTitle("족발");
                break;
            default: getSupportActionBar().setTitle("no-data");
                break;
        }

        String url = base_url + s + aa;

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
                    JSONArray jsonArray = new JSONArray(content);

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject json = jsonArray.getJSONObject(i);

                        String t = json.getString("id");
                        String t1 = json.getString("name");
//                        String t2 = json.getString("img");
                        String t3 = json.getString("phone");
                        String t4 = json.getString("state");

                        Store_ListData s1 = new Store_ListData(null,  t1, getResources().getDrawable(R.drawable.call), t, t3);
                        sAdapter.addItem(s1);
                        sAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem SearchItem = menu.findItem(R.id.action_search);
        SearchView searchview = (SearchView) MenuItemCompat.getActionView(SearchItem);



        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchFunction(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

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

    public void SearchFunction(String message){

        RequestParams params = new RequestParams();
        params.put("name", message);
//
        String url_search = "http://211.110.61.135/forif/index.php/search/contents";
        AsyncHttpClient client_search = new AsyncHttpClient();
//
        client_search.post(url_search, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, org.apache.http.Header[] headers, byte[] bytes) {
                String content = null;
                try {
                    content = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray jsonArray = new JSONArray(content);

                    Intent intent = new Intent(getApplicationContext(), Search_Result.class);
                    ArrayList<String> arrid = new ArrayList<String>();
                    ArrayList<String> arrname = new ArrayList<String>();
                    ArrayList<String> arrpnum = new ArrayList<String>();
                    ArrayList<String> arrlau = new ArrayList<String>();

                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject json = jsonArray.getJSONObject(j);

                        String t = json.getString("id");
                        String t1 = json.getString("name");
                        String t2 = json.getString("life_add_url");
                        String t3 = json.getString("phone");

                        arrid.add(t);
                        arrname.add(t1);
                        arrpnum.add(t3);
                        arrlau.add(t2);


                    }

                    if (arrid.size() == 0){
                        Toast.makeText(getApplicationContext(), "검색결과가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        intent.putStringArrayListExtra("id", arrid);
                        intent.putStringArrayListExtra("name", arrname);
                        intent.putStringArrayListExtra("pnum", arrpnum);
                        intent.putStringArrayListExtra("lau", arrlau);
                        startActivity(intent);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, org.apache.http.Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

            TextView listText = (TextView) view.findViewById(R.id.main_store_id);
            String text = listText.getText().toString();
//            Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Store_Main.this, Menu_Main.class);
            intent.putExtra("id", text);
            startActivity(intent);
            overridePendingTransition(0,0);
        }

    }

}
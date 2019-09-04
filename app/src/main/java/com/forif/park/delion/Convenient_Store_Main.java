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
import android.widget.EditText;
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


public class Convenient_Store_Main extends ActionBarActivity {
    private ListView cListView = null; //메인리스트뷰를 받을 자바상의 리스트뷰 변수 선언
    private Convenient_ListViewAdapter cAdapter = null; //메인리스트뷰의 어뎁터로 쓰일 어뎁터 변수 선언

    //탭바
//    Button button1;
//    Button button2;
//    Button button3;
//    Button button4;

    //---------

    EditText search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convenient_store_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //검색기능

        cAdapter = new Convenient_ListViewAdapter(getApplicationContext());
        cListView = (ListView)findViewById(R.id.conveninent_store_list_view);
        cListView.setAdapter(cAdapter);
        cListView.setOnItemClickListener(new ListClickHandler());



        //
        String base_url = "http://211.110.61.135/forif/index.php/";
        String l = "lifeinfo/life_list/";

        //
        int a = getIntent().getExtras().getInt("position");
        String aa = String.valueOf(a);
        String url = base_url + l + aa;
        //
        switch (aa){
            case "8":
                getSupportActionBar().setTitle("세탁소");
                break;
            case "9":
                getSupportActionBar().setTitle("편의점");
                break;
            case "10":
                getSupportActionBar().setTitle("약국");
                break;
            case "11":
                getSupportActionBar().setTitle("병원");
                break;
            case "12":
                getSupportActionBar().setTitle("인쇄소");
                break;
            case "13":
                getSupportActionBar().setTitle("문구점");
                break;
            case "14":
                getSupportActionBar().setTitle("은행, ATM");
                break;
            case "15":
                getSupportActionBar().setTitle("의류수선");
                break;
            default:
                getSupportActionBar().setTitle("no-data");
                break;
        }

        //
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
                        String t2 = json.getString("life_add_url");
                        String t3 = json.getString("state");

                        Convenient_ListData u = new Convenient_ListData(null, t1, t, t2);
                        cAdapter.addItem(u);
                        cAdapter.notifyDataSetChanged();
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

    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            // TODO Auto-generated method stub
            TextView listText = (TextView) view.findViewById(R.id.convenient_store_id);
            TextView urlText = (TextView) view.findViewById(R.id.convenient_store_url);
            TextView nameText = (TextView) view.findViewById(R.id.conveninet_store_text);
            String url = urlText.getText().toString();
            String text = listText.getText().toString();
            String name = nameText.getText().toString();
//            Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Convenient_Store_Main.this, Convenient_Store_Detail.class);
            intent.putExtra("id", text);
            intent.putExtra("url", url);
            intent.putExtra("name", name);
            startActivity(intent);
            overridePendingTransition(0,0);
        }

    }
//
//    @Override
//    protected void onResume(){
//        super.onResume();
//
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//    }
}

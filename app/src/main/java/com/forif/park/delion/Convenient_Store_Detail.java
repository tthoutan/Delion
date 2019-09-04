package com.forif.park.delion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class Convenient_Store_Detail extends ActionBarActivity implements OnMapReadyCallback {

//    Button button1;
//    Button button2;
//    Button button3;
//    Button button4;


    String urlm;
    String name;
    int a, b, c;


    String xx;
    String yy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.convenient_store_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            String aa = getIntent().getStringExtra("id");



        //
        String base_url = "http://211.110.61.135/forif/index.php/";
        String lm = "lifeinfo/life_detail/";

        ////

        urlm = getIntent().getStringExtra("url");
        name = getIntent().getStringExtra("name");

        a = urlm.indexOf("x=");
        b = urlm.indexOf("&y=");
        c = urlm.indexOf("&enc=");


        xx = urlm.substring(a + 2, b);
        yy = urlm.substring(b + 3, c);

        final TextView tv1,tv2,tv3,tv4;
        tv1 = (TextView)findViewById(R.id.opentime);
        tv2 = (TextView)findViewById(R.id.lifecall);
        tv3 = (TextView)findViewById(R.id.lifename);
        tv4 = (TextView)findViewById(R.id.life_add);
        tv4.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv4.setSingleLine(true);
        tv4.setSelected(true);
        String url = base_url + lm + aa;

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
                                String t2 = json.getString("opentime");
                                String t3 = json.getString("phone");
//                                String t4 = json.getString("img");
                                String t5 = json.getString("life_add");
                                String t7 = json.getString("state");

                                tv1.setText(t2);
                                tv2.setText(t3);
                                tv3.setText(t1);
                                tv4.setText(t5);
//                                TextView textview = (TextView) findViewById(R.id.title);
//                                textview.setText(t1);
                                getSupportActionBar().setTitle(t1);
//                                Toast.makeText(getApplicationContext(), t+t1+t2+t3+t4+t5+t6, Toast.LENGTH_SHORT).show();

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


        //

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



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

    @Override
    public void onMapReady(GoogleMap map) {

        double y = Double.parseDouble(xx);
        double x = Double.parseDouble(yy);

        String title = name;
        LatLng location = new LatLng(x, y);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 17));

        map.addMarker(new MarkerOptions()
                .title(title)
                .position(location));
    }

}

package com.forif.park.delion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Park on 2015-11-22.
 */
public class Tab_Bar_Main extends AppCompatActivity{
    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
/////////////////
    private ListView mListView = null; //메인리스트뷰를 받을 자바상의 리스트뷰 변수 선언
    private Delivery_ListViewAdapter mAdapter = null; //메인리스트뷰의 어뎁터로 쓰일 어뎁터 변수 선언
/////////////////
    private final long   FINSH_INTERVAL_TIME    = 2000;
    private long      backPressedTime        = 0;
   /////////////////
//    Activity act = this;
    private GridView gridView = null;
    HashMap<String, Bitmap> picArr = new HashMap<String, Bitmap>();
    ArrayList<String> textArr = new ArrayList<String>();
    private gridadapter cAdapter = null;
//    ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();





    @Override
    public void onBackPressed() {
        long tempTime        = System.currentTimeMillis();
        long intervalTime    = tempTime - backPressedTime;

        if ( 0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime ) {
            super.onBackPressed();
        }
        else {
            backPressedTime = tempTime;


            Toast.makeText(getApplicationContext(),"'뒤로'버튼을 한 번 더 누르면 종료.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("홈");


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



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

        if(id == R.id.action_inquire){
            Intent intent = new Intent(getApplicationContext(), Inquire_Main.class);
            startActivity(intent);
        }
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }



    public  class Fragment_Delivery extends Fragment{

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.delivery_activity, container, false);
            final Activity activity = getActivity();

            mAdapter = new Delivery_ListViewAdapter(getApplicationContext());
            mListView = (ListView) view.findViewById(R.id.List_View_Main);
            mListView.setAdapter(mAdapter);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), Store_Main.class);
                    intent.putExtra("position", position + 1);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });

            String base_url = "http://211.110.61.135/forif/index.php/";
            String m =  "main/basiclist/";

            String url = base_url + m + "0";
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
                            String t1 = json.getString("listname");
//                        String t2 = json.getString("img");

                            Delivery_ListData u = new Delivery_ListData(null, t1);
                            mAdapter.addItem(u);
                            mAdapter.notifyDataSetChanged();


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    Toast.makeText(activity, "fail", Toast.LENGTH_SHORT).show();
                }


            });




            return view;
        }

    }

    public class Fragment_Convenient extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState){
            View view = inflater.inflate(R.layout.convenient_main, container, false);
            final Activity activity = getActivity();

            cAdapter = new gridadapter(getApplicationContext());
            gridView = (GridView) view.findViewById(R.id.convenient_grid_view);
            gridView.setAdapter(cAdapter);



            String base_url = "http://211.110.61.135/forif/index.php/";
            String m =  "main/basiclist/";

            String url = base_url + m + "1";
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

                        final Bitmap bm11 = BitmapFactory.decodeResource(getResources(), R.drawable.hospital);
                        final Bitmap bm10 = BitmapFactory.decodeResource(getResources(), R.drawable.medicine);
                        final Bitmap bm14 = BitmapFactory.decodeResource(getResources(), R.drawable.bank);
                        final Bitmap bm9 = BitmapFactory.decodeResource(getResources(), R.drawable.convenient);
                        final Bitmap bm12 = BitmapFactory.decodeResource(getResources(), R.drawable.print);
                        final Bitmap bm8 = BitmapFactory.decodeResource(getResources(), R.drawable.laundry);
                        final Bitmap bmdf = BitmapFactory.decodeResource(getResources(), R.drawable.capb);
                        final Bitmap bm13 = BitmapFactory.decodeResource(getResources(), R.drawable.fancy);
                        final Bitmap bm15 = BitmapFactory.decodeResource(getResources(), R.drawable.clothes);




                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject json = jsonArray.getJSONObject(i);

                            String t = json.getString("id");
                            String t1 = json.getString("listname");
//                        String t2 = json.getString("img");
//                            switch (t) {
//                                case "8":
//                                    picArr.put(t, bm8);
//                                    break;
//                                case "9":
//                                    picArr.put(t, bm9);
//                                    break;
//                                case "10":
//                                    picArr.put(t, bm10);
//                                    break;
//                                case "11":
//                                    picArr.put(t, bm11);
//                                    break;
//                                case "12":
//                                    picArr.put(t, bm12);
//                                    break;
//                                case "13":
//                                    picArr.put(t, bm13);
//                                    break;
//                                case "14":
//                                    picArr.put(t, bm14);
//                                    break;
//                                case "15":
//                                    picArr.put(t, bm15);
//                                    break;
//                                default:
//                                    picArr.put(t, bmdf);
//                                    break;
//                            }

                            textArr.add(t1);

                        }

                        picArr.put("8", bm8);
                        picArr.put("9", bm9);
                        picArr.put("10", bm10);
                        picArr.put("11", bm11);
                        picArr.put("12", bm12);
                        picArr.put("13", bm13);
                        picArr.put("14", bm14);
                        picArr.put("15", bm15);

                        cAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_SHORT).show();
                }
            });


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), Convenient_Store_Main.class);
                    intent.putExtra("position", position + 8);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
            });

        return view;
    }

    }

    public class gridadapter extends BaseAdapter {
        LayoutInflater inflater;
        Context context = null;

        public gridadapter(Context context){
            super();
            this.context = context;
        }

        @Override
        public int getCount() {
            return picArr.size();
        }

        @Override
        public Object getItem(int position) {
            return picArr.get(Integer.toString(position+8));
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.convenient_grid_item, parent, false);
            }
            ImageView imageView = (ImageView)convertView.findViewById(R.id.grid_image);
            TextView textView = (TextView)convertView.findViewById(R.id.grid_text);
            imageView.setImageBitmap(picArr.get(Integer.toString(position + 8)));
            textView.setText(textArr.get(position));




            return convertView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position ==0){
                return new Fragment_Delivery();
            }
            if(position ==1){
                return new Fragment_Convenient();
            }
            else{
                return PlaceholderFragment.newInstance(position);
            }
        }

        @Override
        public int getCount() {

            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "배달";
                case 1:
                    return "생활정보";
                case 2:
                    return "즐겨찾기";
            }
            return null;
        }
    }


    public static class PlaceholderFragment extends Fragment {


        private static final String ARG_SECTION_NUMBER = "section_number";


        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.delivery_activity, container, false);

//            Button button_test = (Button) rootView.findViewById(R.id.button_test);
            final Activity root = getActivity();
//            button_test.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(root, "dd", Toast.LENGTH_SHORT).show();
//
//                }
//            });


            return rootView;
        }
    }

}

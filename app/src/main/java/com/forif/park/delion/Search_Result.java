package com.forif.park.delion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class Search_Result extends ActionBarActivity {
    private ListView SListView = null;
    private Search_ListViewAdapter SAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        SAdapter = new Search_ListViewAdapter(getApplicationContext());
        SListView = (ListView)findViewById(R.id.search_list_body);
        SListView.setAdapter(SAdapter);

        SListView.setOnItemClickListener(new SearchListClickHandler());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("검색결과");

        ArrayList<String> search_arrid, search_arrname, search_arrpnum, search_arrlau;

        search_arrid = getIntent().getStringArrayListExtra("id");
        search_arrname = getIntent().getStringArrayListExtra("name");
        search_arrpnum = getIntent().getStringArrayListExtra("pnum");
        search_arrlau =  getIntent().getStringArrayListExtra("lau");


    for (int i = 0; i < search_arrid.size(); i++) {

        Search_ListData S = new Search_ListData(search_arrname.get(i), search_arrid.get(i), search_arrpnum.get(i), getResources().getDrawable(R.drawable.call), search_arrlau.get(i));

        SAdapter.addItem(S);
        SAdapter.notifyDataSetChanged();
    }


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

    public class SearchListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

            TextView listText = (TextView) view.findViewById(R.id.search_id);
            final String text = listText.getText().toString();
            TextView urlText = (TextView)view.findViewById(R.id.search_lau);
            final String url = urlText.getText().toString();
            TextView nameText = (TextView)view.findViewById(R.id.search_name);
            final String name = nameText.getText().toString();
            if (Integer.valueOf(text) <= 32){
                Intent intent = new Intent(Search_Result.this, Menu_Main.class);
                intent.putExtra("id", text);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
            else{
                Intent intent = new Intent(Search_Result.this, Convenient_Store_Detail.class);
                intent.putExtra("id", text);
                intent.putExtra("url", url);
                intent.putExtra("name", name);
                startActivity(intent);
                overridePendingTransition(0, 0);

            }
        }

    }


}

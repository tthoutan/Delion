package com.forif.park.delion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Inquire_Main extends AppCompatActivity {

    EditText search;
    EditText request_title;
    EditText request_contents;
    Button request_send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inquire_main);


        //여기는 재석코드
        request_title = (EditText) findViewById(R.id.requestTitle);
        request_contents= (EditText) findViewById(R.id.requestContents);
        request_send = (Button) findViewById(R.id.requestSend);
        request_send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String req_title = request_title.getText().toString();
                String req_cont = request_contents.getText().toString();

                Intent it = new Intent(Intent.ACTION_SEND);
                //이거 메일주소
                String[] mailaddr = {"Forif.Hanyang@gmail.com"};

                it.setType("plaine/text");
                it.putExtra(Intent.EXTRA_EMAIL, mailaddr);
                it.putExtra(Intent.EXTRA_SUBJECT, req_title);
                it.putExtra(Intent.EXTRA_TEXT, "\n\n" + req_cont);
                startActivity(it);
            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("문의하기");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_inquire, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    }



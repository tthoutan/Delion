package com.forif.park.delion;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;import com.forif.park.delion.R;import java.lang.Override;

/**
 * Created by park on 2015-09-22.
 */
public class More_intro_dev extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_intro_dev);


        getSupportActionBar().setTitle("개발진 소개");

    }


}

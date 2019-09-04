package com.forif.park.delion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;import com.forif.park.delion.R;


/**
 * Created by park on 2015-09-22.
 */
public class FirstIntro extends Activity {
    Handler h;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        h = new Handler();

        h.postDelayed(irun, 2000);
    }
    Runnable irun = new Runnable() {
        @Override
        public void run() {
            Intent i = new Intent(FirstIntro.this, Tab_Bar_Main.class);
            startActivity(i);
            finish();

            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    public void onBackPressed(){
        super.onBackPressed();
        h.removeCallbacks(irun);
    }


}

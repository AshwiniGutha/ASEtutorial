package com.google.vr.sdk.samples.treasurehunt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void start(View v)
    {
        setContentView(R.layout.activity_main);
        Intent redirect = new Intent(MainActivity.this, TreasureHuntActivity.class);
        startActivity(redirect);
    }

}

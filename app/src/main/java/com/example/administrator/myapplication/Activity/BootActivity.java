package com.example.administrator.myapplication.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.myapplication.R;

public class BootActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
    }
    @Override
    protected  void onResume(){
        super.onResume();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            private int abcd =0;

            public void run() {
                startLoginActivity();

            }
        },1000);
    }

    void startLoginActivity(){
        Intent itnt = new Intent(this, LoginActivity.class);
        startActivity(itnt);
        finish();
    }
}

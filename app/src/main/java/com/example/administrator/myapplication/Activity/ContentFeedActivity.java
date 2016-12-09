package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.myapplication.R;

public class ContentFeedActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_feed);

        String content=getIntent().getStringExtra("text");

        TextView textView =(TextView)findViewById(R.id.text);
        textView.setText(content);
    }
}

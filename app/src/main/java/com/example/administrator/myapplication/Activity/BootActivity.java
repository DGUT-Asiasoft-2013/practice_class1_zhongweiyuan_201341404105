package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BootActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);
    }

    @Override
    protected void onResume() {
        super.onResume();

        OkHttpClient httpClient = new OkHttpClient();
        Request request = Server.requestBuilderWithApi("/hello").method("GET", null).build();
        httpClient.newCall(request).enqueue(new Callback() {
            public void onFailure(Call call, final IOException e) {
                BootActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BootActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                BootActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(BootActivity.this, response.body().string(), Toast.LENGTH_LONG).show();
                            startLoginActivity();
                        } catch (IOException e) {
                            onFailure(call, e);
                        }

                    }
                });
            }
        });


//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            private int abcd =0;
//
//            public void run() {
//                startLoginActivity();
//
//            }
//        },1000);
    }

    void startLoginActivity() {
        Intent itnt = new Intent(this, LoginActivity.class);
        startActivity(itnt);
        finish();
    }
}

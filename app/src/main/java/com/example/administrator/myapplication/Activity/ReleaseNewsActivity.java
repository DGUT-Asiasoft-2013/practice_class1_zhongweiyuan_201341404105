package com.example.administrator.myapplication.Activity;


import java.io.IOException;

import com.example.administrator.myapplication.Activity.Server;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.inputcells.SimpleTextInputCellFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReleaseNewsActivity extends Activity {
    SimpleTextInputCellFragment fragInputCellTitle;
    EditText et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release_news);
        fragInputCellTitle = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.editTitle);

        et_content = (EditText) findViewById(R.id.editText);

        Button button = (Button) findViewById(R.id.button_send);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendArticle();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    void sendArticle() {
        String title =  fragInputCellTitle.getText();
        String text = et_content.getText().toString();

        OkHttpClient client = Server.getSharedClient();

        MultipartBody.Builder body = new MultipartBody.Builder().addFormDataPart("title", title).addFormDataPart("text", text);

        Request request = Server.requestBuilderWithApi("/articles/{userId}").method("post", null).post(body.build()).build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                try {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            new AlertDialog.Builder(ReleaseNewsActivity.this)
                                    .setTitle("发送成功")
                                    .setPositiveButton("好", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                            overridePendingTransition(R.anim.none, R.anim.slide_out_bottom);
                                        }
                                    }).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {

            }
        });
    }
}



//import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.example.administrator.myapplication.R;
//
//public class ReleaseNewsActivity extends Activity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_release_news);
//
//        Button button = (Button)findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//                overridePendingTransition(R.anim.none, R.anim.slide_out_bottom);
//            }
//        });
//    }
//
//}

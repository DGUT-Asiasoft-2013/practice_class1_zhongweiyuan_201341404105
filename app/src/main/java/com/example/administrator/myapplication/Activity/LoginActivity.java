package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.PasswordRecoverStep1Fragment;
import com.example.administrator.myapplication.fragment.inputcells.SimpleTextInputCellFragment;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends Activity {


    SimpleTextInputCellFragment account;
    SimpleTextInputCellFragment password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_account);
        password = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_password);

        Button button1 = (Button) findViewById(R.id.btn_REGISTER);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity();
            }
        });
        Button button2 = (Button) findViewById(R.id.btn_EXIT);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        Button button3 = (Button) findViewById(R.id.btn_Login);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHelloWorld();
            }
        });
        TextView textView = (TextView) findViewById(R.id.btn_forget_password);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPasswordRecoverActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        account.setHintText("请输入登录名");
        account.setLabelText("登录名");
        password.setHintText("请输入密码");
        password.setLabelText("密码");
        password.setIsPassword(true);


    }

    ProgressDialog progressDialog;

    void startHelloWorld() {


        String accountString = account.getText();//取值
        String passwordString = password.getText();

        MultipartBody.Builder body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", accountString)//获取登录名
                .addFormDataPart("passwordHash", MD5.getMD5(passwordString));//获取昵称

        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = Server.requestBuilderWithApi("/login")
                .post(body.build())
                .build();


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("登录中");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                progressDialog.dismiss();
                final String responseString = arg1.body().string();
                ObjectMapper mapper = new ObjectMapper();
                final User user = mapper.readValue(responseString, User.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage("HELLO," + user.getName())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent itnt = new Intent(LoginActivity.this, HelloWorldActivity.class);
                                        startActivity(itnt);
                                    }
                                }).show();

                    }
                });
            }

            @Override
            public void onFailure(final Call arg0, final IOException arg1) {
                progressDialog.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        LoginActivity.this.onFailure(arg0, arg1);
                    }
                });
            }
        });
    }


    void onFailure(Call arg0, Exception arg1) {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("请求失败！")
                .setMessage(arg1.getLocalizedMessage())
                .setPositiveButton("OK", null)
                .show();
    }

    void startRegisterActivity() {
        Intent itnt = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(itnt);
    }


    void startPasswordRecoverActivity() {
        Intent itnt = new Intent(LoginActivity.this, PasswordRecoverActivity.class);
        startActivity(itnt);
    }
}

//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                LoginActivity.this.onFailure(call, e);//注册失败执行
//
//            }
//
//            public void onResponse(Call arg0, Response arg1) throws IOException {
//                final String responseString = arg1.body().string();
//                ObjectMapper mapper = new ObjectMapper();
//                final User user = mapper.readValue(responseString, User.class);
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.dismiss();
//                        new AlertDialog.Builder(LoginActivity.this)
//                                .setMessage("HELLO," + user.getName())
//                                .setMessage(responseString)
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                                    }
//                                });
//                        show();
//                    }
//                });
//            }
//        });
//    }

//    void onFailure(Call call, final IOException e) {//注册失败执行
//        progressDialog.dismiss();
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                new AlertDialog.Builder(LoginActivity.this)
//                        .setTitle("登录失败")
//                        .setMessage("原因：" + e==null?"sb":e.getLocalizedMessage())
//                        .setCancelable(true)
//                        .show();
//            }
//        });
//    }
//    void startHelloWorldActivity() {
//        Intent itnt = new Intent(this, HelloWorldActivity.class);
//        startActivity(itnt);
//        finish();
//    }



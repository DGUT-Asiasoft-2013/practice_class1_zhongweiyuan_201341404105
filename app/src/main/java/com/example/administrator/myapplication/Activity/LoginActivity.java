package com.example.administrator.myapplication.Activity;

import java.io.IOException;
import java.util.List;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entity.Server;
import com.example.administrator.myapplication.entity.User;
import com.example.administrator.myapplication.fragment.inputcells.SimpleTextInputCellFragment;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends Activity {
    SimpleTextInputCellFragment fragAccount, fragPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goRegister();
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goLogin();
            }
        });

        findViewById(R.id.btn_forgot_password).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                goRecoverPassword();
            }
        });

        fragAccount = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_account);
        fragPassword = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_password);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出系统吗？选择困难者请点确定，谢谢！");
            // 添加选择按钮并注册监听
            isExit.setButton("确定", listener);
            isExit.setButton2("取消", listener);
            // 显示对话框
            isExit.show();

        }

        return false;

    }
    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    System.exit(0);
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        fragAccount.setLabelText("登录名");
        fragAccount.setHintText("请输入登录名");
        fragPassword.setLabelText("密码");
        fragPassword.setHintText("请输入密码");
        fragPassword.setIsPassword(true);
    }

    void goRegister() {
        Intent itnt = new Intent(this, RegisterActivity.class);
        startActivity(itnt);
    }

    void goLogin() {
        OkHttpClient client = Server.getSharedClient();

        MultipartBody requestBody = new MultipartBody.Builder()
                .addFormDataPart("account", fragAccount.getText())
                .addFormDataPart("passwordHash", MD5.getMD5(fragPassword.getText()))
                .build();

        Request request = Server.requestBuilderWithApi("login")
                .method("post", null)
                .post(requestBody)
                .build();

        final ProgressDialog dlg = new ProgressDialog(this);
        dlg.setCancelable(false);
        dlg.setCanceledOnTouchOutside(false);
        dlg.setMessage("登录中");
        dlg.show();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                dlg.dismiss();
                final String responseString = arg1.body().string();
                ObjectMapper mapper = new ObjectMapper();
                final User user = mapper.readValue(responseString, User.class);

                runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(LoginActivity.this)
                                .setMessage("欢迎欢迎'" + user.getName() + "'欢迎光临")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent itnt = new Intent(LoginActivity.this, HelloWorldActivity.class);
                                        startActivity(itnt);
                                    }
                                })
                                .show();
                    }
                });
            }

            @Override
            public void onFailure(Call arg0, final IOException arg1) {
                dlg.dismiss();
                runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(LoginActivity.this, "失败", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    void goRecoverPassword() {
        Intent itnt = new Intent(this, PasswordRecoverActivity.class);
        startActivity(itnt);
    }
}




package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.inputcells.PictureInputCellFragment;
import com.example.administrator.myapplication.fragment.inputcells.SimpleTextInputCellFragment;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends Activity {

    SimpleTextInputCellFragment account;
    SimpleTextInputCellFragment password;
    SimpleTextInputCellFragment emailadress;
    SimpleTextInputCellFragment passwordReapeat;
    SimpleTextInputCellFragment name;
    PictureInputCellFragment picture;

    static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_name);
        account = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_account);
        password = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_password);
        passwordReapeat = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_passwordReapeat);
        emailadress = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_emailadress);
        picture = (PictureInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_picture);

        Button button = (Button) findViewById(R.id.btn_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        account.setHintText("请输入登录名");
        account.setLabelText("登录名");
        name.setHintText("请输入昵称");
        name.setLabelText("昵称");
        password.setHintText("请输入密码");
        password.setLabelText("密码");
        password.setIsPassword(true);
        passwordReapeat.setHintText("请再次输入密码");
        passwordReapeat.setLabelText("确认密码");
        passwordReapeat.setIsPassword(true);
        emailadress.setHintText("邮箱地址");
        emailadress.setLabelText("请输入邮箱地址");
        picture.setLabelText("选取头像");
        picture.setHintText("请选择图片");
    }


    ProgressDialog  progressDialog;
    void startLoginActivity() {

        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("注册进行中");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String accountString = account.getText();//取值
        String passwordString = password.getText();
        String passwordReapeatString = passwordReapeat.getText();
        String emailadressString = emailadress.getText();
        String nameString = name.getText();

        if (!passwordString.equals(passwordReapeatString)) { //判断密码不相同，输出密码不一致

            Toast.makeText(this, "密码不一致", Toast.LENGTH_LONG).show();//吐司

            return;
        }

        passwordString = MD5.getMD5(passwordString);

        OkHttpClient client = new OkHttpClient();

        MultipartBody.Builder body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("account", accountString)//获取登录名
                .addFormDataPart("passwordHash", passwordString)//获取昵称
                .addFormDataPart("email", emailadressString)
                .addFormDataPart("name", nameString)
               ;

        body.addFormDataPart("avatar", "avatar", RequestBody.create(MEDIA_TYPE_PNG, picture.getPngData()));

        Request request = new Request.Builder()
                .url("http://172.27.0.37:8080/membercenter/api/register")
                .post(body.build())
                .build();
        client.newCall(request).enqueue(new Callback() {//向后台发送请求进入队列中
            @Override
            public void onFailure(Call call, IOException e) {
                RegisterActivity.this.onFailure(call, e);//注册失败执行

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                RegisterActivity.this.onResponse(call, response);//注册成功执行
            }
        });
    }

    void onFailure(Call call, final IOException e) {//注册失败执行
        progressDialog.dismiss();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("注册失败")
                        .setMessage("原因：" + e.getLocalizedMessage())
                        .setCancelable(true)
                        .show();
            }
        });

    }

    void onResponse(Call call, final Response response) throws IOException {//注册成功执行

        progressDialog.dismiss();
        final String result=response.body().string();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(RegisterActivity.this)
                        .setTitle("注册成功")
                        .setMessage("注册成功：" + result)
                        .setCancelable(true)
                        .show();
            }
            });
        finish();
    }

}

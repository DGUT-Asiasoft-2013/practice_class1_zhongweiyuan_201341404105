package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.PasswordRecoverStep1Fragment;
import com.example.administrator.myapplication.fragment.PasswordRecoverStep2Fragment;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/6.
 */

public class PasswordRecoverActivity extends Activity {
    PasswordRecoverStep1Fragment step1 = new PasswordRecoverStep1Fragment();
    PasswordRecoverStep2Fragment step2 = new PasswordRecoverStep2Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recover);

        setPasswordRecoverStep1Fragment();


    }

    private void setPasswordRecoverStep1Fragment() {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, step1);
        transaction.commit();

        step1.setGoStep2Listener(new PasswordRecoverStep1Fragment.GoStep2Listener() {
            @Override
            public void goStep2() {
                setPasswordRecoverStep2Fragment();
            }
        });

        step2.setGoSubmitListener(new PasswordRecoverStep2Fragment.GoSubmitListener() {
            @Override
            public void backToLogin() {
                goSubmit();
            }
        });


    }

    ProgressDialog progressDialog;
    void goSubmit() {

        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .addFormDataPart("email", step1.getText())
                .addFormDataPart("passwordHash", MD5.getMD5(step2.getText()));
        OkHttpClient client = Server.getSharedClient();
        okhttp3.Request request = Server.requestBuilderWithApi("/passwordrecover")
                .post(bodyBuilder.build())
                .build();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("修改中");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                PasswordRecoverActivity.this.onFailure(call, e.getLocalizedMessage());//修改失败执行

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                PasswordRecoverActivity.this.onResponse(call, response);//修改成功执行
            }
        });
    }

    void onFailure(Call call, final String e) {//修改失败执行
        progressDialog.dismiss();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new android.support.v7.app.AlertDialog.Builder(PasswordRecoverActivity.this)
                        .setTitle("修改失败")
                        .setMessage("原因：" + e)
                        .setPositiveButton("OK",null)
                        .setCancelable(true)
                        .show();
            }
        });
    }

    void onResponse(Call call, final Response response) throws IOException {//修改成功执行

        progressDialog.dismiss();


        final String result = response.body().string();
        if (!result.equals("")) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new android.support.v7.app.AlertDialog.Builder(PasswordRecoverActivity.this)
                            .setTitle("修改成功")
                            .setMessage("修改成功！")
                            .setPositiveButton("OK",null)
                            .setCancelable(true)
                            .show();

                }
            });
        } else {
            onFailure(call, "数据异常！");
        }
    }


    void setPasswordRecoverStep2Fragment() {


        if (!step1.getText().equals("")) {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(
                            R.animator.slide_in_right,
                            R.animator.slide_out_left,
                            R.animator.slide_in_left,
                            R.animator.slide_out_right)
                    .replace(R.id.container, step2)
                    .addToBackStack(null).commit();
        } else {
            Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_SHORT).show();//吐司
        }
    }


}

package com.example.administrator.myapplication.Activity;

import java.io.IOException;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entity.Server;
import com.example.administrator.myapplication.fragment.PasswordRecoverStep1Fragment;
import com.example.administrator.myapplication.fragment.PasswordRecoverStep1Fragment.OnGoNextListener;
import com.example.administrator.myapplication.fragment.PasswordRecoverStep2Fragment;
import com.example.administrator.myapplication.fragment.PasswordRecoverStep2Fragment.OnSubmitClickedListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PasswordRecoverActivity extends Activity {

    PasswordRecoverStep1Fragment step1 = new PasswordRecoverStep1Fragment();
    PasswordRecoverStep2Fragment step2 = new PasswordRecoverStep2Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_password_recover);

        step1.setOnGoNextListener(new OnGoNextListener() {

            @Override
            public void onGoNext() {
                if (!step1.getText().equals("")) {
                    goStep2();
                } else {
                    Toast.makeText(PasswordRecoverActivity.this, "邮箱为空！", Toast.LENGTH_LONG).show();
                }
            }
        });

        step2.setOnSubmitClickedListener(new OnSubmitClickedListener() {

            @Override
            public void onSubmitClicked() {
                goSubmit();
            }
        });

        getFragmentManager().beginTransaction().replace(R.id.container, step1).commit();
    }

    void goStep2() {

        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_in_right,
                        R.animator.slide_out_left,
                        R.animator.slide_in_left,
                        R.animator.slide_out_right)
                .replace(R.id.container, step2)
                .addToBackStack(null)
                .commit();
    }

    void goSubmit() {
        OkHttpClient client = Server.getSharedClient();
        MultipartBody body = new MultipartBody.Builder()
                .addFormDataPart("email", step1.getText())
                .addFormDataPart("passwordHash", MD5.getMD5(step2.getText()))
                .build();
        Request request = Server.requestBuilderWithApi("passwordrecover").post(body).build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call arg0, final Response arg1) throws IOException {
                final String responseString = arg1.body().string();
                runOnUiThread(new Runnable() {
                    public void run() {

                        try {
                            PasswordRecoverActivity.this.onResponse(arg0, responseString);
                        } catch (Exception e) {
                            e.printStackTrace();
//                            PasswordRecoverActivity.this.onFailure(arg0, e);
                        }
                    }
                });
            }

            @Override
            public void onFailure(final Call arg0, final IOException arg1) {
                runOnUiThread(new Runnable() {
                    public void run() {

                        new AlertDialog.Builder(PasswordRecoverActivity.this)
                                .setTitle("失败")
                                .setMessage(arg1.getLocalizedMessage())
                                .setNegativeButton("好", null)
                                .show();
                    }
                });
            }
        });
    }

    void onResponse(Call arg0, String responseBody) {
        new AlertDialog.Builder(this)
                .setTitle("成功")
                .setMessage(responseBody)
                .setPositiveButton("好", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

//    void onFailure(Call arg0, Exception arg1) {
//        new AlertDialog.Builder(this)
//                .setTitle("失败")
//                .setMessage(arg1.getLocalizedMessage())
//                .setNegativeButton("好", null)
//                .show();
//    }

}

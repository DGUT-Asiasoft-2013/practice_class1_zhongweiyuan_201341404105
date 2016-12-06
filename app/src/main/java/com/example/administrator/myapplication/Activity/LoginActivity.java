package com.example.administrator.myapplication.Activity;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.inputcells.SimpleTextInputCellFragment;

public class LoginActivity extends AppCompatActivity {

    SimpleTextInputCellFragment account;
    SimpleTextInputCellFragment password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_account);
        password = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_password);

        Button button1 = (Button)findViewById(R.id.btn_REGISTER);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity();
            }
        });
        Button button2 = (Button)findViewById(R.id.btn_EXIT);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
        Button button3 = (Button)findViewById(R.id.btn_Login);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startHelloWorldActivity();
            }
        });
    }

    @Override
    protected  void onResume() {
        super.onResume();

        account.setHintText("请输入登录名");
        account.setLabelText("登录名");
        password.setHintText("请输入密码");
        password.setLabelText("密码");
        password.setIsPassword(true);

    }

    void startRegisterActivity(){
        Intent itnt = new Intent(this, RegisterActivity.class);
        startActivity(itnt);
        finish();
    }

    void startHelloWorldActivity(){
        Intent itnt = new Intent(this, HelloWorldActivity.class);
        startActivity(itnt);
        finish();
    }
}

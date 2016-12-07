package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.inputcells.PictureInputCellFragment;
import com.example.administrator.myapplication.fragment.inputcells.SimpleTextInputCellFragment;

public class RegisterActivity extends Activity {

    SimpleTextInputCellFragment account;
    SimpleTextInputCellFragment password;
    SimpleTextInputCellFragment emailadress;
    SimpleTextInputCellFragment passwordReapeat;
    PictureInputCellFragment picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        account = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_account);
        password = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_password);
        passwordReapeat = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_passwordReapeat);
        emailadress = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_emailadress);
        picture = (PictureInputCellFragment) getFragmentManager().findFragmentById(R.id.frag_picture);

        Button button = (Button)findViewById(R.id.btn_goback);
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
    void startLoginActivity(){
        Intent itnt = new Intent(this, LoginActivity.class);
        startActivity(itnt);
        finish();
    }
}

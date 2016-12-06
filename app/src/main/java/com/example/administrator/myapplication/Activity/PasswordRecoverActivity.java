package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.PasswordRecoverStep1Fragment;
import com.example.administrator.myapplication.fragment.PasswordRecoverStep2Fragment;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/12/6.
 */

public class PasswordRecoverActivity extends Activity{
    PasswordRecoverStep1Fragment step1 = new PasswordRecoverStep1Fragment();
    PasswordRecoverStep2Fragment step2 = new PasswordRecoverStep2Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recover);

        setPasswordRecoverStep1Fragment();


    }

    private void setPasswordRecoverStep1Fragment() {

        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.container,step1);
        transaction.commit();

        step1.setGoStep2Listener(new PasswordRecoverStep1Fragment.GoStep2Listener() {
            @Override
            public void goStep2() {
                setPasswordRecoverStep2Fragment();
            }
        });

        step2.setGoLoginListener(new PasswordRecoverStep2Fragment.GoLoginListener() {
            @Override
            public void backToLogin() {
                finish();
            }
        });


    }

    void setPasswordRecoverStep2Fragment(){
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.slide_in_right,
                        R.animator.slide_out_left,
                        R.animator.slide_in_left,
                        R.animator.slide_out_right)
                .replace(R.id.container,step2)
                .addToBackStack(null).commit();
    }


}

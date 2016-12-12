package com.example.administrator.myapplication.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapplication.Activity.HelloWorldActivity;
import com.example.administrator.myapplication.Activity.LoginActivity;
import com.example.administrator.myapplication.Activity.MD5;
import com.example.administrator.myapplication.Activity.RegisterActivity;
import com.example.administrator.myapplication.Activity.Server;
import com.example.administrator.myapplication.Activity.User;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.inputcells.SimpleTextInputCellFragment;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/12/6.
 */

public class PasswordRecoverStep2Fragment extends Fragment {

    SimpleTextInputCellFragment username;
    SimpleTextInputCellFragment newpassword;
    SimpleTextInputCellFragment newpasswordReapeat;

    Button submit;


    @Override
    public void onResume() {
        super.onResume();
        username.setLabelText("请输入用户名");
        username.setHintText("用户名");
        newpassword.setLabelText("请输入新密码");
        newpassword.setHintText("新密码");
        newpasswordReapeat.setLabelText("请确认密码");
        newpasswordReapeat.setHintText("确认密码");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_recover_step2, container, false);

        username = (SimpleTextInputCellFragment) getChildFragmentManager().findFragmentById(R.id.input_username);
        newpassword = (SimpleTextInputCellFragment) getChildFragmentManager().findFragmentById(R.id.input_newpassword1);
        newpasswordReapeat = (SimpleTextInputCellFragment) getChildFragmentManager().findFragmentById(R.id.input_newpassword2);

        submit = (Button) view.findViewById(R.id.input_sure);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!newpassword.getText().equals(newpasswordReapeat.getText())) {
                    Toast.makeText(getActivity(), "密码不一致", Toast.LENGTH_SHORT).show();//吐司
                } else {
                    goSubmitListener.backToLogin();
                }

            }
        });
        return view;
    }

    public String getText() {
        if (newpassword.getText().equals(newpasswordReapeat.getText())) {
            return newpassword.getText();
        }
        return newpassword.getText();
    }

    public static interface GoSubmitListener {
        void backToLogin();
    }

    GoSubmitListener goSubmitListener;


    public void setGoSubmitListener(GoSubmitListener goSubmitListener) {
        this.goSubmitListener = goSubmitListener;
    }

}

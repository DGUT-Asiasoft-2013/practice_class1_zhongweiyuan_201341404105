package com.example.administrator.myapplication.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.inputcells.SimpleTextInputCellFragment;

/**
 * Created by Administrator on 2016/12/6.
 */

public class PasswordRecoverStep2Fragment extends Fragment{

    SimpleTextInputCellFragment username;
    SimpleTextInputCellFragment newpassword1;
    SimpleTextInputCellFragment newpassword2;
    private GoLoginListener goLoginListener;

    Button sure;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_password_recover_step2, container, false);

        username = (SimpleTextInputCellFragment) getChildFragmentManager().findFragmentById(R.id.input_username);
        newpassword1 = (SimpleTextInputCellFragment) getChildFragmentManager().findFragmentById(R.id.input_newpassword1);
        newpassword2 = (SimpleTextInputCellFragment) getChildFragmentManager().findFragmentById(R.id.input_newpassword2);

        sure= (Button) view.findViewById(R.id.input_sure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLoginListener. backToLogin();

            }
        });
        return view;
    }

    public  static  interface GoLoginListener {
        void backToLogin();
    }

    public void setGoLoginListener(GoLoginListener goLoginListener)
    {
        this. goLoginListener=goLoginListener;
    }


    @Override
    public void onResume() {
        super.onResume();
        username.setLabelText("请输入用户名");
        username.setHintText("用户名");
        newpassword1.setLabelText("请输入新密码");
        newpassword1.setHintText("新密码");
        newpassword2.setLabelText("请确认密码");
        newpassword2.setHintText("确认密码");
    }

}

package com.example.administrator.myapplication.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.myapplication.Activity.LoginActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.inputcells.SimpleTextInputCellFragment;

/**
 * Created by Administrator on 2016/12/6.
 */

public class PasswordRecoverStep1Fragment extends Fragment {

    SimpleTextInputCellFragment fragEmail;
    View view;
    private GoStep2Listener goStep2Listener;
    Button next;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(view==null) {
            view = inflater.inflate(R.layout.fragment_password_recover_step1, container, false);

            fragEmail = (SimpleTextInputCellFragment) getChildFragmentManager().findFragmentById(R.id.input_email);

            next = (Button) view.findViewById(R.id.btn_next);
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goStep2Listener.goStep2();
                }
            });
        }
        return view;
    }

    public String getText() {
            return fragEmail.getText();
    }


//    void startPasswordRecoverStep2Fragment() {
//        Intent itnt = new Intent(getActivity(), PasswordRecoverStep2Fragment.class);
//        startActivity(itnt);
//    }

    public  static  interface GoStep2Listener {
       void goStep2();
    }

    public void setGoStep2Listener(GoStep2Listener goStep2Listener)
    {
       this. goStep2Listener=goStep2Listener;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (fragEmail!= null) {
            fragEmail.setLabelText("请输入邮箱地址");
            fragEmail.setHintText("email@123.com");
        }
    }

}

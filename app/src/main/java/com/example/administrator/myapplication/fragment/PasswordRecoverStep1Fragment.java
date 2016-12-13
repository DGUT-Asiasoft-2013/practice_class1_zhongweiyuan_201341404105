package com.example.administrator.myapplication.fragment;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.inputcells.SimpleTextInputCellFragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PasswordRecoverStep1Fragment extends Fragment {
    SimpleTextInputCellFragment fragEmail;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(view==null){
            view = inflater.inflate(R.layout.fragment_password_recover_step1, null);

            fragEmail = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_email);

            view.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    goNext();
                }
            });
        }

        return view;
    }

    public String getText(){
        return fragEmail.getText();
    }

    @Override
    public void onResume() {
        super.onResume();

        fragEmail.setLabelText("请输入邮箱地址");
        fragEmail.setHintText("email@123.comַ");
    }

    public static interface OnGoNextListener{
        void onGoNext();
    }

    OnGoNextListener onGoNextListener;

    public void setOnGoNextListener(OnGoNextListener onGoNextListener) {
        this.onGoNextListener = onGoNextListener;
    }

    void goNext(){
        if(onGoNextListener!=null){
            onGoNextListener.onGoNext();
        }
    }
}

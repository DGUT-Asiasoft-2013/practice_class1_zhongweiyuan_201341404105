package com.example.administrator.myapplication.fragment;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.inputcells.SimpleTextInputCellFragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PasswordRecoverStep2Fragment extends Fragment {
    SimpleTextInputCellFragment username, fragPassword, fragPasswordRepeat;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_password_recover_step2, null);
            username = (SimpleTextInputCellFragment) getChildFragmentManager().findFragmentById(R.id.input_verify);
            fragPassword = (SimpleTextInputCellFragment) getChildFragmentManager().findFragmentById(R.id.forget_password);
            fragPasswordRepeat = (SimpleTextInputCellFragment) getChildFragmentManager().findFragmentById(R.id.forget_password_repeat);

            view.findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onSubmitClicked();
                }
            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        username.setLabelText("用户名");
        username.setHintText("请输入用户名ַ");
        fragPassword.setLabelText("新密码");
        fragPassword.setHintText("请输入密码ַ");
        fragPassword.setIsPassword(true);
        fragPasswordRepeat.setLabelText("重复新密码");
        fragPasswordRepeat.setHintText("请在此输入密码ַ");
        fragPasswordRepeat.setIsPassword(true);
    }

    public String getText() {
        return fragPassword.getText();
    }

    public static interface OnSubmitClickedListener {
        void onSubmitClicked();
    }

    OnSubmitClickedListener onSubmitClickedListener;

    public void setOnSubmitClickedListener(OnSubmitClickedListener onSubmitClickedListener) {
        this.onSubmitClickedListener = onSubmitClickedListener;
    }

    void onSubmitClicked() {
        if (fragPassword.getText().equals(fragPasswordRepeat.getText())) {
//            if (onSubmitClickedListener != null) {
                onSubmitClickedListener.onSubmitClicked();
//            }
        } else {
            new AlertDialog.Builder(getActivity())
                    .setMessage("密码不一致")
                    .show();
        }
    }
}


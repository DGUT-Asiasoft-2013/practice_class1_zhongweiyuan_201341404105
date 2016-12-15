package com.example.administrator.myapplication.fragment.LoginFrag;

import java.io.IOException;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entity.Server;
import com.example.administrator.myapplication.entity.User;
import com.example.administrator.myapplication.fragment.widgets.AvatarView;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MeFragment extends Fragment {
    View view;

    Button button;
    TextView textView;
    ProgressBar progress;
    AvatarView avatar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_me, null);
            textView = (TextView) view.findViewById(R.id.text);
            progress = (ProgressBar) view.findViewById(R.id.progress);
            avatar = (AvatarView) view.findViewById(R.id.avatar);
            button = (Button)view.findViewById(R.id.exit);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 创建退出对话框
                    AlertDialog isExit = new AlertDialog.Builder(getActivity()).create();
                    // 设置对话框标题
                    isExit.setTitle("系统提示");
                    // 设置对话框消息
                    isExit.setMessage("确定要退出系统吗？选择困难者请点确定，谢谢！");
                    // 添加选择按钮并注册监听
                    isExit.setButton("确定", listener);
                    isExit.setButton2("取消", listener);
                    // 显示对话框
                    isExit.show();
                }
            });
        }
        return view;
    }
    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    System.exit(0);
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        textView.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);

        OkHttpClient client = Server.getSharedClient();
        Request request = Server.requestBuilderWithApi("me")
                .method("get", null)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call arg0, Response arg1) throws IOException {
                try {
                    final User user = new ObjectMapper().readValue(arg1.body().bytes(), User.class);
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            MeFragment.this.onResponse(arg0, user);
                        }
                    });
                } catch (final Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            MeFragment.this.onFailuer(arg0, e);
                        }
                    });
                }
            }

            @Override
            public void onFailure(final Call arg0, final IOException arg1) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        MeFragment.this.onFailuer(arg0, arg1);
                    }
                });
            }
        });
    }

    protected void onResponse(Call arg0, User user) {
        progress.setVisibility(View.GONE);
        avatar.load(user);
        textView.setVisibility(View.VISIBLE);
        textView.setTextColor(Color.BLACK);
        textView.setText("长得还算凑合啊你，" + user.getName());
    }

    void onFailuer(Call call, Exception ex) {
        progress.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setTextColor(Color.RED);
        textView.setText(ex.getMessage());
    }
}


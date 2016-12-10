package com.example.administrator.myapplication.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.myapplication.Activity.HelloWorldActivity;
import com.example.administrator.myapplication.Activity.LoginActivity;
import com.example.administrator.myapplication.Activity.RegisterActivity;
import com.example.administrator.myapplication.Activity.Server;
import com.example.administrator.myapplication.Activity.User;
import com.example.administrator.myapplication.R;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyProfileFragment extends Fragment {

    View view;

    TextView textView;
    ProgressBar progressBar;
//    AvatarView avatarView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_my_profile, null);
            textView = (TextView) view.findViewById(R.id.textView);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
//            avatarView = (TextView)view.findViewById(R.id.avatarView);
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        textView.setVisibility(View.GONE);
        progressBar.setVisibility(ProgressBar.VISIBLE);

        OkHttpClient client = Server.getSharedClient();
        final Request request = Server.requestBuilderWithApi("me")
                .method("get", null)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onResponse(final Call arg0, final Response arg1) throws IOException {


                String result = arg1.body().string();

                ObjectMapper mapper = new ObjectMapper();
                final User user = mapper.readValue(result, User.class);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        MyProfileFragment.this.onResponse(arg0, user);
                    }
                });

            }


            @Override
            public void onFailure(final Call arg0, final IOException ex) {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyProfileFragment.this.onFailure(arg0, ex);
                    }
                });

            }

        });
    }

    protected void onResponse(Call arg0, User user) {
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setTextColor(Color.RED);
        textView.setText("HELLO," + user.getName());
    }

    void onFailure(Call arg0, IOException ex) {
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.VISIBLE);
        textView.setTextColor(Color.RED);
        textView.setText(ex.getMessage());
    }


}

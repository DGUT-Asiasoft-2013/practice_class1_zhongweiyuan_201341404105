package com.example.administrator.myapplication.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.R;


public class VersionFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_version,null);
        TextView textVersion = (TextView)view.findViewById(R.id.text);

        PackageManager pkgm = this.getActivity().getPackageManager();


            try {
                PackageInfo appinf = pkgm.getPackageInfo(getActivity().getPackageName(),0);
                textVersion.setText("当前版本"+" "+appinf.versionName);



            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                textVersion.setText("ERROR");

            }

        return view;
    }


}

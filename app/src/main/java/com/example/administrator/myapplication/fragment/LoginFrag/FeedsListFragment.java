package com.example.administrator.myapplication.fragment.LoginFrag;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.inputcells.SimpleTextInputCellFragment;

/**
 * Created by Administrator on 2016/12/7.
 */

public class FeedsListFragment extends Fragment {
    View view;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_feed_list, null,false);

//            listView.setAdapter(ListAdapter);

        }

        return view;
    }

//    BaseAdapter listAdapter = new BaseAdapter() {
//        @Override
//        public int getCount() {
//            return 20;
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            View view1 = null;
//
//            if (view1 == null) {
//                LayoutInflater inflater = LayoutInflater.from(parent, getContext());
//                view1 = inflater.inflate(android.R.layout.simple_list_item_1, null);
//            } else {
//
//            }
//
//            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
//            text1.setText("THIS IS ROW" + i);
//
//            return null;
//        }
//    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        if (view == null){
//            view = inflater.inflate(R.layout.fragment_feed_list,container,false);
//
//            fragEmail = (SimpleTextInputCellFragment) getFragmentManager().findFragmentById(R.id.input_email);
//
//            next = (Button) view.findViewById(R.id.btn_next);
//            next.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                }
//            });
//        }
//    }
}

package com.example.administrator.myapplication.fragment.LoginFrag;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapplication.Activity.ContentFeedActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.inputcells.SimpleTextInputCellFragment;

import java.util.Random;

/**
 * Created by Administrator on 2016/12/7.
 */

public class FeedsListFragment extends Fragment {
    View view;
    ListView listView;

    String data[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_feed_list, null, false);
            listView = (ListView) view.findViewById(R.id.frag_list);
            listView.setAdapter(listAdapter);

            Random random = new Random();
            data = new String[10 + new Random().nextInt(100) % 20];

            for (int i = 0; i < data.length; i++) {
                data[i] = "随机数" + new Random().nextInt(20);
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Intent intent = new Intent(getActivity(), ContentFeedActivity.class);
                    intent.putExtra("text", data[i]);
                    startActivity(intent);
                }
            });

        }

        return view;
    }

    BaseAdapter listAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return data == null ? 0 : data.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View container, ViewGroup viewGroup) {
            View view = null;

            if (container == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                view = inflater.inflate(android.R.layout.simple_list_item_1, null);
            } else {
                view = container;

            }

            TextView text1 = (TextView) view.findViewById(android.R.id.text1);
            text1.setText(data[i]);

            return view;
        }
    };

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

package com.example.administrator.myapplication.fragment.LoginFrag;

import com.example.administrator.myapplication.Activity.ContentFeedActivity;
import com.example.administrator.myapplication.Activity.NewContentActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.entity.Article;
import com.example.administrator.myapplication.entity.Page;
import com.example.administrator.myapplication.entity.Server;
import com.example.administrator.myapplication.fragment.widgets.AvatarView;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class NoteListFragment extends Fragment {
    View view;
    ListView listView;
    View btnLoadMore;
    TextView textLoadMore;
    Activity activity;
    List<Article> data;
    int page = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            activity = getActivity();
            view = inflater.inflate(R.layout.fragment_note_list, null);
            btnLoadMore = inflater.inflate(R.layout.widget_load_more_button, null);
            textLoadMore = (TextView) btnLoadMore.findViewById(R.id.load_more_text);

            listView = (ListView) view.findViewById(R.id.list);
            listView.addFooterView(btnLoadMore);
            listView.setAdapter(listAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    onItemClicked(position);
                }
            });

            btnLoadMore.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    loadmore();
                }
            });
        }

        return view;
    }

    BaseAdapter listAdapter = new BaseAdapter() {

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                view = inflater.inflate(R.layout.widget_feed_item, null);
            } else {
                view = convertView;
            }

            TextView textContent = (TextView) view.findViewById(R.id.text);
            TextView textTitle = (TextView) view.findViewById(R.id.title);
            TextView textAuthorName = (TextView) view.findViewById(R.id.username);
            TextView textDate = (TextView) view.findViewById(R.id.date);
            AvatarView avatar = (AvatarView) view.findViewById(R.id.widget_avatar);

            Article article = data.get(position);

            textContent.setText(article.getText());
            textTitle.setText(article.getTitle());
            textAuthorName.setText(article.getAuthor().getName());
            avatar.load(article.getAuthor());

            String dateStr = DateFormat.format("yyyy-MM-dd hh:mm", article.getCreateDate()).toString();
            textDate.setText(dateStr);

            return view;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }
    };

    void onItemClicked(int position) {
        Article article = data.get(position);

        Intent itnt = new Intent(activity, ContentFeedActivity.class);
        itnt.putExtra("data", article);

        startActivity(itnt);
    }

    @Override
    public void onResume() {
        super.onResume();
        reload();
    }

    void reload() {
        Request request = Server.requestBuilderWithApi("feeds")
                .get()
                .build();

        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                try {
                    final Page<Article> data = new ObjectMapper()
                            .readValue(arg1.body().string(),
                                    new TypeReference<Page<Article>>() {
                                    });

                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            NoteListFragment.this.page = data.getNumber();
                            NoteListFragment.this.data = data.getContent();
                            listAdapter.notifyDataSetInvalidated();
                        }
                    });
                } catch (final Exception e) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            new AlertDialog.Builder(activity)
                                    .setMessage(e.getMessage())
                                    .show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call arg0, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        new AlertDialog.Builder(activity)
                                .setMessage(e.getMessage())
                                .show();
                    }
                });
            }
        });
    }

    void loadmore() {
        btnLoadMore.setEnabled(false);
        textLoadMore.setText("加载更多");

        Request request = Server.requestBuilderWithApi("feeds/" + (page + 1)).get().build();
        Server.getSharedClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call arg0, Response arg1) throws IOException {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        btnLoadMore.setEnabled(true);
                        textLoadMore.setText("成功");
                    }
                });

                try {
                    Page<Article> feeds = new ObjectMapper().readValue(arg1.body().string(), new TypeReference<Page<Article>>() {
                    });
                    if (feeds.getNumber() > page) {
                        if (data == null) {
                            data = feeds.getContent();
                        } else {
                            data.addAll(feeds.getContent());
                        }
                        page = feeds.getNumber();

                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                listAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call arg0, IOException arg1) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        btnLoadMore.setEnabled(true);
                        textLoadMore.setText("失败");
                    }
                });
            }
        });
    }
}


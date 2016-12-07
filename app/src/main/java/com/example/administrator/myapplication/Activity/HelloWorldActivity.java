package com.example.administrator.myapplication.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.LoginFrag.FeedsListFragment;
import com.example.administrator.myapplication.fragment.LoginFrag.MeFragment;
import com.example.administrator.myapplication.fragment.LoginFrag.NoteListFragment;
import com.example.administrator.myapplication.fragment.LoginFrag.SearchFragment;
import com.example.administrator.myapplication.fragment.inputcells.MainTabbarFragment;

public class HelloWorldActivity extends Activity {
    FeedsListFragment contentFeedList = new FeedsListFragment();
    NoteListFragment contentNoteList = new NoteListFragment();
    SearchFragment contentSearch = new SearchFragment();
    MeFragment contentMe = new MeFragment();

    MainTabbarFragment tabbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);

        tabbar = (MainTabbarFragment) getFragmentManager().findFragmentById(R.id.frag_tabbar);
        tabbar.setOnTabSelectedListener(new MainTabbarFragment.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int index) {
                changeContentFragment(index);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        tabbar.setSelectedItem(0);
    }

    void changeContentFragment(int index) {
        Fragment newFrag = null;
        switch (index) {
            case 0:
                newFrag = contentFeedList;
                break;
            case 1:
                newFrag = contentNoteList;
                break;
            case 2:
                newFrag = contentSearch;
                break;
            case 3:
                newFrag = contentMe;
                break;
            default:
                break;
        }
        if(newFrag==null) return;

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content, newFrag)
                .commit();
    }

//    public static interface OnTabSelected {
//
//    }
//
//    void setFeedsListFragment() {
//        getFragmentManager()
//                .beginTransaction()
//                .setCustomAnimations(
//                        R.animator.slide_in_right,
//                        R.animator.slide_out_left,
//                        R.animator.slide_in_left,
//                        R.animator.slide_out_right)
//                .replace(R.id.frag_feed, contentFeedList)
//                .addToBackStack(null).commit();
//    }
//
//    void setNoteListFragment() {
//        getFragmentManager()
//                .beginTransaction()
//                .setCustomAnimations(
//                        R.animator.slide_in_right,
//                        R.animator.slide_out_left,
//                        R.animator.slide_in_left,
//                        R.animator.slide_out_right)
//                .replace(R.id.frag_note, contentNoteList)
//                .addToBackStack(null).commit();
//    }
//
//    void setSearchFragment() {
//        getFragmentManager()
//                .beginTransaction()
//                .setCustomAnimations(
//                        R.animator.slide_in_right,
//                        R.animator.slide_out_left,
//                        R.animator.slide_in_left,
//                        R.animator.slide_out_right)
//                .replace(R.id.frag_search, contentSearch)
//                .addToBackStack(null).commit();
//    }
//
//    void setMeFragment() {
//        getFragmentManager()
//                .beginTransaction()
//                .setCustomAnimations(
//                        R.animator.slide_in_right,
//                        R.animator.slide_out_left,
//                        R.animator.slide_in_left,
//                        R.animator.slide_out_right)
//                .replace(R.id.frag_me, contentMe)
//                .addToBackStack(null).commit();
//    }


}

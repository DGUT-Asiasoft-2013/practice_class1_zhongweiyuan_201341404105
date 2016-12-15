package com.example.administrator.myapplication.Activity;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.LoginFrag.FeedsListFragment;
import com.example.administrator.myapplication.fragment.LoginFrag.MeFragment;
import com.example.administrator.myapplication.fragment.LoginFrag.NoteListFragment;
import com.example.administrator.myapplication.fragment.LoginFrag.SearchFragment;
import com.example.administrator.myapplication.fragment.widgets.MainTabbarFragment;
import com.example.administrator.myapplication.fragment.widgets.MainTabbarFragment.OnNewClickedListener;
import com.example.administrator.myapplication.fragment.widgets.MainTabbarFragment.OnTabSelectedListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class HelloWorldActivity extends Activity {

    FeedsListFragment contentFeedList = new FeedsListFragment();
    NoteListFragment contentNoteList = new NoteListFragment();
    SearchFragment contentSearchPage = new SearchFragment();
    MeFragment contentMyProfile = new MeFragment();

    MainTabbarFragment tabbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_hello_world);

        tabbar = (MainTabbarFragment) getFragmentManager().findFragmentById(R.id.frag_tabbar);
        tabbar.setOnTabSelectedListener(new OnTabSelectedListener() {

            @Override
            public void onTabSelected(int index) {
                changeContentFragment(index);
            }
        });

        tabbar.setOnNewClickedListener(new OnNewClickedListener() {

            @Override
            public void onNewClicked() {
                bringUpEditor();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(tabbar.getSelectedIndex()<0){
            tabbar.setSelectedItem(0);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            // 创建退出对话框
            AlertDialog isExit = new AlertDialog.Builder(this).create();
            // 设置对话框标题
            isExit.setTitle("系统提示");
            // 设置对话框消息
            isExit.setMessage("确定要退出登录吗？选择困难者请点确定，谢谢！");
            // 添加选择按钮并注册监听
            isExit.setButton("确定", listener);
            isExit.setButton2("取消", listener);
            // 显示对话框
            isExit.show();

        }

        return false;

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

    void changeContentFragment(int index){
        Fragment newFrag = null;

        switch (index) {
            case 0: newFrag = contentFeedList; break;
            case 1: newFrag = contentNoteList; break;
            case 2: newFrag = contentSearchPage; break;
            case 3: newFrag = contentMyProfile; break;

            default:break;
        }

        if(newFrag==null) return;

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content, newFrag)
                .commit();
    }

    void bringUpEditor(){
        Intent itnt = new Intent(this, NewContentActivity.class);
        startActivity(itnt);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.none);
    }
}

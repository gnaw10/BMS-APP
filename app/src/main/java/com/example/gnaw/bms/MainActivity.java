package com.example.gnaw.bms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private View view1, view2, view3;
    private ViewPager viewPager;  //对应的viewPager
    private TabLayout tabLayout;
    private List<View> viewList = new ArrayList<View>();//view数组viewList = new ArrayList<View>();
    private TextView textView;
    private static final String TAG = "MainActivity";
    public static Handler mMainHandler, mChildHandler;
    private Button bookButton, button;
    private ListView listView;
    private List<Book> books = new ArrayList<Book>();
    private Book book = new Book();
    public static Intent intent = new Intent();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater mInflater;
        mInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        final View view1 = mInflater.inflate(R.layout.layout1, null);
        bookButton = (Button) view1.findViewById(R.id.bookButton);
        listView = (ListView) view1.findViewById(R.id.listView);


        final List<Book> books = new ArrayList<Book>();
        books.add(book);
        final BookAdapter bookAdapter = new BookAdapter(this, books);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        LayoutInflater inflater = getLayoutInflater();
        //view1 = inflater.inflate(R.layout.layout1, null);
        view2 = inflater.inflate(R.layout.layout2, null);
        view3 = inflater.inflate(R.layout.layout3, null);
        button = (Button) view3.findViewById(R.id.button);
        textView = (TextView) view3.findViewById(R.id.textView5);
        // 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewPager.setCurrentItem(0);

        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return viewList.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // TODO Auto-generated method stub
                container.removeView(viewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // TODO Auto-generated method stub
                container.addView(viewList.get(position));
                return viewList.get(position);
            }
        };

        books.add(book);
        bookAdapter.notifyDataSetChanged();

        viewPager.setAdapter(pagerAdapter);

        pagerAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Book");
        tabLayout.getTabAt(1).setText("Log");
        tabLayout.getTabAt(2).setText("User");

        //pagerAdapter.notifyDataSetChanged();
        //tabLayout.setupWithViewPager(viewPager);

        mMainHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.i(TAG, "s" + (String) msg.obj);
                // 接收子线程的消息
                System.out.println("0.0.0.0" + (String) msg.obj);
                //info.setText((String) msg.obj);
                Log.i(TAG, (String) msg.obj);
                Request request = JSON.parseObject((String) msg.obj, Request.class);
                Log.i(TAG, request.toString());

                System.out.println(JSON.parseObject((String) msg.obj, Request.class));
                //
                request = JSON.parseObject((String) msg.obj, Request.class);

                books.clear();
                bookAdapter.notifyDataSetChanged();
                List<Book> tempBooks = request.getResponse();
                books.addAll(tempBooks);
                bookAdapter.notifyDataSetChanged();
                listView.setAdapter(bookAdapter);
                Log.e(TAG, "asdadrfaweoiru");
            }
        };


        new ChildThread().start();
        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChildHandler != null) {
                    //发送消息给子线程
                    Message childMsg = mChildHandler.obtainMessage();
                    childMsg.obj = mMainHandler.getLooper().getThread().getName();
                    mChildHandler.sendMessage(childMsg);
                    Log.i(TAG, (String) childMsg.obj);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //button.setVisibility(View.GONE);

                intent = new Intent(MainActivity.this, UserLogin.class);
                startActivityForResult(intent, 1);


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String loginResult="00";
        switch (resultCode) {
            case RESULT_OK:
                try {
                    loginResult = data.getStringExtra("loginResult");
                    textView.setText(loginResult);
                    Toast.makeText(this, loginResult, Toast.LENGTH_SHORT).show();
                    button.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    textView.setText("登录失败"+e.toString());
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Stop looping the child thread's message queue");
        mChildHandler.getLooper().quit();
    }

}




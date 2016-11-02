package com.example.gnaw.bms;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MainActivity extends Activity {

    private View view1, view2, view3;
    private ViewPager viewPager;  //对应的viewPager
    private TabLayout tabLayout;
    private List<View> viewList = new ArrayList<View>();//view数组viewList = new ArrayList<View>();
    private TextView textView;
    private static final String TAG = "MainActivity";
    public static Handler mMainHandler, mChildHandler;
    private Button bookButton, button,logListButton;
    private ListView listView,logListView;
    private Book book = new Book();
    public static Intent intent = new Intent();
    public  BookAdapter bookAdapter;
    public LogListAdapter logListAdapter;
    private  List<Book> books = new ArrayList<Book>();
    private  List<LogList> logLists = new ArrayList<LogList>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookAdapter= new BookAdapter(this, books);
        logListAdapter = new LogListAdapter(this,logLists);
        LayoutInflater mInflater;
        mInflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        final View view1 = mInflater.inflate(R.layout.layout1, null);
        bookButton = (Button) view1.findViewById(R.id.bookButton);
        listView = (ListView) view1.findViewById(R.id.listView);

        final View view2 = mInflater.inflate(R.layout.layout2, null);
        logListButton = (Button) view2.findViewById(R.id.logListButton);
        logListView = (ListView) view2.findViewById(R.id.logListView);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        LayoutInflater inflater = getLayoutInflater();
        //view1 = inflater.inflate(R.layout.layout1, null);
        //view2 = inflater.inflate(R.layout.layout2, null);
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

        bookAdapter.notifyDataSetChanged();
        viewPager.setAdapter(pagerAdapter);


        pagerAdapter.notifyDataSetChanged();
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Book");
        tabLayout.getTabAt(1).setText("Log");
        tabLayout.getTabAt(2).setText("User");

        //pagerAdapter.notifyDataSetChanged();
        //tabLayout.setupWithViewPager(viewPager);


        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    new BookThread(bookHandler).start();
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

        logListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LogListThread(logListHandler).start();
            }
        });

    }

    Handler logListHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 接收消息里面的包中的String数据
            String string = msg.getData().getString("key");
            // 将线程中的得到的数据显示
            try
            {
                LogListRequest logRequest = new LogListRequest();
                Log.i("qwerqwer",LogListThread.result);
                logRequest = JSON.parseObject(LogListThread.result, LogListRequest.class);
                logLists.clear();
                List<LogList> tempLogs = logRequest.getResponse();
                logLists.addAll(tempLogs);
                logListAdapter.notifyDataSetChanged();
                logListView.setAdapter(logListAdapter);
                Log.e(TAG, "asdadrfaweoiru");
            }
            catch (Exception e)
            {
                Log.i("12",e.toString());
            }
        }
    };

    Handler bookHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 接收消息里面的包中的String数据
            String string = msg.getData().getString("key");
            // 将线程中的得到的数据显示
            try
            {
                Request request = new Request();
                Log.i("qwerqwer",BookThread.result);
                request = JSON.parseObject(BookThread.result, Request.class);
                books.clear();
                bookAdapter.notifyDataSetChanged();
                List<Book> tempBooks = request.getResponse();
                books.addAll(tempBooks);
                bookAdapter.notifyDataSetChanged();
                listView.setAdapter(bookAdapter);
                Log.e(TAG, "asdadrfaweoiru");
            }
            catch (Exception e)
            {
                Log.i("12",e.toString());
            }
        }
    };

    public Handler handler = new  Handler();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String loginResult="1",borrowResult="2";
        Log.i(TAG,"/"+resultCode+"/"+requestCode);
        if(requestCode == 1)
        switch (resultCode) {
            case RESULT_OK:
                try {
                    loginResult = data.getStringExtra("loginResult");
                    textView.setText(loginResult);
                    System. out.println("--------"+loginResult);
                    if(JSON.parseObject(loginResult).getString("code") .equals("0000"))
                        Toast.makeText(this, loginResult, Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this, "登录失败"+loginResult, Toast.LENGTH_SHORT).show();
                    //button.setVisibility(View.INVISIBLE);
                } catch (Exception e) {
                    textView.setText("登录失败"+e.toString());
                }
                break;
            default:
                break;
        }
        else if(requestCode == 2)
            switch (resultCode) {
                case RESULT_OK:
                    try {
                        borrowResult = data.getStringExtra("borrowResult");
                        //textView.setText(borrowResult);
                        Log.i(TAG,"123"+borrowResult);
                        String code = JSON.parseObject(borrowResult).getString("code");
                        Log.i(TAG,"456"+code);
                        if(code.equals("0000"))
                            Toast.makeText(this, borrowResult+"借阅成功", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(this, borrowResult+"借阅失败", Toast.LENGTH_SHORT).show();

                        //button.setVisibility(View.INVISIBLE);
                    } catch (Exception e) {
                        //textView.setText("登录失败"+e.toString());
                        Toast.makeText(this, borrowResult+"借阅失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public class BookAdapter extends BaseAdapter {
        private List<Book> books;
        private LayoutInflater mInflater;
        BookAdapter(Context context, List<Book> books)
        {
            this.books = books;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return books.size();
        }

        @Override
        public Object getItem(int position) {
            return books.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final int num = position;
            Book book = books.get(position);
            View v = convertView;
            if(convertView == null)
            {
                v = mInflater.inflate(R.layout.book , parent , false);
            }
            ((TextView)v.findViewById(R.id.textTitle)).setText("--"+book.getId()+"--  "+book.getName());

            if(book.getUser_id() == 0)
            {
                ((TextView)v.findViewById(R.id.textUser)).setText("可借");
                ((TextView)v.findViewById(R.id.borrowButton)).setEnabled(true);
                ((TextView)v.findViewById(R.id.borrowButton)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent borrowIntent = new Intent(MainActivity.this,BorrowBook.class);
                        Bundle bookBundle;//该类用作携带数据
                        bookBundle = new Bundle();
                        bookBundle.putInt("id", (position+1));
                        borrowIntent.putExtras(bookBundle);
                        startActivityForResult(borrowIntent,2);
                    }
                });
            }
            else
            {
                ((TextView)v.findViewById(R.id.textUser)).setText("不可借,在"+book.getBorrowUser()+"处");
                ((TextView)v.findViewById(R.id.borrowButton)).setEnabled(false);
            }
            ((TextView)v.findViewById(R.id.textState)).setText(book.getCoverUrl());
            return v;
        }
    }

    public class LogListAdapter extends BaseAdapter {
        private List<LogList> logLists;
        private LayoutInflater mInflater;
        LogListAdapter(Context context, List<LogList> logLists)
        {
            this.logLists = logLists;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return logLists.size();
        }

        @Override
        public Object getItem(int position) {
            return books.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final int num = position;
            final LogList logList = logLists.get(position);
            View v = convertView;
            if(convertView == null)
            {
                v = mInflater.inflate(R.layout.loglist , parent , false);
            }
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent singleLogIntent = new Intent(MainActivity.this,SingleLog.class);
                    Bundle singleLogBundle = new Bundle();
                    singleLogBundle.putString("logListTitle",logLists.get(position+1).getTitle());
                    singleLogBundle.putString("logListBody",logLists.get(position+1).getBody());
                    singleLogIntent.putExtras(singleLogBundle);
                    startActivity(singleLogIntent);
                }
            });
            ((TextView)v.findViewById(R.id.logListTitle)).setText("--"+logList.getId()+"--  "+logList.getTitle());
            ((TextView)v.findViewById(R.id.logListBody)).setText(logList.getBody());
            ((TextView)v.findViewById(R.id.logListUser)).setText("作者:"+logList.getUser_id());
            ((TextView)v.findViewById(R.id.logListBook)).setText("相关书籍:"+logList.getBook_id());
            ((TextView)v.findViewById(R.id.logListBody)).setText(logList.getBody());

            return v;
        }
    }


}




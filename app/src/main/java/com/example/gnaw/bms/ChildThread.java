package com.example.gnaw.bms;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by gnaw on 2016/10/29.
 */
public class ChildThread extends Thread {

    private static final String CHILD_TAG = "ChildThread";
    private String result;
    public void run() {
        this.setName("ChildThread");

        //初始化消息循环队列，需要在Handler创建之前
        Looper.prepare();
        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://book.gnaw10.cn/book/list");
            String para = new String("");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
        /*
         * 只有设置contentType为application/x-www-form-urlencoded，
         * servlet就可以直接使用request.getParameter("username");直接得到所需要信息
         */
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", String.valueOf(para.getBytes().length));
            //默认为false
            conn.setDoOutput(true);
            //4.向服务器写入数据
            conn.getOutputStream().write(para.getBytes());
            System.out.println(para);
            //5.得到服务器相应
            if (conn.getResponseCode() == 200) {
                System.out.println("服务器已经收到表单数据！");
                InputStream is = conn.getInputStream();
                Scanner scanner = new Scanner(is, "UTF-8");
                result = scanner.useDelimiter("\\A").next();
                System.out.println(result);
                scanner.close();
            } else {
                System.out.println("请求失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            if (conn != null) {
                //关闭连接 即设置 http.keepAlive = false;
                conn.disconnect();
            }
        }


        MainActivity.mChildHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Log.i(CHILD_TAG, "Got an incoming message from the main thread - " + (String)msg.obj);

                try {
                    //在子线程中可以做一些耗时的工作
                    sleep(100);

                    Message toMain = MainActivity.mMainHandler.obtainMessage();
                    toMain.obj = result;

                    MainActivity.mMainHandler.sendMessage(toMain);

                    Log.i(CHILD_TAG, "Send a message to the main thread - " + (String)toMain.obj);

                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        };

        Log.i(CHILD_TAG, "Child handler is bound to - "+ MainActivity.mChildHandler.getLooper().getThread().getName());

        //启动子线程消息循环队列
        Looper.loop();
    }
}
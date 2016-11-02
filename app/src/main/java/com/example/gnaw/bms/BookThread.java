package com.example.gnaw.bms;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by gnaw on 2016/11/2.
 */

public class BookThread extends Thread {
    private Handler handler = null;
    public static   String para,result;
    private static final String CHILD_TAG = "ChildThread";
    // 初始化线程
    protected BookThread(Handler handler) {
        this.handler = handler;
    }

    @Override
    // 线程的start()执行时自动调用此函数
    public void run() {
        //super.run();
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

        // …………
        // 执行子线程里实现的功能
        // …………

        // 线程中产生的数据，可以是任何类型的值，此处用String类型作为例子
        String string = para;
        // 定义消息，之后发送出去
        Message msg = handler.obtainMessage();
        // 定义数据包，数据包里可以put不同类型的数据
        Bundle bundle = new Bundle();
        // 将String数据放入包中
        bundle.putString("key", string);
        // 将包放入消息中
        msg.setData(bundle);
        // 将消息发送出去
        handler.sendMessage(msg);
        Log.i("123","456");
    }
}

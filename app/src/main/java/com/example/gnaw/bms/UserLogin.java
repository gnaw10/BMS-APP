package com.example.gnaw.bms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by gnaw on 2016/10/31.
 */

public class UserLogin extends Activity {


    private Button signButton = null;
    private User user = new User();
    private static String result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlogin);
        signButton=(Button)findViewById(R.id.signButton);


        // 创建一个新线程，用外部处理程序初始化，便于将线程的数据送回
        final MyThread thread = new MyThread(handler);
        // 开始新线程

        //thread.start();
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setPassword(((EditText)findViewById(R.id.editText2)).getText().toString());
                user.setUsername(((EditText)findViewById(R.id.editText1)).getText().toString());
                new MyThread(handler).start();
            }
        });
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 接收消息里面的包中的String数据
            String string = msg.getData().getString("key");
            // 将线程中的得到的数据显示
            try
            {
                Intent retIt=getIntent();
                retIt.putExtra("loginResult",result);
                setResult(Activity.RESULT_OK,retIt);
                finish();
            }
            catch (Exception e)
            {
                ;
            }
        }
    };

    public class MyThread extends Thread {
        private Handler handler = null;
        public   String para;
        private static final String CHILD_TAG = "ChildThread";
        // 初始化线程
        protected MyThread(Handler handler) {
            this.handler = handler;
        }

        @Override
        // 线程的start()执行时自动调用此函数
        public void run() {
            //super.run();
            Looper.prepare();
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://book.gnaw10.cn/user/signin");
                Log.i(CHILD_TAG,user.toString());
                String passage= JSON.toJSONString(user);
                Log.i(CHILD_TAG,passage);

                String para = passage;
                Log.i(CHILD_TAG,"asdasd"+para);
                //1.得到HttpURLConnection实例化对象
                conn = (HttpURLConnection) url.openConnection();
                //2.设置请求方式
                conn.setRequestMethod("POST");
                System.out.println("11111");
                //3.设置post提交内容的类型和长度

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
                    Log.i("-----",passage);
                    System.out.println("服务器已经收到表单数据！");
                    InputStream is = conn.getInputStream();
                    Scanner scanner = new Scanner(is, "UTF-8");
                    result = scanner.useDelimiter("\\A").next();

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
}
package com.example.ch7_webconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button get, post, put, getAll;
    TextView tv1;
    EditText et_id, et_name, et_address;
    ListView listView;
    static String res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_id = findViewById(R.id.editTextTextPersonName);
        et_name = findViewById(R.id.editTextTextPersonName2);
        et_address = findViewById(R.id.editTextTextPersonName3);
        post = findViewById(R.id.button2);
        get = findViewById(R.id.button);
        put = findViewById(R.id.button3);
        getAll = findViewById(R.id.button4);
        listView = findViewById(R.id.listview);
        tv1 = findViewById(R.id.textView);


        get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //0.发起http请求为额外动作，需单独开启子线程进行
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //1.创建URL对象
                            URL url = new URL("http://10.0.2.2:8080/user/get");//////////////////////
                            //2. 调用openConnection来获取HttpURLConnection对象
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            //3.设置http请求方法
                            httpURLConnection.setRequestMethod("GET");/////////////////
                            //4. 设置连接超时，读取超时
                            httpURLConnection.setConnectTimeout(8000);
                            httpURLConnection.setReadTimeout(8000);
                            //5. 调用getInputStream()方法获得服务器返回的数据输入流
                            InputStream in = httpURLConnection.getInputStream();
                            //测试连接是否成功----  getResponseCode()==200
                            Log.d("test--------", " 响应结果： "+httpURLConnection.getResponseCode());
                            //6.读取数据
                            int read = -1;
                            StringBuffer stringBuffer = new StringBuffer();
                            while ((read = in.read()) != -1) {
                                stringBuffer.append((char) read);
                                Log.i("@@@@@ read:", stringBuffer.toString());
                            }
                            in.close();
                            res = stringBuffer.toString();
                            Log.i("@@@@@ read:", res);
                            //6.2
                            httpURLConnection.disconnect();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            Log.e("UrlException:", e.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("IOException:", e.toString());
                        }
                    }
                });
                thread.start();
                try {
                    //7.两线程间数据交互，主线程需要获取该线程数据时使用。防止主线程早于该线程结束。
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tv1.setText(res);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://10.0.2.2:8080/user/post/" + et_id.getText().toString() + "/" + et_name.getText().toString());//-----------------
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestMethod("POST");//-------------------------------------------------------
                            httpURLConnection.setReadTimeout(8000);
                            httpURLConnection.setReadTimeout(8000);
                            InputStream in = httpURLConnection.getInputStream();
                            Log.d("test--------", String.valueOf(httpURLConnection.getResponseCode() == 200));
                            int read = -1;
                            StringBuffer stringBuffer = new StringBuffer();
                            while ((read = in.read()) != -1) {
                                stringBuffer.append((char) read);
                                Log.i("@@@@@ read:", stringBuffer.toString());
                            }
                            in.close();
                            res = stringBuffer.toString();
                            Log.i("@@@@@ read:", res);
                            httpURLConnection.disconnect();
                        } catch (Exception e) {
                            Log.e("Error: ", " " + e.toString());
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tv1.setText(res);
            }
        });

        put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String id = et_id.getText().toString();
                            String name = et_name.getText().toString();
                            String address = et_address.getText().toString();
                            URL url = new URL("http://10.0.2.2:8080/user/put/" + id + "/" + name + "/" + address);//-------------------
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestMethod("PUT");//-------------------------------
                            httpURLConnection.setReadTimeout(8000);
                            httpURLConnection.setReadTimeout(8000);
                            InputStream in = httpURLConnection.getInputStream();
                            Log.d("test--------", String.valueOf(httpURLConnection.getResponseCode() == 200));
                            int read = -1;
                            StringBuffer stringBuffer = new StringBuffer();
                            while ((read = in.read()) != -1) {
                                stringBuffer.append((char) read);
                                Log.i("@@@@@ read:", stringBuffer.toString());
                            }
                            in.close();
                            res = stringBuffer.toString();
                            Log.i("@@@@@ read:", res);
                            httpURLConnection.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Error: ", e.toString());
                        }
                    }
                });
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tv1.setText(res);
            }
        });

        getAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://10.0.2.2:8080/user/get/list");///--------------------------------------
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestMethod("GET");///-----------------------------
                            httpURLConnection.setReadTimeout(8000);
                            httpURLConnection.setReadTimeout(8000);
                            InputStream in = httpURLConnection.getInputStream();
                            Log.d("test--------", String.valueOf(httpURLConnection.getResponseCode() == 200));
                            int read = -1;
                            StringBuffer stringBuffer = new StringBuffer();
                            while ((read = in.read()) != -1) {
                                stringBuffer.append((char) read);
                                Log.i("@@@@@ read:", stringBuffer.toString());
                            }
                            in.close();
                            res = stringBuffer.toString();
                            Log.i("@@@@@ read:", res);
                            httpURLConnection.disconnect();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("Error: ", e.toString());
                        }
                    }
                });

                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //数据处理，填充到listView
                ArrayList<User> data = jsonToList(res);
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice,data);
                listView.setAdapter(adapter);
                listView.deferNotifyDataSetChanged();
            }

        });
    }

    public ArrayList<User> jsonToList(String json){
        ArrayList<User> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i =0;i<jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                User user = new User();
                user.setId(Integer.parseInt(jsonObject.getString("id")));
                user.setName(jsonObject.getString("name"));
                user.setAddress(jsonObject.getString("address"));
                list.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
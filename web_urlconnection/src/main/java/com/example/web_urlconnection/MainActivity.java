package com.example.web_urlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //1.声明
    Button get, post, put, getAll;
    TextView tv1;
    EditText et_id, et_name, et_address;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    //2.init
    public void init() {
        et_id = findViewById(R.id.editTextTextPersonName);
        et_name = findViewById(R.id.editTextTextPersonName2);
        et_address = findViewById(R.id.editTextTextPersonName3);
        post = findViewById(R.id.button2);
        get = findViewById(R.id.button);
        put = findViewById(R.id.button3);
        getAll = findViewById(R.id.button4);
        listView = findViewById(R.id.listview);
        tv1 = findViewById(R.id.textView);
        //绑定监听器
        get.setOnClickListener(listener);
        post.setOnClickListener(listener);
        put.setOnClickListener(listener);
        getAll.setOnClickListener(listener);
    }

    //3.listener
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button:
                    String get = "http://10.0.2.2:8080/user/get";
                    String result = HttpRequestUtil.httpRequest(get,"GET");
                    tv1.setText(result);
                    break;
                case R.id.button2:
                    String post = "http://10.0.2.2:8080/user/post/" + et_id.getText().toString() + "/" + et_name.getText().toString();
                    result = HttpRequestUtil.httpRequest(post,"POST");
                    tv1.setText(result);
                    break;
                case R.id.button3:
                    String put = "http://10.0.2.2:8080/user/put/" + et_id.getText().toString() +
                            "/" + et_name.getText().toString() + "/" + et_address.getText().toString();
                    result = HttpRequestUtil.httpRequest(put,"PUT");
                    tv1.setText(result);
                    break;
                case R.id.button4:
                    String getAll = "http://10.0.2.2:8080/user/get/list";
                    result = HttpRequestUtil.httpRequest(getAll,"GET");
                    //4.数据处理，填充到listView
                    ArrayList<UserBean> data =  HttpRequestUtil.jsonToList(result);
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice,data);
                    listView.setAdapter(adapter);
                    listView.deferNotifyDataSetChanged();
                    break;
            }
        }
    };
}
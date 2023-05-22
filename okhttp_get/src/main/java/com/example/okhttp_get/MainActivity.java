package com.example.okhttp_get;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageView;
    EditText editText;
    Button button1, button2, button_download,btn_upload, btn_post;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }

    public void init() {
        button1 = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button_download = findViewById(R.id.button3);
        btn_upload = findViewById(R.id.button4);
        btn_post = findViewById(R.id.btn_post);

        imageView = findViewById(R.id.imageView);
        tv = findViewById(R.id.textView);
        editText = findViewById(R.id.editTextTextPersonName);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button_download.setOnClickListener(this);
        btn_upload.setOnClickListener(this);
        btn_post.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        OkHttpClient okHttpClient;
        switch (v.getId()) {
            case R.id.button:
                okHttpClient = new OkHttpClient();
                String id = editText.getText().toString();
                String url = "http://10.0.2.2:8083/3_ModelAndView_war_exploded2/user1/" + id;
                Request request = new Request.Builder().get().url(url).build();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Call call = okHttpClient.newCall(request);
                        try {
                            Response response = call.execute();
                            String result = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "获取响应成功", Toast.LENGTH_SHORT).show();
                                    tv.setText(result);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
                                    tv.setText("Nothing");
                                }
                            });
                        }
                    }
                }).start();
                break;

            case R.id.button2:
                okHttpClient = new OkHttpClient();
                String id1 = editText.getText().toString();
                String url1 = "http://10.0.2.2:8083/3_ModelAndView_war_exploded2/user1/" + id1;
                Request request1 = new Request.Builder().get().url(url1).build();
                Call call1 = okHttpClient.newCall(request1);
                call1.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "获取失败", Toast.LENGTH_SHORT).show();
                                tv.setText("failed");
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String result1 = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "获取响应成功", Toast.LENGTH_SHORT).show();
                                tv.setText(result1);
                            }
                        });
                    }
                });
                break;


            case R.id.button3:
                String picture_url = "http://10.0.2.2:8083/3_ModelAndView_war_exploded2/download";
//                String picture_url = "https://i1.hoopchina.com.cn/newsPost/23418-1bg7ghundefined.png?x-oss-process=image/resize,w_800/format,webp";
                okHttpClient = new OkHttpClient();
                Request request2 = new Request.Builder().get().url(picture_url).build();
                Call call = okHttpClient.newCall(request2);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        InputStream inputStream = response.body().byteStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);

                                //文件名
                                String fileName = UUID.randomUUID().toString() + ".png";
                                //将图片保存到本地存储卡中
                                File file = new File(Environment.getExternalStorageDirectory(), fileName);
                                FileOutputStream fileOutputStream = null;
                                try {
                                    fileOutputStream = new FileOutputStream(file);
                                    byte[] temp = new byte[128];
                                    int length;
                                    while ((length = inputStream.read(temp)) != -1) {
                                        fileOutputStream.write(temp, 0, length);
                                    }
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                    inputStream.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
                break;

            case R.id.button4:
                String url_upload ="";


                break;

            case R.id.btn_post:
                String url_post = "";
                RequestBody body = null;
                okHttpClient = new OkHttpClient();
                Request request3 = new Request.Builder().post(body).url(url_post).build();

                break;
        }
    }


    /**
     * 传递键值对参数
     *
     * @param url
     * @param name
     */
    private void postDataWithParame(String url, String name) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formbody = new FormBody.Builder();
        formbody.add("username", name);
        Request request = new Request.Builder()
                .url(url)
                .post(formbody.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
    }

    /**
     * 传递json字符串
     *
     * @param view
     * @param url
     */
    public void postString(View view, String url) {
        OkHttpClient client = new OkHttpClient();

        MediaType JSON = MediaType.parse("text/plain;charset=utf-8");
        String jsonInfo = "{\"username\":\"lisi\",\"nickname\":\"李四\"}";//json数据.
        RequestBody requestBody = RequestBody.create(JSON, jsonInfo);

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
    }


    /**
     * 上传markdown 流文件
     *
     * @param view
     * @param url
     */
    public void postStreaming(View view, String url) {
        final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown;charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new RequestBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_MARKDOWN;
            }

            @Override
            public void writeTo(@NonNull BufferedSink bufferedSink) throws IOException {
                bufferedSink.writeUtf8("Numbers\n");
                bufferedSink.writeUtf8("----\n");
                for (int i = 2; i <= 997; i++) {
                    bufferedSink.writeUtf8(String.format("*%s=%s\n", i, factor(i)));
                }
            }

            private String factor(int n) {
                for (int i = 2; i < n; i++) {
                    int x = n / i;
                    if (x * i == n) return factor(x) + " × " + i;
                }
                return Integer.toString(n);
            }
        };

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String responseStr = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //显示响应结果
                    }
                });
            }
        });
    }

    public void postFile(String url) {
        OkHttpClient client = new OkHttpClient();
        final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
        File file = new File("README.md");//打开指定文件
        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseStr = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    public void onClick1(View view){
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }
}
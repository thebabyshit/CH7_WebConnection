package com.example.web_urlconnection;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HttpRequestUtil {

    public  static String httpRequest(String uAddr, String method) {
        final String[] res = {null};
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(uAddr);////////////////
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();//////////
                    httpURLConnection.setRequestMethod(method);//////////////////
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setReadTimeout(8000);
                    InputStream in = httpURLConnection.getInputStream();//////
                    Log.d("test--------", String.valueOf(httpURLConnection.getResponseCode() == 200));
                    int read = -1;
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((read = in.read()) != -1) {
                        stringBuffer.append((char) read);
                        Log.i("@@@@@ read:", stringBuffer.toString());
                    }
                    in.close();
                    res[0] = stringBuffer.toString();
                    Log.i("@@@@@ read:", res[0]);
                    httpURLConnection.disconnect();///////////////////
                }catch(MalformedURLException e){
                    e.printStackTrace();
                    Log.e("MalformedException:", e.toString());
                }catch(IOException e) {
                    e.printStackTrace();
                    Log.e("IOException:", e.toString());
                }
            }
        };
        thread1.start();
        try {
            thread1.join();/////////////////////////////
            /**
             *  UIThread-----------
             *      thread-----data----
             *   UIThread-----------------------------
             *             thread-----data----join()
             */
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res[0];
    }


    /**
     *
     * @param json [
     *              {"id":1,"name":"zhoubo","adress":"nxu"},
     *              {},
     *              {}
     *             ]
     * @return list<User>
     */
    public static ArrayList<UserBean> jsonToList(String json){
        Log.e("----json---",json);
        ArrayList<UserBean> list = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i =0;i<jsonArray.length();i++){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                UserBean user = new UserBean();
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

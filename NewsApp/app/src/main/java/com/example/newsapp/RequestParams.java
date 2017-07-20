package com.example.newsapp;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Sandeep on 2/6/2017.
 */

public class RequestParams {
    String method, baseURL;
    HashMap<String,String> params= new HashMap<String, String>();

    public RequestParams(String method, String baseURL) {
        this.method = method;
        this.baseURL = baseURL;
    }

    public  void addParam(String key, String value)
    {
        params.put(key,value);
    }

    public String getEncodedParams(){
        StringBuilder sb= new StringBuilder();
        for(String key:params.keySet())
        {
            try {
                String value= URLEncoder.encode(params.get(key),"UTF-8");
                if(sb.length()>0){
                    sb.append("&");
                }
                sb.append(key+"="+value);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return sb.toString();
    }

    public String getEncodeURL(){

        return  this.baseURL + "?" + getEncodedParams();
    }

    public HttpURLConnection setUpConnection() throws IOException {
        if(method.equals("GET"))
        {
            URL url = new URL(getEncodeURL());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            try {
                connection.setRequestMethod("GET");//connection.setRequestMethod("POST");//
                return connection;
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            return  connection;

        }
        else //POST
        {
            URL url = new URL(this.baseURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            OutputStreamWriter writer=new OutputStreamWriter(connection.getOutputStream());
            writer.write(getEncodedParams());
            writer.flush();
            return  connection;
        }
    }


}

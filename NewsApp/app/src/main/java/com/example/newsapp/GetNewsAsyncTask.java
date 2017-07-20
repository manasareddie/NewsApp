package com.example.newsapp;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sandeep on 2/6/2017.
 */

public class GetNewsAsyncTask extends AsyncTask<String, Void, ArrayList<News>> {
    MainActivity activity;

    public GetNewsAsyncTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<News> doInBackground(String... params) {
        try {
            URL url= new URL(params[0]);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statusCode= con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK)
            {
                BufferedReader reader=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb= new StringBuilder();
                String line = reader.readLine();
                while(line != null)
                {
                    sb.append(line);
                    line=reader.readLine();
                }
                ArrayList<News> arrayList = NewsUtil.NewsJSONParser.parseNews(sb.toString());
                return arrayList;
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  null;

    }

    @Override
    protected void onPostExecute(ArrayList<News> newses) {
        activity.onGetNews(newses);
        super.onPostExecute(newses);
    }

    static public  interface IData{
        public void onGetNews(ArrayList<News> al);
    }
}

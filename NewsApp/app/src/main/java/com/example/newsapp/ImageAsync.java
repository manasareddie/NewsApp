package com.example.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sandeep on 2/6/2017.
 */

public class ImageAsync extends AsyncTask<String,Void,Bitmap> {
    MainActivity activity;

    public ImageAsync(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        BufferedReader reader = null;
        InputStream in = null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");//connection.setRequestMethod("POST");//
            in = connection.getInputStream();
            // connection.getInputStream();//for xml parsing
            Bitmap image = BitmapFactory.decodeStream(connection.getInputStream());
            return image;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {

            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap s) {
        activity.onGetImage(s);
    }
    static public  interface IImage{
        public void onGetImage(Bitmap al);
    }

}
package com.example.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.InputDevice;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, GetNewsAsyncTask.IData, ImageAsync.IImage{
    String src;
    int index = 0;
    ArrayList<News> arrayList;
    LinearLayout vertical;
    LinearLayout horiz;
    TextView et1, et2, et3, et4;
    ImageView iv;
    RequestParams requestParams = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner= (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this, R.array.sources, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        vertical= (LinearLayout) findViewById(R.id.vertical);

        findViewById(R.id.getnews_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isConnectedOnline())
                {
                    requestParams= new RequestParams("GET","https://newsapi.org/v1/articles");
                    requestParams.addParam("apiKey","c86a3623e91048709b3db0611337fe02");
                    if(src.equals("BBC"))
                        requestParams.addParam("source","bbc-news");
                    else if(src.equals("CNN"))
                        requestParams.addParam("source","cnn");
                    else if(src.equals("Buzzfeed"))
                        requestParams.addParam("source","buzzfeed");
                    else if(src.equals("ESPN"))
                        requestParams.addParam("source","espn");
                    else if(src.equals("Sky News"))
                        requestParams.addParam("source","sky-news");
                    else
                        Toast.makeText(getApplicationContext(),"select a source",Toast.LENGTH_SHORT).show();
                    new GetNewsAsyncTask(MainActivity.this).execute(requestParams.getEncodeURL());

                }

               // NewsUtil newsutil= new NewsUtil();
            }
        });


    }
    public  void onGetNews(ArrayList<News> al){
        try {
            Log.d("demo",""+al.size());

            News n = al.get(0);
            arrayList = al;
            setData(n);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setData(News n){
        et1= new TextView(this);et2= new TextView(this);et3= new TextView(this);et4= new TextView(this);
        et1.setText(String.valueOf(n.getTitle()));et2.setText("Author :"+ n.getAuthor());
        et3.setText("Published on: "+n.getPublishedAt());
        et4.setText("Description: "+"\n"+n.getDescription());
        vertical.removeAllViews();
        vertical.addView(et1);
        vertical.addView(et2);
        vertical.addView(et3);
        vertical.addView(et4);

        new ImageAsync(this).execute(n.getUrlToImage());

    }

    public void finsh(View v){
        finish();
    }

    public void firstButton(View v){
        News n = arrayList.get(0);
        setData(n);
        index = 0;
    }

    public void prevButton(View v){
        if(arrayList!=null){
            if(index > 0){
                index --;
                News n = arrayList.get(index);
                setData(n);
            }
        }

    }
    public void nextButton(View v){
        if(arrayList!=null){
            if(index < arrayList.size()-1){
                index ++;
                News n = arrayList.get(index);
                setData(n);
            }
        }

    }

    public void lastButton(View v){
        if(arrayList!=null){

            News n = arrayList.get(arrayList.size()-1);
            setData(n);
            index = arrayList.size()-1;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        src=String.valueOf(parent.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private  boolean isConnectedOnline()
    {
        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        if( networkInfo!= null && networkInfo.isConnected())
        {
            return  true;
        }
        return  false;
    }

    @Override
    public void onGetImage(Bitmap al) {
        iv = (ImageView) findViewById(R.id.imageView);
        iv.setImageBitmap(al);
        Log.d("demo", "image");
    }
}

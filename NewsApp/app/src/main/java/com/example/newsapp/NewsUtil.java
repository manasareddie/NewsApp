package com.example.newsapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sandeep on 2/6/2017.
 */

public class NewsUtil {

    static  public  class  NewsJSONParser{
        static ArrayList<News> parseNews(String in) throws JSONException {
            ArrayList<News> newsList= new ArrayList<>();

            JSONObject root= new JSONObject(in);
            JSONArray newsJSONArray= root.getJSONArray("articles");
            for(int i=0; i<newsJSONArray.length();i++)
            {
                JSONObject newsJSONObject=newsJSONArray.getJSONObject(i);
                News news= new News();
                news.setAuthor(newsJSONObject.getString("author"));
                news.setTitle(newsJSONObject.getString("title"));
                news.setDescription(newsJSONObject.getString("description"));
                news.setUrl(newsJSONObject.getString("url"));
                news.setUrlToImage(newsJSONObject.getString("urlToImage"));
                news.setPublishedAt(newsJSONObject.getString("publishedAt"));
                newsList.add(news);


            }


            return  newsList;
        }
    }


}

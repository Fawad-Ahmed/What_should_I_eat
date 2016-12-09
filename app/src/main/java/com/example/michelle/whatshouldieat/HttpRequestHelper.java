package com.example.michelle.whatshouldieat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Michelle on 8-12-2016.
 *
 * API: Yummly
 *
 * app_id = 505ee6ce
 * app_key = cb6d5da1425c9ea816b063cf1c95f1e5
 *
 */

public class HttpRequestHelper {
    static String app_id = "505ee6ce";
    static String app_key = "cb6d5da1425c9ea816b063cf1c95f1e5";

    static String downloadFromServer_search(String... params) {
        // Create URL to request from
        String url1 = "http://api.yummly.com/v1/api/recipes?_app_id=";
        String url2 = "&_app_key=";
        String url3 = "&allowedIngredient[]=";

        String completeUrl = url1 + app_id + url2 + app_key;
        for (int i = 0; i < params.length; i++) {
            completeUrl += url3 + params[i].replaceAll("\\s+","+");
        }

        return downloadFromServer(completeUrl);
    }

    static String downloadFromServer_recipe(String... params) {
       // Create URL to request from
        String url1 = "http://api.yummly.com/v1/api/recipe/";
        String url2 = "?_app_id=";
        String url3 = "&_app_key=";

        String recipe_ID = params[0];
        String completeUrl = url1 + recipe_ID + url2 + app_id + url3 + app_key;

        return downloadFromServer(completeUrl);
    }

    private static String downloadFromServer(String completeUrl) {
        URL url = null;
        String result = "";

        try{
            url = new URL(completeUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection connection;
        if (url != null) {
            try {
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");

                // get response code
                Integer responseCode = connection.getResponseCode();

                // if 200-300, read inputstream
                if(200 <= responseCode && responseCode <= 299) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String line;
                    while((line = br.readLine()) != null) {
                        result = result + line;
                    }
                    return result;
                } else {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

                    String line;
                    while((line = br.readLine()) != null) {
                        result = result + line;
                    }
                    return result;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "no internet";
    }
}

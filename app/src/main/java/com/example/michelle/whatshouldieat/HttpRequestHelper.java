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
 * Search by ingredient:
 * http://api.yummly.com/v1/api/recipes?_app_id=505ee6ce&_app_key=cb6d5da1425c9ea816b063cf1c95f1e5&allowedIngredient[]=cheese
 */

public class HttpRequestHelper {


    static String downloadFromServer_search(String... params) {
        String ID = "505ee6ce";
        String KEY = "cb6d5da1425c9ea816b063cf1c95f1e5";

        String url1 = "http://api.yummly.com/v1/api/recipes?_app_id=";
        String url2 = "&_app_key=";
        String url3 = "&allowedIngredient[]=";

        URL url = null;
        String result = "";
        String completeUrl = url1 + ID + url2 + KEY;
        for (int i = 0; i < params.length; i++) {
            completeUrl += url3 + params[i];
        }

        System.out.println(completeUrl);

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

                // Get response code
                Integer responseCode = connection.getResponseCode();
                // If response successful
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

    static String downloadFromServer_recipe(String... params) {
        String app_id = "505ee6ce";
        String app_key = "cb6d5da1425c9ea816b063cf1c95f1e5";

        String url1 = "http://api.yummly.com/v1/api/recipe/";
        String url2 = "?_app_id=";
        String url3 = "&_app_key=";

        URL url = null;
        String result = "";
        String recipe_ID = params[0];
        String completeUrl = url1 + recipe_ID + url2 + app_id + url3 + app_key;

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

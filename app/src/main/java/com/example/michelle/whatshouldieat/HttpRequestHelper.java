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
 // API food2fork
 // KEY: 85f5cc2681661390c7a2936b73168c7f

 // Search
 // http://food2fork.com/api/search?key={KEY}&q=shredded%20chicken

 // Get
 // http://food2fork.com/api/get?key={KEY}&rId={ID}
 */


public class HttpRequestHelper {


    static String downloadFromServer_search(String... params) {
        String KEY = "85f5cc2681661390c7a2936b73168c7f";

        String url1 = "http://food2fork.com/api/search?key=";
        String url2 = "&q=";

        URL url = null;
        String result = "";
        String input = params[0];
        String completeUrl = url1 + KEY + url2 + input;

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
        String KEY = "85f5cc2681661390c7a2936b73168c7f";

        String url1 = "http://food2fork.com/api/get?key=";
        String url2 = "&rId=";

        URL url = null;
        String result = "";
        String recipe_ID = params[0];
        String completeUrl = url1 + KEY + url2 + recipe_ID;

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

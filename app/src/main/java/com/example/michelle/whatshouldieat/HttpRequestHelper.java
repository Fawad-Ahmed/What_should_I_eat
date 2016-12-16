package com.example.michelle.whatshouldieat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The HttpRequest helper requests the Yummly API and returns the result.
 *
 * downloadFromServer_search
 * The input is the ingredients that are required and the preferences of
 * the allergies and diets. The result is a list of recipes that comply
 * with the query.
 *
 * downloadFromServer_recipe
 * The input is a recipe id and the output is an elaborate amount of
 * information about the recipe, such as the ingredients, the nutritional
 * value, the course etc. Unfortunately, directions isn't included in this
 * information, but a link to the website that contain the directions is.
 */

public class HttpRequestHelper {
    static String app_id = "505ee6ce";
    static String app_key = "cb6d5da1425c9ea816b063cf1c95f1e5";

    static String downloadFromServer_search(String[] allergies, String[] diets, String... params) {
        // Create URL to request from
        String url1 = "http://api.yummly.com/v1/api/recipes?_app_id=";
        String url2 = "&_app_key=";
        String url3 = "&allowedIngredient[]=";
        String url4 = "&allowedAllergy[]=";
        String url5 = "&allowedDiet[]=";

        String completeUrl = url1 + app_id + url2 + app_key;
        // Add the ingredients
        for (int i = 0; i < params.length; i++) {
            completeUrl += url3 + params[i].replaceAll("\\s+","+");
        }

        // Add the allergies
        for (int i = 0; i < allergies.length; i++) {
            completeUrl += url4 + allergies[i];
        }

        // Add the diets
        for (int i = 0; i < diets.length; i++) {
            completeUrl += url5 + diets[i].replaceAll("\\s+","+");
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

                // Get response code
                Integer responseCode = connection.getResponseCode();

                // If 200-300, read inputstream
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

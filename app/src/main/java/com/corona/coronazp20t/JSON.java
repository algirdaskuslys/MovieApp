package com.corona.coronazp20t;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class JSON {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static JSONArray getJSONArray(JSONObject json) throws JSONException {
        // removing from JSON all unnecessary information and leaving only covid19Stats array
        int jsonLength = json.toString().length();
        String covid19Stats = "{" + json.toString().substring(96, jsonLength) + "}";

        // String to JSONObject
        JSONObject jsonObject = new JSONObject(covid19Stats);
        //JSONObject to JSONArray
        JSONArray jsonArray = jsonObject.getJSONArray("covid19Stats");

        return jsonArray;
    }

    public static ArrayList<Movie> getList(JSONObject json_data) throws JSONException {
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        // Extract data from json and store into ArrayList as class objects
        //for (int i = 0; i < jsonArray.length(); i++){
        //JSONObject json_data = jsonArray.getJSONObject(i);
        Movie movie = new Movie(
                json_data.getString("title"),
                json_data.getInt("budget"),
                json_data.getDouble("popularity"),
                json_data.getString("release_date")
        );
        movieList.add(movie);
        //}
        return movieList;
    }

    public static ArrayList<Movie> getMovieListByName(ArrayList<Movie> movieArrayList, String name) {
        ArrayList<Movie> movieArrayList1 = new ArrayList<Movie>();
        for (Movie movie : movieArrayList) {
            if (movie.getName().contains(name)) {
                movieArrayList1.add(movie);
            }
        }
        return movieArrayList1;
    }

}

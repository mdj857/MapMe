package com.example.mapme_hw4;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by mattjohnson on 3/27/18.
 */

public class MyAsyncTask extends AsyncTask<String, Void, Object[]> {

    @Override
    protected Object[] doInBackground(String... strings) {
        Object[] result = new Object[3];

        String resp_JSON = new String();
        try {
            String request = strings[0];
            resp_JSON = getLatLongByURL(request);
        } catch (Exception e){
            System.out.print(e.getStackTrace());
            return null;
        }


        try {
            JSONObject jsonObject = new JSONObject(resp_JSON);

            double lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            double lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");
            String streetName = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getString("formatted_address");

            result[0] = (Object) lat;
            result[1] = (Object) lng;
            result[2] = (Object) streetName;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public String getLatLongByURL(String requestURL) {
        URL url;
        String response = "";
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            conn.setDoOutput(true);
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


}

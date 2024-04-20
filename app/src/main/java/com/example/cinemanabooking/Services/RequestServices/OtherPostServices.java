package com.example.cinemanabooking.Services.RequestServices;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cinemanabooking.Hellper.Helper;
import com.example.cinemanabooking.Services.ApiResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class OtherPostServices extends AsyncTask<String, Void, ApiResponse> {
    private otherPostListener listener;
    public OtherPostServices(otherPostListener listener) {
        this.listener = listener;
    }
    private ApiResponse _apiResponse = new ApiResponse();
    @Override
    protected ApiResponse doInBackground(String... params) {
        String Url = params[0];
        String JsonPost = params[1];

        try {
            URL url = new URL(Helper.BaseUrl + Url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            writer.write(JsonPost);
            writer.flush();
            writer.close();
            outputStream.close();


            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                inputStream.close();
                _apiResponse.Result=response.toString();
                _apiResponse.IsSuccess=true;

            } else  {

                InputStream inputStream = urlConnection.getErrorStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                inputStream.close();
                JSONObject JsonResponse = new JSONObject(response.toString());
                JSONArray JsonErrorMessage = JsonResponse.getJSONArray("errorMessage");
                for (int i = 0; i < JsonErrorMessage.length(); i++) {
                    _apiResponse.ErrorMessage.add(JsonErrorMessage.getString(i));
                }
            }
            _apiResponse.HttpStateCode = responseCode;
            return _apiResponse;

        } catch (Exception e) {
            Log.e("Login Request", "  Error:   " + e.getMessage());
            return null;
        }
    }
    @Override
    protected void onPostExecute(ApiResponse result) {
        if (result.IsSuccess) {
            listener.onOtherPostSuccess(result);
        } else {
            listener.onOtherPostFailure(result);
        }
    }
    public interface otherPostListener {
        void onOtherPostSuccess(ApiResponse response);

        void onOtherPostFailure(ApiResponse response);
    }
}
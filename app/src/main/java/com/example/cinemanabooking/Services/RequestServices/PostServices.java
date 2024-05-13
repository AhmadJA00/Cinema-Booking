package com.example.cinemanabooking.Services.RequestServices;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.cinemanabooking.Hellper.Helper;
import com.example.cinemanabooking.MainActivity;
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

public class PostServices  extends AsyncTask<String, Void, ApiResponse> {
    private PostListener listener;
    public PostServices(PostListener listener) {
        this.listener = listener;
    }
    private ApiResponse _apiResponse = new ApiResponse();
    @Override
    protected ApiResponse doInBackground(String... params) {
        String Url = params[0];
        String JsonPost = params[1];
        String bearerToken = params[2];


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

            if (bearerToken != null && !bearerToken.isEmpty()) {
                urlConnection.setRequestProperty("Authorization", "Bearer " + bearerToken);
            }

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {

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
            return _apiResponse;
        }
    }
    @Override
    protected void onPostExecute(ApiResponse result) {
        if (result.IsSuccess) {
            listener.onPostSuccess(result);
        } else {
            listener.onPostFailure(result);
        }
    }
    public interface PostListener {
        void onPostSuccess(ApiResponse response);

        void onPostFailure(ApiResponse response);
    }
}
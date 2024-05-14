package com.example.cinemanabooking.Services.RequestServices;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cinemanabooking.Hellper.Helper;
import com.example.cinemanabooking.Services.ApiResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetServices extends AsyncTask<String, Void, ApiResponse> {
    private GetListener listener;

    public GetServices(GetListener listener) {
        this.listener = listener;
    }

    private ApiResponse _apiResponse = new ApiResponse();

    @Override
    protected ApiResponse doInBackground(String... params) {
        String Url = params[0];

        try {
            URL url = new URL(Helper.BaseUrl + Url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            if (Helper._Token != null && !Helper._Token.isEmpty()) {
                urlConnection.setRequestProperty("Authorization", "Bearer " + Helper._Token);
            }

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
                _apiResponse.Result = response.toString();
                _apiResponse.IsSuccess = true;
            } else {
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
            listener.onGetSuccess(result);
        } else {
            listener.onGetFailure(result);
        }
    }

    public interface GetListener {
        void onGetSuccess(ApiResponse response);

        void onGetFailure(ApiResponse response);
    }
}

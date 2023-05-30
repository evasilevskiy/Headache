package ru.evgeka.headache;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Controller {

    int result;
    final static String BASE_GET_URL = "http://37.46.131.252:15307/health/headache/get_state/Maria";
    final static String BASE_PUT_URL = "http://37.46.131.252:15307/health/headache/set_state/Maria";
    OkHttpClient client;

    Controller() {

        client = new OkHttpClient();
        result = 9;

    }

    public void getState() {

        Request request = new Request.Builder()
                .url(BASE_GET_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                result = 2;
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String body = response.body().string();
                if (Boolean.parseBoolean(body)) result = 1;
                else result = 0;
            }
        });
    }

    public void putState(boolean state) {

        String stateUrl;

        if(state) stateUrl = BASE_PUT_URL + "/TRUE";
        else stateUrl = BASE_PUT_URL + "/FALSE";

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        String jsonRequest = "Some request";

        RequestBody body =  RequestBody.create(jsonRequest, JSON);

        Request request = new Request.Builder()
                .url(stateUrl)
                .put(body)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

                result = 2;
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                result = 1;
            }
        });
    }

    public int getResult() {

        return result;
    }

}

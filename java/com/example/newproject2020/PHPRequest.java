package com.example.newproject2020;

import android.app.Activity;
import android.content.ContentValues;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PHPRequest {

    String prefix;

    public PHPRequest(String p){
        prefix = p;
    }

    public void doRequest(final Activity a, String file, ContentValues params, final RequestHandler rh){
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        for (String key:params.keySet()){
            builder.add(key,params.getAsString(key));
        }

        Request request = new Request.Builder()
                .url(prefix+file)
                .post(builder.build())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    final String myResponse = response.body().string();

                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                rh.processResponse(myResponse);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
        });
    }
}

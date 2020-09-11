package com.corner.apps;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitGSON {
    private static RetrofitGSON ourInstance;

    private Retrofit retrofit;

    private Map<String, String> requestHeaders = new HashMap<>();

    private ApiService apiService;

    //URL REST API Yang dituju
    private static final String BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/";

    //KEY API
    private static final String API_KEY = "1";

    private RetrofitGSON() {
        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(2, TimeUnit.MINUTES);
        client.readTimeout(2, TimeUnit.MINUTES);
        client.writeTimeout(2, TimeUnit.MINUTES);

        // Tambahkan Authentifikasi pada default request.
        client.addInterceptor(this.headerInterceptor());

        //Retrofit
        OkHttpClient clientBuild = client.build();
        Retrofit.Builder retrofit = new Retrofit.Builder();
        retrofit.baseUrl(RetrofitGSON.BASE_URL);
        retrofit.client(clientBuild);
        retrofit.addConverterFactory(GsonConverterFactory.create(gson));

        this.retrofit = retrofit.build();

    }

    public static RetrofitGSON getInstance() {
        if (ourInstance == null) {
            ourInstance = new RetrofitGSON();

        }
        return ourInstance;
    }

    public ApiService api() {
        if (this.apiService == null) {
            this.apiService = this.retrofit.create(ApiService.class);
        }
        return this.apiService;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }


    private Interceptor headerInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                final String basic = "Bearer " + API_KEY;
                builder.addHeader("Accept", "application/json");
                builder.addHeader("Authorization", basic);
                builder.addHeader("Cache-Control", "no-cache");
                for (final String headerName : requestHeaders.keySet()) {
                    final String headerValue = requestHeaders.get(headerName);
                    builder.header(headerName, headerValue);
                }
                return chain.proceed(
                        builder.build()
                );
            }
        };
    }
}

package com.mindorks.framework.androidloginandregisterwithsqlitedatabase.network;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//    public static  final  String BASE_URL = "http://192.168.1.102:8080/api/test/";
//    public static  final  String BASE_URL = "https://jsonplaceholder.typicode.com/";
//public static  final  String BASE_URL = "http://go.insharptechnologies.com/api/";
    public static  final  String BASE_URL = "https://tuti.world/api/";

    public  static Retrofit retrofit = null;

    public static ApiInterface getRetrofitClient(){
//        System.out.println("11111111111111111111");
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return  retrofit.create(ApiInterface.class);
    }

    public static ApiInterface getAuthRetrofitClient(Context context){


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("api_client", "value").build();
                return chain.proceed(request);
            }
        });

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return  retrofit.create(ApiInterface.class);
    }
}

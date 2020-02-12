//============================================================+
// File name   : AddressMapActivity
// Begin       : 27-02-2019
// Last Update : 27-02-2019
//
// Short Description : FPO Office Address showing in Googlemaps by using FPO office Address Latitude and Longitude values .
//
// Author: Devi Prasanna, Vitalsoft Artifacts Pvt Ltd//
//============================================================+

package com.lasys.app.quotes.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lasys.app.quotes.intrface.AppConstants.BASE_URL;

public class ApiClient
{
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null)
        {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            OkHttpClient client = new OkHttpClient().newBuilder()
                    .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS).build();

             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}

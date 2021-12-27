package com.tarasovms.weather.data.remote

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import java.io.IOException
import retrofit2.http.GET
import retrofit2.http.Query


class RemoteService {

    @GET("/api/get")
    fun getData(
        @Query("name") resourceName: String?,
        @Query("num") count: Int
    ): Call<List<AnekdotModel?>?>?{

    }


    private var okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .addInterceptor { chain ->
            val originalRequest: Request = chain.request()
            val builder: Request.Builder = originalRequest.newBuilder()
                .header("Authorization", Credentials.basic("aUsername", "aPassword"))
            val newRequest: Request = builder.build()
            chain.proceed(newRequest)
        }.build()

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.example.com")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()


}
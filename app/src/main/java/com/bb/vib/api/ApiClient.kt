package com.bb.vib.api

import com.bb.vib.VibApp
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {

    companion object {

        private var retrofit: Retrofit? = null

        fun getClient(): Retrofit? {

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(ChuckInterceptor(VibApp.applicationContext()))
                .addInterceptor { chain ->
                    val newRequest = chain.request().newBuilder()
//                        .addHeader("Authorization", Constants.HEADER_TOKEN)
                        .addHeader("Content-Type", "application/json")
                        .build()
                    chain.proceed(newRequest)
                }
                .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build()
            }
            return retrofit
        }


    }


}
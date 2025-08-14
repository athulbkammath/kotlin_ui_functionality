package com.eto.binding_usage_project.api

import com.eto.binding_usage_project.util.Constants
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val requestInterceptor = Interceptor { chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", Constants.API_KEY)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }


    private val okHttpClient = okhttp3.OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .build()


    fun getClient(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient).addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }


}
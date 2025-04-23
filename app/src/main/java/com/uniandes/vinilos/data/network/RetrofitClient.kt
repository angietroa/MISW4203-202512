package com.uniandes.vinilos.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.uniandes.vinilos.BuildConfig

object RetrofitClient {
    private val BASE_URL = BuildConfig.BACKEND_BASE_URL

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
package com.example.resumeapp

import com.google.firebase.appdistribution.gradle.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://expressjs-api-resume-random.onrender.com/"

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}
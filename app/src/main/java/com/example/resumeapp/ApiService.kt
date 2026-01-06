package com.example.resumeapp

import com.google.firebase.appdistribution.gradle.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object RetrofitInstance {
    private const val BASE_URL = "https://expressjs-api-resume-random.onrender.com/"

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    interface ApiService {
        @GET("resume")
        suspend fun getResume(@Query("name") name: String): ResumeData
    }
}

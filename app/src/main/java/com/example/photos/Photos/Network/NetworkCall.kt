package com.example.photos.Photos.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkCall {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: PhotoApiService by lazy {
        retrofit.create(PhotoApiService::class.java)
    }
}


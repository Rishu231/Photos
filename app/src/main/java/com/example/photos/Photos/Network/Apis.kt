package com.example.photos.Photos.Network

import com.example.photos.Photos.Photo
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoApiService {
    @GET("v2/list")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<Photo>
}

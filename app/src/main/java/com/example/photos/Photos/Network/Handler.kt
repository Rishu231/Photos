package com.example.photos.Photos.Network

import android.content.Context
import com.example.photos.Photos.Photo
import com.example.photos.Room.PhotoDao

class Handler(private val context: Context, private val photoDao: PhotoDao) {
//    private var currentPage = 0

    suspend fun fetchPhotos(page: Int, limit: Int): List<Photo> {
        return try {

            val cachedPhotos = photoDao.getPhotosByPage(page, limit)
            if (cachedPhotos.isNotEmpty()) {
                return cachedPhotos  // Return cached data if available
            }

            val photos = NetworkCall.api.getPhotos(page, limit)
            photoDao.insertPhotos(photos)

            photos
        } catch (e: Exception) {
            photoDao.getAllPhotos()  //if network fails
        }
    }
}


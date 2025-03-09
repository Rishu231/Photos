package com.example.photos.Photos.Network

import android.content.Context
import com.example.photos.Photos.Photo
import com.example.photos.Room.PhotoDao
import com.example.photos.Room.downloadAndCacheImage

class Handler(private val context: Context, private val photoDao: PhotoDao) {
    private var currentPage = 0

    suspend fun fetchPhotos(page: Int, limit: Int): List<Photo> {
        return try {
            // ✅ Step 1: Check if cached data is available
            val cachedPhotos = photoDao.getPhotosByPage(page, limit)
            if (cachedPhotos.isNotEmpty()) {
                return cachedPhotos  // Return cached data if available
            }

            // ✅ Step 2: Fetch from API only if cache is empty
            val photos = NetworkCall.api.getPhotos(page, limit)

            // ✅ Step 3: Store fetched data in Room without blocking UI
            photoDao.insertPhotos(photos)

            photos
        } catch (e: Exception) {
            photoDao.getAllPhotos()  // Return cached data if network fails
        }
    }
}


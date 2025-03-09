package com.example.photos.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.photos.Photos.Photo

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photo: List<Photo>)

    @Query("SELECT * FROM photos LIMIT :limit OFFSET (:page - 1) * :limit")
    suspend fun getPhotosByPage(page: Int, limit: Int): List<Photo>

    @Query("SELECT * FROM photos")
    suspend fun getAllPhotos(): List<Photo>

    @Query("DELETE FROM photos")
    suspend fun clearAllPhotos()
}

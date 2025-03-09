package com.example.photos.Photos

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.photos.Photos.Network.Handler
import com.example.photos.Room.PhotoDatabase
import kotlinx.coroutines.delay

class ViewModel (application: Application) : AndroidViewModel(application) {
    private val photoDao = PhotoDatabase.getInstance(application).photoDao()
    private val repository = Handler(application, photoDao) // ✅ Pass `application` (Context)

    private val _photos = MutableLiveData<List<Any>>() // List includes headers & photos
    val photos: LiveData<List<Any>> get() = _photos

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _filteredPhotos = MutableLiveData<List<Any>>()  // Changed from List<Photo> to List<Any>
    val filteredPhotos: LiveData<List<Any>> get() = _filteredPhotos



    private var currentPage = 1

    fun fetchPhotos() {
        if (_isLoading.value == true) return
        _isLoading.postValue(true)

        viewModelScope.launch {
            delay(1500)
            val newPhotos = repository.fetchPhotos(page = currentPage, limit = 30)
            val groupedPhotos = groupPhotosByAlbum(newPhotos)

            val updatedPhotos = if (currentPage == 1) groupedPhotos else _photos.value.orEmpty() + groupedPhotos
            _photos.postValue(updatedPhotos)

            // 🔹 Update _filteredPhotos only if no search is applied
            if (_filteredPhotos.value.isNullOrEmpty()) {
                _filteredPhotos.postValue(updatedPhotos)
            }

            currentPage++
            _isLoading.postValue(false)
        }
    }


    private fun groupPhotosByAlbum(photos: List<Photo>): List<Any> {
        val grouped = mutableListOf<Any>()
        var lastAlbum: String? = null

        for (photo in photos) {
            // Ensure the first header is always added
            if (photo.author != lastAlbum) {
                grouped.add(photo.author)
                lastAlbum = photo.author
            }
            grouped.add(photo) // Add the actual photo
        }
//        Log.d("MyTag", grouped.toString() )
        return grouped
    }

    fun searchPhotos(query: String) {
        val originalPhotos = _photos.value.orEmpty()

        if (query.isEmpty()) {
            _filteredPhotos.postValue(originalPhotos) // Restore original list on empty search
            return
        }

        val filteredList = mutableListOf<Any>()
        var lastHeader: String? = null

        for (item in originalPhotos) {
            if (item is String) { // Album header
                lastHeader = item
            } else if (item is Photo && (item.id.contains(query, true) || item.author.contains(query, true))) {
                if (lastHeader != null && !filteredList.contains(lastHeader)) {
                    filteredList.add(lastHeader) // Keep album header
                }
                filteredList.add(item)
            }
        }

        _filteredPhotos.postValue(filteredList) // Update only filtered data
    }

}
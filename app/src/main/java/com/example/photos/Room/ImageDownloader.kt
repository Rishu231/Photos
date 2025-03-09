package com.example.photos.Room

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import java.io.File
import java.io.FileOutputStream

suspend fun downloadAndCacheImage(context: Context, imageUrl: String): String? {
    val loader = ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .allowHardware(false)
        .build()

    val result = loader.execute(request).drawable?.toBitmap() ?: return null // ✅ Fix null crash

    val file = File(context.cacheDir, "photo_${System.currentTimeMillis()}.jpg")
    FileOutputStream(file).use { outputStream ->
        result.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    }

    return file.absolutePath
}

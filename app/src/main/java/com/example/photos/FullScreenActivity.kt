package com.example.photos

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load

class FullScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)
        val imageView: ImageView = findViewById(R.id.fullscreenImageView)
        val textView: TextView = findViewById(R.id.textViewAuthor)

        val imageUrl = intent.getStringExtra("IMAGE_URL")
        val author = intent.getStringExtra("AUTHOR")

        imageView.load(imageUrl)
        textView.text = author

        imageView.setOnClickListener {
            finish() // Close activity on click
        }
    }
}
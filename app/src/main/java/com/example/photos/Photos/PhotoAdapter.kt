package com.example.photos.Photos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.photos.R

class PhotoAdapter(private val onClick: (Photo) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var items: List<Any> = emptyList()

    companion object {
        const val TYPE_PHOTO = 1
        const val TYPE_HEADER = 2
    }

    fun submitList(newItems: List<Any>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] is String) TYPE_HEADER else TYPE_PHOTO
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_PHOTO -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
                PhotoViewHolder(view, onClick)
            }
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_header, parent, false)
                HeaderViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PhotoViewHolder -> holder.bind(items[position] as Photo)
            is HeaderViewHolder -> holder.bind(items[position] as String)
        }
    }

    override fun getItemCount(): Int = items.size

    class PhotoViewHolder(itemView: View, private val onClick: (Photo) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)

        fun bind(photo: Photo) {
            textViewAuthor.text = photo.author
            imageView.load(photo.download_url)

            itemView.setOnClickListener {
                onClick(photo)
            }
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewHeader: TextView = itemView.findViewById(R.id.textViewHeader)

        fun bind(headerText: String) {
            textViewHeader.text = headerText
        }
    }
}

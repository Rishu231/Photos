package com.example.photos.Photos


import android.graphics.Canvas
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView

class StickyHeader(private val adapter: PhotoAdapter) : RecyclerView.ItemDecoration() {
    private val paint = Paint().apply {
        textSize = 40f
        color = android.graphics.Color.BLACK
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val child = parent.getChildAt(0) ?: return
        val position = parent.getChildAdapterPosition(child)

        if (adapter.getItemViewType(position) == PhotoAdapter.TYPE_HEADER) {
            val text = adapter.items[position] as String
            c.drawText(text, child.left.toFloat(), child.top.toFloat() - 10, paint)
        }
    }
}

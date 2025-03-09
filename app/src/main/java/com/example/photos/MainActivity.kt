package com.example.photos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.photos.Photos.PhotoAdapter
import com.example.photos.Photos.StickyHeader
import com.example.photos.Photos.ViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhotoAdapter
    private lateinit var searchEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        val loadingView: View = findViewById(R.id.loadingView)
        searchEditText = findViewById(R.id.searchEditText)
        val searchButton = findViewById<Button>(R.id.searchButton)

        // ✅ Pass onClick event to open full-screen activity
        adapter = PhotoAdapter { photo ->
            val intent = Intent(this, FullScreenActivity::class.java).apply {
                putExtra("IMAGE_URL", photo.download_url)
                putExtra("AUTHOR", photo.author)
            }
            startActivity(intent)
        }

        // ✅ Fix GridLayoutManager
        val layoutManager = GridLayoutManager(this, 4)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.getItemViewType(position) == PhotoAdapter.TYPE_HEADER) 4 else 1
            }
        }

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(StickyHeader(adapter))

        // ✅ Observe ViewModel data
        viewModel.photos.observe(this, Observer { photos ->
            adapter.submitList(photos)
        })

        viewModel.isLoading.observe(this) { isLoading ->
            loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.filteredPhotos.observe(this, Observer { photos ->
            adapter.submitList(photos)
        })

        // ✅ Initial Data Load
        viewModel.fetchPhotos()

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.searchPhotos(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // ✅ Fix RecyclerView Scroll Listener for Lazy Loading
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                // ✅ Load more items only if not already loading and near the end
                if (!viewModel.isLoading.value!! && lastVisibleItemPosition >= totalItemCount - 6) {
                    viewModel.fetchPhotos()
                }
            }
        })

        searchButton.setOnClickListener {
            hideKeyboard()
            val query = searchEditText.text.toString().trim()
            viewModel.searchPhotos(query)

            if (query.isEmpty()) {
                viewModel.searchPhotos("") // Reset to full list
            }
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchEditText.windowToken, 0)
    }
}

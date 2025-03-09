Photo Album App

📌 Overview

This is a Photo Album App that fetches and displays images from a remote API. It supports:

Lazy loading for efficient data fetching 📥

Sticky headers for categorized sections 📌

Full-screen image view with author details 📸

Search functionality for filtering images 🔍

🚀 Features

Grid Layout: Images are displayed in a responsive grid with headers.

Lazy Loading: Loads more images when scrolled to the bottom.

Search Bar: Filters images based on author names.

Full-Screen Mode: Tap an image to view it in full screen with author details.

🛠️ Setup & Installation

1️⃣ Clone the Repository

https://github.com/Rishu231/Photos.git
cd photo

2️⃣ Open in Android Studio

Open Android Studio.

Click on Open an Existing Project.

Select the cloned photo-album-app folder.

3️⃣ Build & Run the App

Ensure you have a connected device or an Android Emulator running.

Click Run ▶️ or use the shortcut Shift + F10.

📋 Dependencies

The app uses the following dependencies:

// Image loading
implementation 'io.coil-kt:coil:2.3.0'

// ViewModel & LiveData
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1'
implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.6.1'

// RecyclerView & Grid Layout
implementation 'androidx.recyclerview:recyclerview:1.3.1'
implementation 'androidx.appcompat:appcompat:1.6.1'

Make sure to sync Gradle after adding dependencies.

🛠️ API Configuration

The app fetches photos from a public API. If required, update the base URL inside NetworkCall.kt:

const val BASE_URL = "https://picsum.photos/v2/list"





🏗️ Folder Structure

📂 app/src/main/
 ├── 📂 java/com/example/photos
 │   ├── 📂 Photos/  # Photo models, ViewModel, Adapter
 │   ├── 📂 Room/    # Local caching with Room Database
 │   ├── 📂 Network/ # API calls using Retrofit
 │   ├── MainActivity.kt  # Main UI logic
 │   ├── FullScreenActivity.kt  # Handles full-screen view
 │
 ├── 📂 res/layout/
 │   ├── activity_main.xml  # Main UI
 │   ├── item_photo.xml  # Photo item layout
 │   ├── activity_fullscreen.xml  # Full-screen UI
 │
 ├── 📂 res/drawable/
 │   ├── placeholder.png  # Placeholder image

🤝 Contributing




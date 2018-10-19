package com.example.mitch.placemark.main

import android.app.Application
import com.example.mitch.placemark.models.PlacemarkJSONStore
import com.example.mitch.placemark.models.PlacemarkStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {
  lateinit var placemarks: PlacemarkStore

  override fun onCreate() {
    super.onCreate()
    placemarks = PlacemarkJSONStore(applicationContext)
    info("Placemark started")
  }
}
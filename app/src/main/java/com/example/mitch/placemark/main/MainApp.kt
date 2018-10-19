package com.example.mitch.placemark.main

import android.app.Application
import com.example.mitch.placemark.models.PlacemarkMemStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {
  lateinit var placemarks: PlacemarkMemStore

  override fun onCreate() {
    super.onCreate()
    placemarks = PlacemarkMemStore()
    info("Placemark started")
  }
}
package com.example.mitch.placemark.main

import android.app.Application
import com.example.mitch.placemark.models.PlacemarkMemStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {
  val placemarks = PlacemarkMemStore()

  override fun onCreate() {
    super.onCreate()
    info("Placemark started")
  }
}
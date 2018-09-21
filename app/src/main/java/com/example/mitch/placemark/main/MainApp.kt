package com.example.mitch.placemark.main

import android.app.Application
import com.example.mitch.placemark.models.PlacemarkModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {
  val placemarks = ArrayList<PlacemarkModel>()

  override fun onCreate() {
    super.onCreate()
    info("Placemark started")
  }
}
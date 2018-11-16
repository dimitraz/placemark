package com.example.mitch.placemark.activities

import com.example.mitch.placemark.main.MainApp
import com.example.mitch.placemark.models.PlacemarkModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class PlacemarkListPresenter(val activity: PlacemarkListActivity) {
  var app: MainApp = activity.application as MainApp

  fun getPlacemarks() = app.placemarks.findAll()

  fun doAddPlacemark() {
    activity.startActivityForResult<PlacemarkActivity>(0)
  }

  fun doEditPlacemark(placemark: PlacemarkModel) {
    activity.startActivityForResult(activity.intentFor<PlacemarkActivity>().putExtra("placemark_edit", placemark), 0)
  }

  fun doShowPlacemarksMap() {
    activity.startActivity<PlacemarkMapsActivity>()
  }
}
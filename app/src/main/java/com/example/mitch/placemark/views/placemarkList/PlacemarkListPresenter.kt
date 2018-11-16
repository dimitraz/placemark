package com.example.mitch.placemark.views.placemarkList

import com.example.mitch.placemark.activities.PlacemarkMapsView
import com.example.mitch.placemark.main.MainApp
import com.example.mitch.placemark.models.PlacemarkModel
import com.example.mitch.placemark.views.placemark.PlacemarkView
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.startActivityForResult

class PlacemarkListPresenter(val view: PlacemarkListView) {
  var app: MainApp = view.application as MainApp

  fun getPlacemarks() = app.placemarks.findAll()

  fun doAddPlacemark() {
    view.startActivityForResult<PlacemarkView>(0)
  }

  fun doEditPlacemark(placemark: PlacemarkModel) {
    view.startActivityForResult(view.intentFor<PlacemarkView>().putExtra("placemark_edit", placemark), 0)
  }

  fun doShowPlacemarksMap() {
    view.startActivity<PlacemarkMapsView>()
  }
}
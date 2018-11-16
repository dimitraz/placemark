package com.example.mitch.placemark.views.placemark

import android.content.Intent
import com.example.mitch.placemark.views.editLocation.EditLocationView
import com.example.mitch.placemark.helpers.showImagePicker
import com.example.mitch.placemark.main.MainApp
import com.example.mitch.placemark.models.Location
import com.example.mitch.placemark.models.PlacemarkModel
import org.jetbrains.anko.intentFor

class PlacemarkPresenter(val view: PlacemarkView) {
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2

  var placemark = PlacemarkModel()
  var location = Location(52.245696, -7.139102, 15f)
  var app: MainApp = view.application as MainApp
  var edit = false;

  init {
    if (view.intent.hasExtra("placemark_edit")) {
      edit = true
      placemark = view.intent.extras.getParcelable<PlacemarkModel>("placemark_edit")
      view.showPlacemark(placemark)
    }
  }

  fun doAddOrSave(title: String, description: String) {
    placemark.title = title
    placemark.description = description
    if (edit) {
      app.placemarks.update(placemark)
    } else {
      app.placemarks.create(placemark)
    }
    view.finish()
  }

  fun doCancel() {
    view.finish()
  }

  fun doDelete() {
    app.placemarks.delete(placemark)
    view.finish()
  }

  fun doSelectImage() {
    showImagePicker(view, IMAGE_REQUEST)
  }

  fun doSetLocation() {
    if (placemark.location.zoom != 0f) {
      location.lat = placemark.location.lat
      location.lng = placemark.location.lng
      location.zoom = placemark.location.zoom
    }
    view.startActivityForResult(view.intentFor<EditLocationView>().putExtra("location", location), LOCATION_REQUEST)
  }

  fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
    when (requestCode) {
      IMAGE_REQUEST -> {
        placemark.image = data.data.toString()
        view.showPlacemark(placemark)
      }
      LOCATION_REQUEST -> {
        location = data.extras.getParcelable<Location>("location")
        placemark.location.lat = location.lat
        placemark.location.lng = location.lng
        placemark.location.zoom = location.zoom
      }
    }
  }
}
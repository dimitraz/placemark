package com.example.mitch.placemark.views.placemarkMaps

import com.example.mitch.placemark.main.MainApp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class PlacemarkMapsPresenter(val view: PlacemarkMapsView) {
  var app: MainApp = view.application as MainApp

  fun doPopulateMap(map: GoogleMap) {
    map.uiSettings.isZoomControlsEnabled = true
    map.setOnMarkerClickListener(view)
    app.placemarks.findAll().forEach {
      val loc = LatLng(it.location.lat, it.location.lng)
      val options = MarkerOptions().title(it.title).position(loc)
      map.addMarker(options).tag = it.id
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
    }
  }

  fun doMarkerSelected(marker: Marker) {
    val tag = marker.tag as Long
    val placemark = app.placemarks.findById(tag)
    if (placemark != null) view.showPlacemark(placemark)
  }
}
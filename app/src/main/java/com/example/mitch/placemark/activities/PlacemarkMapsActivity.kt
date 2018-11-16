package com.example.mitch.placemark.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mitch.placemark.R
import com.example.mitch.placemark.main.MainApp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_placemark_maps.*
import kotlinx.android.synthetic.main.content_placemark_maps.*

class PlacemarkMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {
  lateinit var map: GoogleMap
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark_maps)
    setSupportActionBar(toolbarMaps)
    app = application as MainApp

    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync {
      map = it
      configureMap()
    }
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    currentTitle.text = marker.title
    return false
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }

  fun configureMap() {
    map.uiSettings.setZoomControlsEnabled(true)
    map.setOnMarkerClickListener(this)
    app.placemarks.findAll().forEach {
      val loc = LatLng(it.location.lat, it.location.lng)
      val options = MarkerOptions().title(it.title).position(loc)
      map.addMarker(options).tag = it.id
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
    }
  }
}

// app -> build -> tmp -> kotlin-classes -> debug
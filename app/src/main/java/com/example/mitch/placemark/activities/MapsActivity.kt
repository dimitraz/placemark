package com.example.mitch.placemark.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.mitch.placemark.R
import com.example.mitch.placemark.models.Location

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,  GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener {
  private lateinit var map: GoogleMap
  var location = Location()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_maps)

    location = intent.extras.getParcelable<Location>("location")

    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    val mapFragment = supportFragmentManager
        .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
  }

  override fun onMapReady(googleMap: GoogleMap) {
    map = googleMap

    // Add a marker and move the camera
    val loc = LatLng(location.lat, location.lng)
    val options = MarkerOptions()
        .title("Placemark")
        .snippet("GPS: " + loc.toString())
        .draggable(true)
        .position(loc)
    map.addMarker(options)
    map.setOnMarkerDragListener(this)
    map.setOnMarkerClickListener(this)
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
  }

  override fun onMarkerDragEnd(marker: Marker) {
    location.lat = marker.position.latitude
    location.lng = marker.position.longitude
    location.zoom = map.cameraPosition.zoom
  }

  override fun onMarkerDragStart(marker: Marker) {
  }

  override fun onMarkerDrag(marker: Marker) {
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    var loc = LatLng(location.lat, location.lng)
    marker.snippet = "GPS: " + loc.toString()
    return false
  }

  override fun onBackPressed() {
    val resultIntent = Intent()
    resultIntent.putExtra("location", location)
    setResult(Activity.RESULT_OK, resultIntent)
    finish()
    super.onBackPressed()
  }
}

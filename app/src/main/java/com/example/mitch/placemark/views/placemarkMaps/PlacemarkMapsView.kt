package com.example.mitch.placemark.views.placemarkMaps

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mitch.placemark.R
import com.example.mitch.placemark.helpers.readImageFromPath
import com.example.mitch.placemark.models.PlacemarkModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_placemark_maps.*
import kotlinx.android.synthetic.main.content_placemark_maps.*

class PlacemarkMapsView : AppCompatActivity(), GoogleMap.OnMarkerClickListener {
  lateinit var presenter: PlacemarkMapsPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark_maps)
    setSupportActionBar(toolbarMaps)

    presenter = PlacemarkMapsPresenter(this)

    mapView.onCreate(savedInstanceState)
    mapView.getMapAsync {
      presenter.doPopulateMap(it)
    }
  }

  fun showPlacemark(placemark: PlacemarkModel) {
    currentTitle.text = placemark.title
    currentDescription.text = placemark.description
    imageView.setImageBitmap(readImageFromPath(this, placemark.image))
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    presenter.doMarkerSelected(marker)
    return true
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
}

// app -> build -> tmp -> kotlin-classes -> debug
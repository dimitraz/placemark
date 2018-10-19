package com.example.mitch.placemark.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.mitch.placemark.R
import com.example.mitch.placemark.R.layout.activity_placemark
import com.example.mitch.placemark.helpers.readImage
import com.example.mitch.placemark.helpers.readImageFromPath
import com.example.mitch.placemark.helpers.showImagePicker
import com.example.mitch.placemark.main.MainApp
import com.example.mitch.placemark.models.Location
import com.example.mitch.placemark.models.PlacemarkModel
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {
  var placemark = PlacemarkModel()
  val IMAGE_REQUEST = 1
  val LOCATION_REQUEST = 2
  var edit = false
  var image: String = ""
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    app = application as MainApp

    // Update view for editing a placemark
    if (intent.hasExtra("placemark_edit")) {
      edit = true
      btnAdd.setText(getString(R.string.button_editPlacemark))

      if (placemark.image != null) {
        chooseImage.setText(getString(R.string.button_editImage))
      }

      placemark = intent.extras.getParcelable<PlacemarkModel>("placemark_edit")
      placemarkTitle.setText(placemark.title)
      description.setText(placemark.description)
      placemarkImage.setImageBitmap(readImageFromPath(this, placemark.image))
    }

    // Add choose image button listener
    chooseImage.setOnClickListener {
      showImagePicker(this, IMAGE_REQUEST)
    }

    // Add location button listener
    placemarkLocation.setOnClickListener {
      val location = Location(52.245696, -7.139102, 15f)
      if (placemark.location.zoom != 0f) {
        location.lat =  placemark.location.lat
        location.lng = placemark.location.lng
        location.zoom = placemark.location.zoom
      }
      startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    // Add create placemark button listener
    btnAdd.setOnClickListener() {
      placemark.title = placemarkTitle.text.toString()
      placemark.description = description.text.toString()

      if (placemark.title.isNotEmpty()) {
        if (edit) {
          app.placemarks.update(placemark.copy())
          app.placemarks.logAll()
        } else {
          app.placemarks.create(placemark.copy())
          app.placemarks.logAll()
        }
        setResult(AppCompatActivity.RESULT_OK)
        finish()
      } else {
        toast(getString(R.string.add_error_message))
      }
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
      IMAGE_REQUEST -> {
        if (data != null) {
          placemark.image = data.getData().toString()
          placemarkImage.setImageBitmap(readImage(this, resultCode, data))
          chooseImage.setText(getString(R.string.button_editImage))
        }
      }
      LOCATION_REQUEST -> {
        if (data != null) {
          placemark.location = data.extras.getParcelable<Location>("location")
        }
      }
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_placemark, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_cancel -> finish()
    }
    return super.onOptionsItemSelected(item)
  }
}

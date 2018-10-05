package com.example.mitch.placemark.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.mitch.placemark.R
import com.example.mitch.placemark.main.MainApp
import com.example.mitch.placemark.models.PlacemarkModel
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast


class PlacemarkActivity : AppCompatActivity(), AnkoLogger {
  var placemark = PlacemarkModel()
  val placemarks = ArrayList<PlacemarkModel>()
  var edit = false
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)
    toolbarAdd.title = title
    setSupportActionBar(toolbarAdd)
    app = application as MainApp

    if (intent.hasExtra("placemark_edit")) {
      edit = true
      btnAdd.setText(getString(R.string.button_editPlacemark))

      placemark = intent.extras.getParcelable<PlacemarkModel>("placemark_edit")
      placemarkTitle.setText(placemark.title)
      description.setText(placemark.description)
    }

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

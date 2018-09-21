package com.example.mitch.placemark.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.mitch.placemark.R
import com.example.mitch.placemark.main.MainApp
import com.example.mitch.placemark.models.PlacemarkModel
import kotlinx.android.synthetic.main.activity_placemark.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class PlacemarkActivity : AppCompatActivity(), AnkoLogger {
  val placemarks = ArrayList<PlacemarkModel>()
  lateinit var app : MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark)
    app = application as MainApp

    btnAdd.setOnClickListener() {
      val placemark = PlacemarkModel()
      placemark.title = placemarkTitle.text.toString()
      placemark.description = description.text.toString()

      if (placemark.title.isNotEmpty()) {
        app.placemarks.add(placemark.copy())
        info("Placemark added with title: ${placemark.title}, description: ${placemark.description}")
        app.placemarks.forEach { info("Placemark added: ${it}") }

        setResult(AppCompatActivity.RESULT_OK)
        finish()
      }
      else {
        toast ("Please enter a valid title")
      }
    }
  }
}

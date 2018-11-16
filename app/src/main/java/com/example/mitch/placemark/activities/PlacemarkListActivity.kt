package com.example.mitch.placemark.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.mitch.placemark.R
import com.example.mitch.placemark.adapters.PlacemarkAdapter
import com.example.mitch.placemark.adapters.PlacemarkListener
import com.example.mitch.placemark.models.PlacemarkModel
import kotlinx.android.synthetic.main.activity_placemark_list.*

class PlacemarkListActivity : AppCompatActivity(), PlacemarkListener {
  lateinit var presenter: PlacemarkListPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_placemark_list)
    toolbarMain.title = title
    setSupportActionBar(toolbarMain)

    presenter = PlacemarkListPresenter(this)

    val layoutManager = LinearLayoutManager(this)
    recyclerView.layoutManager = layoutManager
    loadPlacemarks()
  }

  override fun onPlacemarkClick(placemark: PlacemarkModel) {
    presenter.doEditPlacemark(placemark)
  }

  private fun loadPlacemarks() {
    showPlacemarks(presenter.getPlacemarks())
  }

  fun showPlacemarks(placemarks: List<PlacemarkModel>) {
    recyclerView.adapter = PlacemarkAdapter(placemarks, this)
    recyclerView.adapter?.notifyDataSetChanged()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.item_add -> presenter.doAddPlacemark()
      R.id.item_map -> presenter.doShowPlacemarksMap()
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    loadPlacemarks()
    super.onActivityResult(requestCode, resultCode, data)
  }
}
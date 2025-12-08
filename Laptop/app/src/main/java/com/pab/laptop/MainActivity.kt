package com.pab.laptop

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvLaptops: RecyclerView
    private val list = ArrayList<Laptop>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        rvLaptops = findViewById(R.id.rv_laptops)
        rvLaptops.setHasFixedSize(true)

        list.addAll(getListLaptops())
        showRecyclerList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_page -> {
                val intent = Intent(this, AboutActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getListLaptops(): ArrayList<Laptop> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val dataSpecProcessor = resources.getStringArray(R.array.data_spec_processor)
        val dataSpecRam = resources.getStringArray(R.array.data_spec_ram)
        val dataSpecGpu = resources.getStringArray(R.array.data_spec_gpu)
        val dataSpecStorage = resources.getStringArray(R.array.data_spec_storage)
        val dataSpecDisplay = resources.getStringArray(R.array.data_spec_display)

        val listLaptop = ArrayList<Laptop>()
        for (i in dataName.indices) {
            val laptop = Laptop(
                dataName[i],
                dataDescription[i],
                dataPhoto.getResourceId(i, -1),
                dataSpecProcessor[i],
                dataSpecRam[i],
                dataSpecGpu[i],
                dataSpecStorage[i],
                dataSpecDisplay[i]
            )
            listLaptop.add(laptop)
        }
        dataPhoto.recycle()
        return listLaptop
    }

    private fun showRecyclerList() {
        rvLaptops.layoutManager = LinearLayoutManager(this)
        val listLaptopAdapter = ListLaptopAdapter(list)
        rvLaptops.adapter = listLaptopAdapter

        listLaptopAdapter.setOnItemClickCallback(object : ListLaptopAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Laptop) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra("key_laptop", data)
                startActivity(intentToDetail)
            }
        })
    }
}
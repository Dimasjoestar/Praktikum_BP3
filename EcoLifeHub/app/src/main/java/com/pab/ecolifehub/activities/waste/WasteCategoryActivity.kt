package com.pab.ecolifehub.activities.waste

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pab.ecolifehub.R
import com.pab.ecolifehub.adapters.WasteCategoryAdapter
import com.pab.ecolifehub.data.DataSource
import com.pab.ecolifehub.models.WasteCategory

class WasteCategoryActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var rvWasteCategories: RecyclerView
    private lateinit var wasteCategoryAdapter: WasteCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_waste_category)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupRecyclerView()
        setupListeners()
    }

    private fun initViews() {
        ivBack = findViewById(R.id.ivBack)
        rvWasteCategories = findViewById(R.id.rvWasteCategories)
    }

    private fun setupRecyclerView() {
        val categories = DataSource.getWasteCategories()

        wasteCategoryAdapter = WasteCategoryAdapter(categories) { category ->
            handleCategoryClick(category)
        }

        rvWasteCategories.apply {
            layoutManager = LinearLayoutManager(this@WasteCategoryActivity)
            adapter = wasteCategoryAdapter
        }
    }

    private fun handleCategoryClick(category: WasteCategory) {
        val intent = when (category.id) {
            1 -> Intent(this, PlasticWasteActivity::class.java)
            2 -> Intent(this, OrganicWasteActivity::class.java)
            3 -> Intent(this, ElectronicWasteActivity::class.java)
            4 -> Intent(this, PaperWasteActivity::class.java)
            5 -> Intent(this, B3WasteActivity::class.java)
            else -> return
        }
        startActivity(intent)
    }

    private fun setupListeners() {
        ivBack.setOnClickListener {
            finish()
        }
    }
}

package com.pab.ecolifehub.activities.lifestyle

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pab.ecolifehub.R
import com.pab.ecolifehub.adapters.GreenTipsAdapter
import com.pab.ecolifehub.data.DataSource

class GreenTipsActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var rvGreenTips: RecyclerView
    private lateinit var greenTipsAdapter: GreenTipsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_green_tips)
        
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
        rvGreenTips = findViewById(R.id.rvGreenTips)
    }

    private fun setupRecyclerView() {
        val tips = DataSource.getGreenTips()

        greenTipsAdapter = GreenTipsAdapter(tips)

        rvGreenTips.apply {
            layoutManager = LinearLayoutManager(this@GreenTipsActivity)
            adapter = greenTipsAdapter
        }
    }

    private fun setupListeners() {
        ivBack.setOnClickListener {
            finish()
        }
    }
}

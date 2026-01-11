package com.pab.ecolifehub.activities.waste

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pab.ecolifehub.R
import com.pab.ecolifehub.data.DataSource

class PaperWasteActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var ivWasteIcon: ImageView
    private lateinit var tvDescription: TextView
    private lateinit var tvImpact: TextView
    private lateinit var llTips: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_waste_detail)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        loadData()
        setupListeners()
    }

    private fun initViews() {
        ivBack = findViewById(R.id.ivBack)
        tvTitle = findViewById(R.id.tvTitle)
        ivWasteIcon = findViewById(R.id.ivWasteIcon)
        tvDescription = findViewById(R.id.tvDescription)
        tvImpact = findViewById(R.id.tvImpact)
        llTips = findViewById(R.id.llTips)
    }

    private fun loadData() {
        val wasteDetail = DataSource.getPaperWasteDetail()

        tvTitle.text = wasteDetail.name
        ivWasteIcon.setImageResource(wasteDetail.imageResId)
        tvDescription.text = wasteDetail.description
        tvImpact.text = wasteDetail.impact

        // Add tips dynamically
        wasteDetail.tips.forEachIndexed { index, tip ->
            val tipView = createTipView(index + 1, tip)
            llTips.addView(tipView)
        }
    }

    private fun createTipView(number: Int, tip: String): LinearLayout {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            setPadding(0, 8, 0, 8)
        }

        val numberView = TextView(this).apply {
            text = "$number."
            setTextColor(getColor(R.color.primary))
            textSize = 14f
            setPadding(0, 0, 16, 0)
        }

        val tipTextView = TextView(this).apply {
            text = tip
            setTextColor(getColor(R.color.text_secondary))
            textSize = 14f
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }

        layout.addView(numberView)
        layout.addView(tipTextView)

        return layout
    }

    private fun setupListeners() {
        ivBack.setOnClickListener {
            finish()
        }
    }
}

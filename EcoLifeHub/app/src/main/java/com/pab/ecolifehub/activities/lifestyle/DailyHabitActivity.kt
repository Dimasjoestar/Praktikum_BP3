package com.pab.ecolifehub.activities.lifestyle

import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pab.ecolifehub.R
import com.pab.ecolifehub.adapters.DailyHabitAdapter
import com.pab.ecolifehub.data.DataSource
import com.pab.ecolifehub.models.DailyHabit

class DailyHabitActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var tvProgress: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var rvDailyHabits: RecyclerView
    private lateinit var dailyHabitAdapter: DailyHabitAdapter

    private val habits = mutableListOf<DailyHabit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daily_habit)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViews()
        setupRecyclerView()
        setupListeners()
        updateProgress()
    }

    private fun initViews() {
        ivBack = findViewById(R.id.ivBack)
        tvProgress = findViewById(R.id.tvProgress)
        progressBar = findViewById(R.id.progressBar)
        rvDailyHabits = findViewById(R.id.rvDailyHabits)
    }

    private fun setupRecyclerView() {
        habits.clear()
        habits.addAll(DataSource.getDailyHabits())

        dailyHabitAdapter = DailyHabitAdapter(habits) { habit, isChecked ->
            updateProgress()
        }

        rvDailyHabits.apply {
            layoutManager = LinearLayoutManager(this@DailyHabitActivity)
            adapter = dailyHabitAdapter
        }
    }

    private fun updateProgress() {
        val completed = habits.count { it.isCompleted }
        val total = habits.size
        val percentage = if (total > 0) (completed * 100) / total else 0

        tvProgress.text = "$completed/$total"
        progressBar.progress = percentage
    }

    private fun setupListeners() {
        ivBack.setOnClickListener {
            finish()
        }
    }
}

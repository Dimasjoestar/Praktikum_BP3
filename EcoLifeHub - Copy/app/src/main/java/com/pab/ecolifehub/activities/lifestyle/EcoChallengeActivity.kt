package com.pab.ecolifehub.activities.lifestyle

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
import com.pab.ecolifehub.adapters.EcoChallengeAdapter
import com.pab.ecolifehub.data.DataSource
import com.pab.ecolifehub.models.EcoChallenge

class EcoChallengeActivity : AppCompatActivity() {

    private lateinit var ivBack: ImageView
    private lateinit var rvEcoChallenges: RecyclerView
    private lateinit var ecoChallengeAdapter: EcoChallengeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_eco_challenge)
        
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
        rvEcoChallenges = findViewById(R.id.rvEcoChallenges)
    }

    private fun setupRecyclerView() {
        val challenges = DataSource.getEcoChallenges()

        ecoChallengeAdapter = EcoChallengeAdapter(challenges) { challenge ->
            navigateToChallengeDetail(challenge)
        }

        rvEcoChallenges.apply {
            layoutManager = LinearLayoutManager(this@EcoChallengeActivity)
            adapter = ecoChallengeAdapter
        }
    }

    private fun navigateToChallengeDetail(challenge: EcoChallenge) {
        val intent = Intent(this, ChallengeDetailActivity::class.java).apply {
            putExtra("CHALLENGE_ID", challenge.id)
            putExtra("CHALLENGE_TITLE", challenge.title)
            putExtra("CHALLENGE_DESCRIPTION", challenge.description)
            putExtra("CHALLENGE_BENEFIT", challenge.benefit)
            putExtra("CHALLENGE_DIFFICULTY", challenge.difficulty)
            putExtra("CHALLENGE_ICON", challenge.iconResId)
        }
        startActivity(intent)
    }

    private fun setupListeners() {
        ivBack.setOnClickListener {
            finish()
        }
    }
}

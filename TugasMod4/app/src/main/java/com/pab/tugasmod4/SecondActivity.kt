package com.pab.tugasmod4

import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private lateinit var tvDataReceived: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        tvDataReceived = findViewById(R.id.tv_data_received)

        val person = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_PERSON, Person::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_PERSON)
        }

        if (person != null) {
            val text = "Nama: ${person.name}\nNIM: ${person.nim}\nEmail: ${person.email}\nUmur: ${person.age}\nKota: ${person.city}"
            tvDataReceived.text = text
        }
    }

    companion object {
        const val EXTRA_PERSON = "extra_person"
    }
}
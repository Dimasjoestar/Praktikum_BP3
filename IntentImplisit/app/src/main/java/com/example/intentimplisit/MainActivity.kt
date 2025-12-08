package com.example.intentimplisit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnBukaBrowser = findViewById<Button>(R.id.btn_buka_browser)
        val btnBukaTelepon = findViewById<Button>(R.id.btn_buka_telepon)
        val btnSendEmail = findViewById<Button>(R.id.btn_send_email)

        val edtUrl = findViewById<EditText>(R.id.edt_url)
        val edtTelepon = findViewById<EditText>(R.id.edt_telepon)
        val edtEmail = findViewById<EditText>(R.id.edt_email)
        val edtSubject = findViewById<EditText>(R.id.edt_subject)
        val edtIsiEmail = findViewById<EditText>(R.id.edt_isi_email)

        btnBukaBrowser.setOnClickListener {
            var url = edtUrl.text.toString()
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://$url"
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        btnSendEmail.setOnClickListener {
            val email = edtEmail.text.toString()
            val subject = edtSubject.text.toString()
            val message = edtIsiEmail.text.toString()

            if (email.isNotEmpty()) {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:")
                    putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                    putExtra(Intent.EXTRA_SUBJECT, subject)
                    putExtra(Intent.EXTRA_TEXT, message)
                }
                startActivity(intent)
            }
        }

        btnBukaTelepon.setOnClickListener {
            val nomorTelepon = edtTelepon.text.toString()
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$nomorTelepon")
            }
            startActivity(intent)
        }
    }
}

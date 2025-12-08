package com.example.intentparcelable

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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

        // Membuat objek Mahasiswa
        val mahasiswa = Mahasiswa(
            "Dimas",
            "dimasnurdiansyah912@gmail.com",
            "20230810087",
            21,
            "Kuningan"
        )

        // Ambil tombol dari layout
        val btnPindahDenganObjek = findViewById<Button>(R.id.btn_pindah_objek)

        // Set onClickListener agar pindah Activity saat tombol diklik
        btnPindahDenganObjek.setOnClickListener {
            val intentPindahDenganObjek = Intent(this, PindahDenganObjek::class.java)
            intentPindahDenganObjek.putExtra(PindahDenganObjek.EXTRA_MAHASISWA, mahasiswa)
            startActivity(intentPindahDenganObjek)
        }
    }
}

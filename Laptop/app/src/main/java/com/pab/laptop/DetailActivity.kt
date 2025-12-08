package com.pab.laptop

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar: Toolbar = findViewById(R.id.toolbar_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        val laptop = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("key_laptop", Laptop::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("key_laptop")
        }

        if (laptop != null) {
            // Bind data to views
            val ivDetailPhoto: ImageView = findViewById(R.id.img_laptop_photo)
            val tvDetailName: TextView = findViewById(R.id.tv_laptop_name)
            val tvDetailDescription: TextView = findViewById(R.id.tv_laptop_description)

            ivDetailPhoto.setImageResource(laptop.photo)
            tvDetailName.text = laptop.name
            tvDetailDescription.text = laptop.description

            // Setup spec items
            setupSpecItem(findViewById(R.id.spec_processor_layout), R.drawable.ic_processor, "Prosesor", laptop.spec_processor)
            setupSpecItem(findViewById(R.id.spec_ram_layout), R.drawable.ic_ram, "RAM", laptop.spec_ram)
            setupSpecItem(findViewById(R.id.spec_gpu_layout), R.drawable.ic_gpu, "GPU", laptop.spec_gpu)
            setupSpecItem(findViewById(R.id.spec_storage_layout), R.drawable.ic_storage, "Penyimpanan", laptop.spec_storage)
            setupSpecItem(findViewById(R.id.spec_display_layout), R.drawable.ic_display, "Layar", laptop.spec_display)

            // Share button
            val btnShare: Button = findViewById(R.id.btn_share)
            btnShare.setOnClickListener {
                val shareText = "Lihat spesifikasi ${laptop.name}:\n"
                    .plus("Prosesor: ${laptop.spec_processor}\n")
                    .plus("RAM: ${laptop.spec_ram}\n")
                    .plus("GPU: ${laptop.spec_gpu}\n")
                    .plus("Penyimpanan: ${laptop.spec_storage}\n")
                    .plus("Layar: ${laptop.spec_display}\n\n")
                    .plus(laptop.description)

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, "Bagikan spesifikasi ${laptop.name}")
                startActivity(shareIntent)
            }
        }
    }

    private fun setupSpecItem(view: View, iconRes: Int, title: String, value: String) {
        val icon: ImageView = view.findViewById(R.id.iv_spec_icon)
        val tvTitle: TextView = view.findViewById(R.id.tv_spec_title)
        val tvValue: TextView = view.findViewById(R.id.tv_spec_value)

        icon.setImageResource(iconRes)
        tvTitle.text = title
        tvValue.text = value
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
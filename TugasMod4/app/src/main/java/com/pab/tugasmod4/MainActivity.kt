package com.pab.tugasmod4

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnMoveWithObject: Button = findViewById(R.id.btn_move_with_object)

        btnMoveWithObject.setOnClickListener {
            val person = Person(
                name = "Dimas Nurdiansyah",
                nim = "20230810087",
                email = "20230810087@uniku.ac.id",
                age = 21,
                city = "Kuningan"
            )

            val moveWithObjectIntent = Intent(this@MainActivity, SecondActivity::class.java)
            moveWithObjectIntent.putExtra(SecondActivity.EXTRA_PERSON, person)
            startActivity(moveWithObjectIntent)
        }
    }
}
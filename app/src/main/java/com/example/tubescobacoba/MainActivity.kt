package com.example.tubescobacoba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    private lateinit var cashierButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cashierButton = findViewById(R.id.imageButton)
        cashierButton.setOnClickListener {
            val intent = Intent(this, CariItem::class.java)
            startActivity(intent)
        }
    }
}
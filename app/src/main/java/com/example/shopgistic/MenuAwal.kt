package com.example.shopgistic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MenuAwal : AppCompatActivity() {

    private lateinit var cashierButton: ImageButton
    private lateinit var goodsButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_awal)

        cashierButton = findViewById(R.id.imageButton)
        cashierButton.setOnClickListener {
            val intent = Intent(this, CariItem::class.java)
            startActivity(intent)
        }
        goodsButton = findViewById(R.id.imageButton2)
        goodsButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
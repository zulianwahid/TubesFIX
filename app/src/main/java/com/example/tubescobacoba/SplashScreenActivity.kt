package com.example.tubescobacoba

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this@SplashScreenActivity,
                MainActivity::class.java)
            startActivity(intent)
            finish()
        },5000)
    }
}
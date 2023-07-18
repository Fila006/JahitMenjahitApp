package com.example.jahitmenjahit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()
        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(this, LoginMainActivity1::class.java)
            startActivity(intent)
        }, 5000)
    }
}
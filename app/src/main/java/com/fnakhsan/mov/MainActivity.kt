package com.fnakhsan.mov

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.fnakhsan.mov.onboard.OnboardOneActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@MainActivity, OnboardOneActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
package com.fnakhsan.mov

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.fnakhsan.mov.dashboard.DashboardActivity
import com.fnakhsan.mov.onboard.OnBoardOneActivity
import com.fnakhsan.mov.sign.`in`.SignInActivity
import com.fnakhsan.mov.utils.Preferences


class MainActivity : AppCompatActivity() {
    private lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = Preferences(this)
        when(preferences.getValues("status")){
            "1" -> {
                val intent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
            "2" -> {
                val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> {
                val intent = Intent(this@MainActivity, OnBoardOneActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
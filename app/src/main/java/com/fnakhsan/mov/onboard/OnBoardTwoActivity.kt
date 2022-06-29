package com.fnakhsan.mov.onboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fnakhsan.mov.databinding.ActivityOnBoardTwoBinding

class OnBoardTwoActivity : AppCompatActivity() {
    private lateinit var onBoardTwoBinding: ActivityOnBoardTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBoardTwoBinding = ActivityOnBoardTwoBinding.inflate(layoutInflater)
        setContentView(onBoardTwoBinding.root)

        onBoardTwoBinding.button2.setOnClickListener {
            val intent = Intent(this@OnBoardTwoActivity, OnBoardThreeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
package com.fnakhsan.mov.onboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fnakhsan.mov.sign.`in`.SignInActivity
import com.fnakhsan.mov.databinding.ActivityOnBoardThreeBinding

class OnBoardThreeActivity : AppCompatActivity() {
    private lateinit var onBoardThreeBinding: ActivityOnBoardThreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBoardThreeBinding = ActivityOnBoardThreeBinding.inflate(layoutInflater)
        setContentView(onBoardThreeBinding.root)

        onBoardThreeBinding.btnStart.setOnClickListener {
            finishAffinity()
            val intent = Intent(this@OnBoardThreeActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
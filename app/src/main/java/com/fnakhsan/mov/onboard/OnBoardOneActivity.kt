package com.fnakhsan.mov.onboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fnakhsan.mov.SignInActivity
import com.fnakhsan.mov.databinding.ActivityOnBoardOneBinding

class OnBoardOneActivity : AppCompatActivity() {
    private lateinit var onBoardOneBinding: ActivityOnBoardOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBoardOneBinding = ActivityOnBoardOneBinding.inflate(layoutInflater)
        setContentView(onBoardOneBinding.root)

        onBoardOneBinding.btnResume.setOnClickListener {
            val intent = Intent(this@OnBoardOneActivity, OnBoardTwoActivity::class.java)
            startActivity(intent)
        }

        onBoardOneBinding.btnSkipIntro.setOnClickListener {
            finishAffinity()
            val intent = Intent(this@OnBoardOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
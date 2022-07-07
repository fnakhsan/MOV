package com.fnakhsan.mov.onboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fnakhsan.mov.sign.`in`.SignInActivity
import com.fnakhsan.mov.databinding.ActivityOnBoardOneBinding
import com.fnakhsan.mov.utils.Preferences

class OnBoardOneActivity : AppCompatActivity() {
    private lateinit var onBoardOneBinding: ActivityOnBoardOneBinding
    private lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBoardOneBinding = ActivityOnBoardOneBinding.inflate(layoutInflater)
        setContentView(onBoardOneBinding.root)

        preferences = Preferences(this)
        if (preferences.getValues("onBoarding") == "1"){
            finishAffinity()
            val intent = Intent(this@OnBoardOneActivity, SignInActivity::class.java)
            startActivity(intent)
        }
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
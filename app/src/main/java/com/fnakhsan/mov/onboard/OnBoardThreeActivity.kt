package com.fnakhsan.mov.onboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fnakhsan.mov.databinding.ActivityOnBoardThreeBinding

class OnBoardThreeActivity : AppCompatActivity() {
    private lateinit var onBoardThreeBinding: ActivityOnBoardThreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBoardThreeBinding = ActivityOnBoardThreeBinding.inflate(layoutInflater)
        setContentView(onBoardThreeBinding.root)
    }
}
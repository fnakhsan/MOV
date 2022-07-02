package com.fnakhsan.mov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fnakhsan.mov.databinding.ActivityChooseSeatBinding

class ChooseSeatActivity : AppCompatActivity() {
    private lateinit var chooseSeatBinding: ActivityChooseSeatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseSeatBinding = ActivityChooseSeatBinding.inflate(layoutInflater)
        setContentView(chooseSeatBinding.root)
    }
}
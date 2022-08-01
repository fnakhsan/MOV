package com.fnakhsan.mov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fnakhsan.mov.data.Film
import com.fnakhsan.mov.databinding.ActivityChooseSeatBinding

class ChooseSeatActivity : AppCompatActivity() {
    private lateinit var chooseSeatBinding: ActivityChooseSeatBinding
    private var total: Int = 0
    private lateinit var seats : MutableList<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseSeatBinding = ActivityChooseSeatBinding.inflate(layoutInflater)
        setContentView(chooseSeatBinding.root)

        intent.getParcelableExtra<Film>("data")
        seatInit()
    }

    fun seatInit(){
        for(i in 0..29){
            seats[i] = false
        }
    }
}
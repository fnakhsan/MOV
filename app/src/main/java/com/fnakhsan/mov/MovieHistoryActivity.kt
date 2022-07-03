package com.fnakhsan.mov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fnakhsan.mov.databinding.ActivityMovieHistoryBinding

class MovieHistoryActivity : AppCompatActivity() {
    private lateinit var movieHistoryBinding: ActivityMovieHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieHistoryBinding = ActivityMovieHistoryBinding.inflate(layoutInflater)
        setContentView(movieHistoryBinding.root)
    }
}
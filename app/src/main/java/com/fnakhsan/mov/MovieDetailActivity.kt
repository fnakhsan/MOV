package com.fnakhsan.mov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fnakhsan.mov.data.Film
import com.fnakhsan.mov.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var movieDetailBinding: ActivityMovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieDetailBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(movieDetailBinding.root)

        val data = intent.getParcelableExtra<Film>("film")

        with(movieDetailBinding){
            tvTitle.text = data?.genre
            tvGenre.text = data?.genre
            tvRate.text = data?.rating
            Glide.with(this@MovieDetailActivity)
                .load(data?.poster)
                .apply(RequestOptions.centerCropTransform())
                .into(ivCover)
        }
    }
}
package com.fnakhsan.mov.dashboard.home.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fnakhsan.mov.data.Film
import com.fnakhsan.mov.data.Play
import com.fnakhsan.mov.databinding.ActivityMovieDetailBinding
import com.google.firebase.database.*

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var movieDetailBinding: ActivityMovieDetailBinding
    private lateinit var play: ArrayList<Play>
    private lateinit var mPlaysRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieDetailBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(movieDetailBinding.root)

        val data = intent.getParcelableExtra<Film>("film")
        mPlaysRef =
            FirebaseDatabase.getInstance("https://bwa-mov-fbe4b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Film").child(data?.judul.toString()).child("play")

        with(movieDetailBinding) {
            tvTitle.text = data?.genre
            tvGenre.text = data?.genre
            tvRate.text = data?.rating
            Glide.with(this@MovieDetailActivity)
                .load(data?.poster)
                .apply(RequestOptions.centerCropTransform())
                .into(ivCover)
            rvWp.layoutManager =
                LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        getData()

    }

    private fun getData() {
        mPlaysRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                play.clear()
                for (data in snapshot.children){

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
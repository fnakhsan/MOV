package com.fnakhsan.mov.dashboard.home.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fnakhsan.mov.checkout.ChooseSeatActivity
import com.fnakhsan.mov.data.Film
import com.fnakhsan.mov.data.Play
import com.fnakhsan.mov.databinding.ActivityMovieDetailBinding
import com.google.firebase.database.*

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var movieDetailBinding: ActivityMovieDetailBinding
    private var players = ArrayList<Play>()
    private lateinit var mPlaysRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieDetailBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(movieDetailBinding.root)

        val data = intent.getParcelableExtra<Film>("film")
        Log.d(TAG, "$data")
        mPlaysRef =
            FirebaseDatabase.getInstance("https://bwa-mov-fbe4b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Film").child(data?.judul.toString()).child("play")
        Log.d(TAG, "first")

        movieDetailBinding.apply {
            Log.d(TAG, "title: ${data?.judul}")
            tvTitle.text = data?.judul
            Log.d(TAG, "genre: ${data?.genre}")
            tvGenre.text = data?.genre
            Log.d(TAG, "rate: ${data?.rating}")
            tvRate.text = data?.rating
            Log.d(TAG, "url poster: ${data?.poster}")
            Glide.with(this@MovieDetailActivity)
                .load(data?.poster)
                .apply(RequestOptions.centerCropTransform())
                .into(ivCover)
            val wpLayoutManager = LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            rvWp.layoutManager = wpLayoutManager
            rvWp.addItemDecoration(DividerItemDecoration(this@MovieDetailActivity, wpLayoutManager.orientation))
            Log.d(TAG, "second")
            btnSeat.setOnClickListener {
                val intent = Intent(
                    this@MovieDetailActivity,
                    ChooseSeatActivity::class.java
                ).putExtra("data", data)
                startActivity(intent)
            }
        }
        Log.d(TAG, "third")

        getData()
    }

    private fun getData() {
        mPlaysRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                players.clear()
                for (data in snapshot.children) {
                    val player = data.getValue(Play::class.java)
                    players.add(player!!)
                }
                movieDetailBinding.rvWp.adapter = PlayerAdapter(players)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MovieDetailActivity, error.message, Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }

    companion object {
        private const val TAG = "detail"
    }
}
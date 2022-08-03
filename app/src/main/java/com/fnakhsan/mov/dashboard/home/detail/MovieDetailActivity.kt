package com.fnakhsan.mov.dashboard.home.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
    private lateinit var players: ArrayList<Play>
    private lateinit var mPlaysRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieDetailBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(movieDetailBinding.root)

        val data = intent.getParcelableExtra<Film>("film")
        mPlaysRef =
            FirebaseDatabase.getInstance("https://bwa-mov-fbe4b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Film").child(data?.judul.toString()).child("play")

        movieDetailBinding.apply {
            tvTitle.text = data?.judul
            tvGenre.text = data?.genre
            tvRate.text = data?.rating
            Glide.with(this@MovieDetailActivity)
                .load(data?.poster)
                .apply(RequestOptions.centerCropTransform())
                .into(ivCover)
            rvWp.layoutManager =
                LinearLayoutManager(this@MovieDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            btnSeat.setOnClickListener {
                val intent = Intent(
                    this@MovieDetailActivity,
                    ChooseSeatActivity::class.java
                ).putExtra("data", data)
                startActivity(intent)
            }
        }

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
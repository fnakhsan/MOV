package com.fnakhsan.mov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.fnakhsan.mov.data.Checkout
import com.fnakhsan.mov.data.Film
import com.fnakhsan.mov.databinding.ActivityChooseSeatBinding
import com.fnakhsan.mov.sign.up.SignUpActivity
import com.google.firebase.database.*

class ChooseSeatActivity : AppCompatActivity() {
    private lateinit var chooseSeatBinding: ActivityChooseSeatBinding
    private var total: Int = 0
    private lateinit var seats: MutableList<Boolean>
    private lateinit var checkout: MutableList<Checkout>
    private lateinit var seatList: MutableMap<Int, String>
    private lateinit var mFilmDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseSeatBinding = ActivityChooseSeatBinding.inflate(layoutInflater)
        setContentView(chooseSeatBinding.root)

        seatInit()
        val data = intent.getParcelableExtra<Film>("data")
        mFilmDatabase =
            FirebaseDatabase.getInstance("https://bwa-mov-fbe4b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Play").child("${data?.judul}")


        mFilmDatabase.child("seats").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {
                    var number = 0
                    checkout[number].seat = "${i.key}"
                    checkout[number].status = "${i.value}"
                    checkout[number].price = 40000

//                    seatList[number] = "${i.value}"
                    number += 1
                    chooseSeatBinding.A1
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChooseSeatActivity, error.message, Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        chooseSeatBinding.apply {
            movieTitle.text = data?.judul

            val a1 = A1


//            mFilmDatabase.child("seats").get().addOnCompleteListener {
//                for (i in ){
//
//                }
//                checkout[].kursi
//
//            }
        }
    }

    private fun seatInit() {
        for (i in 0..29) {
            seats[i] = false
        }
    }

    private fun seatStatus() {
        when (checkout[0].status) {
            "0" -> {
                chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_empty)
            }

            "1" -> chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_booked)
        }
        when (checkout[1].status) {
            "0" -> {
                chooseSeatBinding.A2.background = getDrawable(R.drawable.shape_rect_empty)
            }

            "1" -> chooseSeatBinding.A2.background = getDrawable(R.drawable.shape_rect_booked)
        }
        when (checkout[2].status) {
            "0" -> {

                chooseSeatBinding.A2.background = getDrawable(R.drawable.shape_rect_empty)
            }

            "1" -> chooseSeatBinding.A2.background = getDrawable(R.drawable.shape_rect_booked)
        }
        when (checkout[0].status) {
            "0" -> {
                chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_empty)
            }

            "1" -> chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_booked)
        }
        when (checkout[0].status) {
            "0" -> {
                chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_empty)
            }

            "1" -> chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_booked)
        }
        when (checkout[0].status) {
            "0" -> {
                chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_empty)
            }

            "1" -> chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_booked)
        }
        when (checkout[0].status) {
            "0" -> {
                chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_empty)
            }

            "1" -> chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_booked)
        }
        when (checkout[0].status) {
            "0" -> {
                chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_empty)
            }

            "1" -> chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_booked)
        }
        when (checkout[0].status) {
            "0" -> {
                chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_empty)
            }

            "1" -> chooseSeatBinding.A1.background = getDrawable(R.drawable.shape_rect_booked)
        }


    }

    companion object {
        private const val TAG = "checkout"
    }
}
package com.fnakhsan.mov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.fnakhsan.mov.R
import com.fnakhsan.mov.data.Checkout
import com.fnakhsan.mov.data.Film
import com.fnakhsan.mov.databinding.ActivityChooseSeatBinding
import com.google.firebase.database.*

class ChooseSeatActivity : AppCompatActivity() {
    private lateinit var chooseSeatBinding: ActivityChooseSeatBinding
    private var statusA1: Boolean = false
    private var total: Int = 0

    private var dataList = ArrayList<Checkout>()

//    private lateinit var seats: MutableList<Boolean>
//    private lateinit var checkout: MutableList<Checkout>
//    private lateinit var seatList: MutableMap<Int, String>
    private lateinit var mFilmDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chooseSeatBinding = ActivityChooseSeatBinding.inflate(layoutInflater)
        setContentView(chooseSeatBinding.root)

//      seatInit()
        val data = intent.getParcelableExtra<Film>("data")
        mFilmDatabase =
            FirebaseDatabase.getInstance("https://bwa-mov-fbe4b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Play").child("${data?.judul}")

        chooseSeatBinding.A1.apply {
            setOnClickListener {
                Log.d(TAG, "$statusA1")
                if (statusA1) {
                    background = AppCompatResources.getDrawable(
                        this@ChooseSeatActivity,
                        R.drawable.shape_rect_empty
                    )
                    statusA1 = false
                    Log.d(TAG, "second: $statusA1")
                    total -= 1
                    totalBuy(total)
                } else {
                    background = AppCompatResources.getDrawable(
                        this@ChooseSeatActivity,
                        R.drawable.shape_rect_selected
                    )
                    Log.d(TAG, "third: $statusA1")
                    statusA1 = true
                    Log.d(TAG, "fourth: $statusA1")
                    total += 1
                    totalBuy(total)

                    val a1 = Checkout("A1", "1", 40000)
                    Log.d(TAG, "fifth: $dataList")
                    dataList.clear()
                    Log.d(TAG, "sixth: $dataList")
                    dataList.add(a1)
                    Log.d(TAG, "seventh: $dataList")
                }
            }
        }

        chooseSeatBinding.btnBuy.setOnClickListener {
            val intent = Intent(this, CheckoutActivity::class.java).putExtra("data", dataList)
            startActivity(intent)
        }


//        mFilmDatabase.child("seats").addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                for (i in snapshot.children) {
//                    var number = 0
//                    checkout[number].seat = "${i.key}"
//                    checkout[number].status = "${i.value}"
//                    checkout[number].price = 40000
//
//                    seatStatus(chooseSeatBinding.A1, checkout[number].status)
//                    seatStatus(chooseSeatBinding.A2, checkout[number].status)
//                    seatStatus(chooseSeatBinding.A3, checkout[number].status)
//                    seatStatus(chooseSeatBinding.A4, checkout[number].status)
//                    seatStatus(chooseSeatBinding.A5, checkout[number].status)
//                    seatStatus(chooseSeatBinding.A6, checkout[number].status)
//                    seatStatus(chooseSeatBinding.B1, checkout[number].status)
//                    seatStatus(chooseSeatBinding.B2, checkout[number].status)
//                    seatStatus(chooseSeatBinding.B3, checkout[number].status)
//                    seatStatus(chooseSeatBinding.B4, checkout[number].status)
//                    seatStatus(chooseSeatBinding.B5, checkout[number].status)
//                    seatStatus(chooseSeatBinding.B6, checkout[number].status)
//                    seatStatus(chooseSeatBinding.C1, checkout[number].status)
//                    seatStatus(chooseSeatBinding.C2, checkout[number].status)
//                    seatStatus(chooseSeatBinding.C3, checkout[number].status)
//                    seatStatus(chooseSeatBinding.C4, checkout[number].status)
//                    seatStatus(chooseSeatBinding.C5, checkout[number].status)
//                    seatStatus(chooseSeatBinding.C6, checkout[number].status)
//                    seatStatus(chooseSeatBinding.D1, checkout[number].status)
//                    seatStatus(chooseSeatBinding.D2, checkout[number].status)
//                    seatStatus(chooseSeatBinding.D3, checkout[number].status)
//                    seatStatus(chooseSeatBinding.D4, checkout[number].status)
//                    seatStatus(chooseSeatBinding.D5, checkout[number].status)
//                    seatStatus(chooseSeatBinding.D6, checkout[number].status)
//                    seatStatus(chooseSeatBinding.E1, checkout[number].status)
//                    seatStatus(chooseSeatBinding.E2, checkout[number].status)
//                    seatStatus(chooseSeatBinding.E3, checkout[number].status)
//                    seatStatus(chooseSeatBinding.E4, checkout[number].status)
//                    seatStatus(chooseSeatBinding.E5, checkout[number].status)
//                    seatStatus(chooseSeatBinding.E6, checkout[number].status)
////                    seatList[number] = "${i.value}"
//                    number += 1
//                    chooseSeatBinding.A1
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@ChooseSeatActivity, error.message, Toast.LENGTH_SHORT).show()
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//        })

        chooseSeatBinding.apply {
            movieTitle.text = data?.judul


//            mFilmDatabase.child("seats").get().addOnCompleteListener {
//                for (i in ){
//
//                }
//                checkout[].kursi
//
//            }
        }
    }

    private fun totalBuy(total: Int) {
        chooseSeatBinding.btnBuy.apply {
            if (total == 0) {
                text = getString(R.string.buy_ticket, total)
                visibility = View.INVISIBLE
            } else {
                text = getString(R.string.buy_ticket, total)
                visibility = View.VISIBLE
            }
        }

    }

//    private fun seatInit() {
//        for (i in 0..29) {
//            seats[i] = false
//        }
//    }

//    private fun seatStatus() {
//        val empty = getDrawable(R.drawable.shape_rect_empty)
//        val booked = getDrawable(R.drawable.shape_rect_booked)
//        chooseSeatBinding.apply {
//            when (checkout[0].status) {
//                "0" -> A1.background = empty
//                "1" -> A1.background = booked
//            }
//            when (checkout[1].status) {
//                "0" -> A2.background = empty
//                "1" -> A2.background = booked
//            }
//            when (checkout[2].status) {
//                "0" -> A3.background = empty
//                "1" -> A3.background = booked
//            }
//            when (checkout[3].status) {
//                "0" -> A3.background = empty
//                "1" -> A3.background = booked
//            }
//            when (checkout[4].status) {
//                "0" -> A4.background = empty
//                "1" -> A4.background = booked
//            }
//        }
//
//
//
//    }

//    private fun seatStatus(imageView: ImageView, status: String?){
//        when(status){
//            "0" -> {
//                imageView.background = getDrawable(R.drawable.shape_rect_empty)
//            }
//            "1" -> imageView.background = getDrawable(R.drawable.shape_rect_booked)
//        }
//    }
//
//    private fun seatChangeStatus(imageView: ImageView, status: String?) {
//        var result = status
//        imageView.setOnClickListener {
//            if (status == "1"){
//                Toast.makeText(this, "Maaf bangku ini tidak tersedia", Toast.LENGTH_SHORT).show()
//            } else if (imageView.isSelected) {
//                it.background = getDrawable(R.drawable.shape_rect_empty)
//                result = "0"
//            } else if (!imageView.isSelected){
//                it.background = getDrawable(R.drawable.shape_rect_selected)
//                result = "1"
//                total += 40000
//            }
//        }
//    }
//
//    private fun total(){
//
//    }

    companion object {
        private const val TAG = "seat"
    }
}
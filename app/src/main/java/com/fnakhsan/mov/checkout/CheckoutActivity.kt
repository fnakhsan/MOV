package com.fnakhsan.mov.checkout

import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.fnakhsan.mov.data.Checkout
import com.fnakhsan.mov.data.User
import com.fnakhsan.mov.databinding.ActivityCheckoutBinding
import com.fnakhsan.mov.utils.Preferences
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList

class CheckoutActivity : AppCompatActivity() {
    private lateinit var checkoutBinding: ActivityCheckoutBinding
    private lateinit var dataList: MutableList<Checkout>
    private var total: Int = 0
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkoutBinding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(checkoutBinding.root)

        Log.d(TAG, "first")

        preferences = Preferences(this)
        dataList = intent.getParcelableArrayListExtra<Checkout>("data") as ArrayList<Checkout>

        Log.d(TAG, dataList.toString())
        for (i in dataList.indices) {
            Log.d(TAG, "$dataList")
            Log.d(TAG, "${dataList[i].price ?: 0}")
            total += dataList[i].price ?: 0
        }

//        dataList.add(Checkout("A1", "2", total))
        checkoutBinding.rvItems.apply {
            layoutManager =
                LinearLayoutManager(this@CheckoutActivity, LinearLayoutManager.VERTICAL, false)
            adapter = CheckoutAdapter(dataList)
        }
        checkoutBinding.apply {
            val localID = Locale("in", "ID")
//            val format = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                NumberFormat.getCurrencyInstance(localID)
//            } else {
//                java.text.NumberFormat.getCurrencyInstance(localID)
//            }
            Log.d(TAG, "format init")
            val format = java.text.NumberFormat.getCurrencyInstance(localID).format(total)
            Log.d(TAG, format.toString())
            tvTotalPrice.text = format.toString()
            Log.d(TAG, "success")
            btnCancel.setOnClickListener {
                val intent = Intent(this@CheckoutActivity, ChooseSeatActivity::class.java)
                startActivity(intent)
            }
            Log.d(TAG, "balance init")
            Log.d(TAG, "${preferences.getValues("saldo")}")
            val balance = preferences.getValues("saldo")?.toInt() ?: 0
            Log.d(TAG, "$balance")
            when (balance >= total) {
                true -> {
                    btnBuyNow.apply {
                        visibility = View.VISIBLE
                        setOnClickListener {
                            val intent = Intent(this@CheckoutActivity, CheckoutSuccessActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
                false -> {
                    btnBuyNow.visibility = View.INVISIBLE
                }
            }
        }
    }

    companion object {
        private const val TAG = "checkout"
    }
}
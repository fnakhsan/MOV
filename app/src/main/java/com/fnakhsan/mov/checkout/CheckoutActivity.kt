package com.fnakhsan.mov.checkout

import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.fnakhsan.mov.data.Checkout
import com.fnakhsan.mov.databinding.ActivityCheckoutBinding
import com.fnakhsan.mov.utils.Preferences
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

        preferences = Preferences(this)
        dataList = intent.getParcelableArrayListExtra<Checkout>("data") as ArrayList<Checkout>

        for (i in dataList.indices) {
            Log.d(TAG, "$dataList")
            Log.d(TAG, "${dataList[i].price ?: 0}")
            total += dataList[i].price ?: 0
        }

        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)


//        dataList.add(Checkout("A1", "2", total))

        checkoutBinding.rvItems.apply {
            layoutManager =
                LinearLayoutManager(this@CheckoutActivity, LinearLayoutManager.VERTICAL, false)
            adapter = CheckoutAdapter(dataList)
        }
        checkoutBinding.apply {
            tvTotalPrice.text = format.format(total.toDouble())
        }
    }

    companion object {
        private const val TAG = "checkout"
    }
}
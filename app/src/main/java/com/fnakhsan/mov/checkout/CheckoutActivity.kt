package com.fnakhsan.mov.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fnakhsan.mov.data.Checkout
import com.fnakhsan.mov.databinding.ActivityCheckoutBinding
import com.fnakhsan.mov.utils.Preferences

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

        for (i in dataList.indices){

        }
    }
}
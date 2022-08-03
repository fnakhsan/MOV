package com.fnakhsan.mov.checkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fnakhsan.mov.databinding.ActivityCheckoutBinding

class CheckoutActivity : AppCompatActivity() {
    private lateinit var checkoutBinding: ActivityCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkoutBinding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(checkoutBinding.root)
    }
}
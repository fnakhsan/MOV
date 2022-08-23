package com.fnakhsan.mov.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fnakhsan.mov.dashboard.home.HomeFragment
import com.fnakhsan.mov.dashboard.ticket.TicketFragment
import com.fnakhsan.mov.databinding.ActivityCheckoutSuccessBinding

class CheckoutSuccessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnTicket.setOnClickListener {
                val intent = Intent(this@CheckoutSuccessActivity, TicketFragment::class.java)
                startActivity(intent)
            }
            btnHome.setOnClickListener {
                val intent = Intent(this@CheckoutSuccessActivity, HomeFragment::class.java)
                startActivity(intent)
            }
        }
    }
}
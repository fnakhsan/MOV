package com.fnakhsan.mov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fnakhsan.mov.databinding.ActivityMyWalletBinding

class MyWalletActivity : AppCompatActivity() {
    private lateinit var myWalletBinding: ActivityMyWalletBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myWalletBinding = ActivityMyWalletBinding.inflate(layoutInflater)
        setContentView(myWalletBinding.root)
    }
}
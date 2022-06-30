package com.fnakhsan.mov.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fnakhsan.mov.databinding.ActivitySignUpPhotoBinding

class SignUpPhotoActivity : AppCompatActivity() {
    private lateinit var signUpPhotoBinding: ActivitySignUpPhotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpPhotoBinding = ActivitySignUpPhotoBinding.inflate(layoutInflater)
        setContentView(signUpPhotoBinding.root)
    }
}
package com.fnakhsan.mov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.fnakhsan.mov.data.User
import com.fnakhsan.mov.databinding.ActivitySignInBinding
import com.fnakhsan.mov.signup.SignUpActivity
import com.google.firebase.database.*
import kotlin.math.log

class SignInActivity : AppCompatActivity() {
    private lateinit var signInBinding: ActivitySignInBinding

    lateinit var iUsername: String
    lateinit var iPassword: String

    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(signInBinding.root)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")

        signInBinding.btnSignIn.setOnClickListener {
            iUsername = signInBinding.edtUsername.text.toString()
            iPassword = signInBinding.edtPassword.text.toString()

            if (iUsername == "") {
                signInBinding.edtUsername.error = "Silahkan tulis username anda"
                signInBinding.edtUsername.requestFocus()
            } else if (iPassword == "") {
                signInBinding.edtPassword.error = "Silahkan tulis password anda"
                signInBinding.edtPassword.requestFocus()
            } else {
                pushLogin(iUsername, iPassword)
            }
        }

        signInBinding.btnSignUp.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

//        val database = Firebase.database
//        val myRef = database.getReference("message")
//
//        myRef.setValue("Hello, World!")

    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        Log.d(TAG, "$iUsername, $iPassword")
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                var user = dataSnapshot.getValue(User::class.java)
                Log.d(TAG, "$user")
                if (user == null) {
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan", Toast.LENGTH_LONG)
                        .show()
                } else {
                    if (user.password == iPassword) {
                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@SignInActivity,
                            "Password anda salah",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                Log.d(TAG, "Value is: $user")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignInActivity, error.message, Toast.LENGTH_LONG).show()
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    companion object {
        private const val TAG = "Sign_In"
    }
}
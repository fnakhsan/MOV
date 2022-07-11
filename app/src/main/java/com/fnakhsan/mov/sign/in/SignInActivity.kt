package com.fnakhsan.mov.sign.`in`

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.fnakhsan.mov.dashboard.DashboardActivity
import com.fnakhsan.mov.data.User
import com.fnakhsan.mov.databinding.ActivitySignInBinding
import com.fnakhsan.mov.sign.up.SignUpActivity
import com.fnakhsan.mov.utils.Preferences
import com.google.firebase.database.*
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var signInBinding: ActivitySignInBinding

    private lateinit var iUsername: String
    private lateinit var iPassword: String

//    private lateinit var db: FirebaseDatabase
//    lateinit var myRef: DatabaseReference
    private lateinit var mDatabaseRef: DatabaseReference
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInBinding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(signInBinding.root)

//        db = Firebase.database("https://bwa-mov-fbe4b-default-rtdb.asia-southeast1.firebasedatabase.app/")
//        myRef = db.getReference("User")
        mDatabaseRef =
            FirebaseDatabase
                .getInstance("https://bwa-mov-fbe4b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("User")
        Log.d(TAG, "first")
        preferences = Preferences(this)

        Log.d(TAG, "second")
        preferences.setValues("onBoarding", "1")
        if (preferences.getValues("status") == "1"){
            finishAffinity()
            val intent = Intent(this@SignInActivity, DashboardActivity::class.java)
            startActivity(intent)
        }
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
                pushSignIn(iUsername, iPassword)
            }
        }

        signInBinding.btnSignUp.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pushSignIn(iUsername: String, iPassword: String) {
        mDatabaseRef.child(iUsername).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(this@SignInActivity, "User tidak ditemukan", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (user.password == iPassword) {
                        with(preferences){
                            setValues("nama", user.nama.toString())
                            setValues("user", user.username.toString())
                            setValues("url", user.url.toString())
                            setValues("email", user.email.toString())
                            setValues("saldo", user.saldo.toString())
                            setValues("status", "1")
                        }

                        val intent = Intent(this@SignInActivity, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@SignInActivity,
                            "Password anda salah",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                Log.d(TAG, "Value is: $user")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignInActivity, error.message, Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    companion object {
        private const val TAG = "Sign_In"
    }
}
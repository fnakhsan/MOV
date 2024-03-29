package com.fnakhsan.mov.sign.up

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.Toast
import com.fnakhsan.mov.data.User
import com.fnakhsan.mov.databinding.ActivitySignUpBinding
import com.fnakhsan.mov.utils.Preferences
import com.google.firebase.database.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var signUpBinding: ActivitySignUpBinding

    private lateinit var iUsername: String
    private lateinit var iPassword: String
    private lateinit var iFullname: String
    private lateinit var iEmail: String

    private lateinit var mDatabaseUserRef: DatabaseReference
    private lateinit var isIntent: String
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        isIntent = "no"
        preferences = Preferences(this)
        mDatabaseUserRef =
            FirebaseDatabase
                .getInstance("https://bwa-mov-fbe4b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("User")

        signUpBinding.btnSubmit.setOnClickListener {

            iUsername = signUpBinding.edtUsername.text.toString()
            iPassword = signUpBinding.edtPassword.text.toString()
            iFullname = signUpBinding.edtFullname.text.toString()
            iEmail = signUpBinding.edtEmail.text.toString()

            if (iUsername == "") {
                signUpBinding.edtUsername.error = "Silahkan tulis username anda"
                signUpBinding.edtUsername.requestFocus()
            } else if (iPassword == "") {
                signUpBinding.edtPassword.error = "Silahkan tulis password anda"
                signUpBinding.edtPassword.requestFocus()
            } else if (iFullname == "") {
                signUpBinding.edtFullname.error = "Silahkan tulis nama lengkap anda"
                signUpBinding.edtFullname.requestFocus()
            } else if (iEmail == "") {
                signUpBinding.edtEmail.error = "Silahkan tulis email anda"
                signUpBinding.edtEmail.requestFocus()
            } else if (!EMAIL_ADDRESS.matcher(iEmail).matches()) {
                signUpBinding.edtEmail.error = "Format email anda salah"
                signUpBinding.edtEmail.requestFocus()
            } else {
                submitSignUp(iUsername, iPassword, iFullname, iEmail)
            }
        }
    }

    private fun submitSignUp(
        iUsername: String,
        iPassword: String,
        iFullname: String,
        iEmail: String
    ) {
        val user = User()
        val listUser = arrayListOf<User>()
        user.username = iUsername
        user.password = iPassword
        user.nama = iFullname
        user.email = iEmail
        user.saldo = "280000"

        checkingUsername(iUsername, iEmail, user, listUser)
    }

    private fun checkingUsername(
        iUsername: String,
        iEmail: String,
        dataUser: User,
        listUser: ArrayList<User>
    ) {
        mDatabaseUserRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userDB = snapshot.child(iUsername).getValue(User::class.java)
                var index = 0
                var isEmptyEmail = false
                if (userDB == null) {
                    for (userSnapshot in snapshot.children) {
                        val listUserDB = userSnapshot.getValue(User::class.java)
                        listUser.add(listUserDB!!)
                        listUser[index].email
                        if (listUser[index].email == iEmail) {
                            isEmptyEmail = true
                        }
                        index += 1
                    }
                    if (!isEmptyEmail) {
                        mDatabaseUserRef.child(iUsername).setValue(dataUser)
                        val intent = Intent(
                            this@SignUpActivity,
                            SignUpPhotoActivity::class.java
                        ).putExtra("username", dataUser.username)
                        preferences.apply {
                            Log.d(TAG, "access pref..")
                            setValues("email", dataUser.password.toString())
                            Log.d(TAG, dataUser.password.toString())
                            setValues("nama", dataUser.nama.toString())
                            Log.d(TAG, dataUser.nama.toString())
                            setValues("user", dataUser.username.toString())
                            Log.d(TAG, dataUser.username.toString())
                            setValues("email", dataUser.email.toString())
                            Log.d(TAG, dataUser.email.toString())
                            setValues("saldo", dataUser.saldo.toString())
                            Log.d(TAG, dataUser.saldo.toString())
                        }
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@SignUpActivity,
                            "Email sudah digunakan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        "User sudah digunakan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpActivity, error.message, Toast.LENGTH_SHORT).show()
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    companion object {
        private const val TAG = "Up"
    }
}
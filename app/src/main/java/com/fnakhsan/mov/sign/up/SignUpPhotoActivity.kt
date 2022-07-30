package com.fnakhsan.mov.sign.up

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fnakhsan.mov.R
import com.fnakhsan.mov.dashboard.DashboardActivity
import com.fnakhsan.mov.databinding.ActivitySignUpPhotoBinding
import com.fnakhsan.mov.utils.Preferences
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*


class SignUpPhotoActivity : AppCompatActivity(), PermissionListener {
    private var statusAdd: Boolean = false
    private lateinit var filePath: Uri
    private lateinit var storage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private lateinit var mDatabaseUserRef: DatabaseReference
    private lateinit var preferences: Preferences
    private var getImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            statusAdd = true

            filePath = it!!
            Glide.with(this)
                .load(it)
                .apply(RequestOptions.circleCropTransform())
                .into(signUpPhotoBinding.photoProfile)
            signUpPhotoBinding.btnSaveUpload.visibility = View.VISIBLE
            signUpPhotoBinding.btnUpload.background = getDrawable(R.drawable.ic_btn_delete)
        }
    private lateinit var signUpPhotoBinding: ActivitySignUpPhotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpPhotoBinding = ActivitySignUpPhotoBinding.inflate(layoutInflater)
        setContentView(signUpPhotoBinding.root)

        preferences = Preferences(this)
        val username = intent.getStringExtra("username")
        storage = FirebaseStorage.getInstance("gs://bwa-mov-fbe4b.appspot.com/")
        storageRef = storage.reference
        mDatabaseUserRef =
            FirebaseDatabase
                .getInstance("https://bwa-mov-fbe4b-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("User")

        Log.d(TAG, preferences.getValues("nama").toString())
        with(signUpPhotoBinding) {
            tvWelcome.text = "Selamat Datang,\n$username"
            btnUpload.setOnClickListener {
                if (statusAdd) {
                    statusAdd = false
                    with(signUpPhotoBinding) {
                        btnSaveUpload.visibility = View.VISIBLE
                        btnUpload.background = getDrawable(R.drawable.ic_btn_upload)
                        photoProfile.setImageResource(R.drawable.user_pic)
                    }
                } else {
                    Dexter.withContext(this@SignUpPhotoActivity)
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(this@SignUpPhotoActivity).check()
                }
            }
            btnSkipUpload.setOnClickListener {
                finishAffinity()
                val intent = Intent(this@SignUpPhotoActivity, DashboardActivity::class.java)
                startActivity(intent)
            }
            btnSaveUpload.setOnClickListener {
                signUpPhotoBinding.loading.visibility = View.VISIBLE
                lifecycleScope.launch(Dispatchers.IO) {
                    var complete: Deferred<Boolean> = async {
                        return@async false
                    }
                    val ref =
                        storageRef.child("Profile Picture/" + UUID.randomUUID().toString())
                    ref.putFile(filePath)
                        .addOnSuccessListener {
                            signUpPhotoBinding.loading.visibility = View.INVISIBLE
                            Toast.makeText(
                                this@SignUpPhotoActivity,
                                "Uploaded",
                                Toast.LENGTH_SHORT
                            ).show()
                            //                                    pushImg(username!!, url)
                            ref.downloadUrl.addOnSuccessListener { uri ->
                                val url = uri.toString()
                                Log.d(TAG, "first: $url")
                                lifecycleScope.launch(Dispatchers.IO) {
                                    val result = async {
                                        Log.d(TAG, "1")
                                        var getUrl = ""
                                        Log.d(TAG, "2")
                                        mDatabaseUserRef.child(username!!).child("url")
                                            .setValue(url).toString()
                                        do {
                                            getUrl =
                                                mDatabaseUserRef.child(username).child("url").get()
                                                    .toString()
                                            Log.d(TAG, "get url from db: $getUrl")
                                        } while (getUrl != "")
                                        return@async getUrl
                                    }
                                    preferences.setValues("url", result.await())
                                    Log.d(TAG, "pref url: ${preferences.getValues("url")}")
                                    complete = async {
                                        return@async true
                                    }
                                }
                            }
                            ref.downloadUrl.addOnFailureListener { exception ->
                                Log.d(TAG, exception.toString())
                            }

                        }
                        .addOnFailureListener {
                            signUpPhotoBinding.loading.visibility = View.INVISIBLE
                            Toast.makeText(
                                this@SignUpPhotoActivity,
                                "Upload failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    if (complete.await()) {
                        Log.d(
                            TAG,
                            "final get url from db: ${mDatabaseUserRef.child(username!!).child("url").get()}"
                        )
                        Log.d(TAG, " final pref url: ${preferences.getValues("url")}")
                        finishAffinity()
                        val intent =
                            Intent(this@SignUpPhotoActivity, DashboardActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

//    private fun pushImg(username: String, url: String) {
//        mDatabaseUserRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                mDatabaseUserRef.child(username).child("url").setValue(url)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Toast.makeText(this@SignUpPhotoActivity, error.message, Toast.LENGTH_SHORT).show()
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//
//        })
//    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                getImage.launch("image/*")
            }
        }
    }

    override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        Toast.makeText(
            this@SignUpPhotoActivity,
            "Aktifkan perizinan camera",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, p1: PermissionToken?) {

    }

    companion object {
        const val TAG = "Photo"
    }

}
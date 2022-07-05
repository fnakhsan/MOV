package com.fnakhsan.mov.signup

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fnakhsan.mov.HomeActivity
import com.fnakhsan.mov.R
import com.fnakhsan.mov.databinding.ActivitySignUpPhotoBinding
import com.fnakhsan.mov.utils.Preferences
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.*


class SignUpPhotoActivity : AppCompatActivity(), PermissionListener {
    val REQUEST_IMAGE_CAPTURE = 1
    private var statusAdd: Boolean = false
    lateinit var filePath: Uri
    lateinit var storage: FirebaseStorage
    lateinit var storageRef: StorageReference
    lateinit var preferences: Preferences
    private lateinit var signUpPhotoBinding: ActivitySignUpPhotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpPhotoBinding = ActivitySignUpPhotoBinding.inflate(layoutInflater)
        setContentView(signUpPhotoBinding.root)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance("gs://bwa-mov-fbe4b.appspot.com/")
        storageRef = storage.reference

        with(signUpPhotoBinding) {
            tvWelcome.text = "Selamat Datang,\n" + intent.getStringExtra("username")
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
                val intent = Intent(this@SignUpPhotoActivity, HomeActivity::class.java)
                startActivity(intent)
            }
            btnSaveUpload.setOnClickListener {
                if (filePath != null) {
                    signUpPhotoBinding.loading.visibility = View.VISIBLE
                    val ref = storageRef.child("Profile Picture/" + UUID.randomUUID().toString())
                    ref.putFile(filePath)
                        .addOnSuccessListener {
                            signUpPhotoBinding.loading.visibility = View.INVISIBLE
                            Toast.makeText(
                                this@SignUpPhotoActivity,
                                "Uploaded",
                                Toast.LENGTH_SHORT
                            ).show()

                            ref.downloadUrl.addOnSuccessListener {
                                preferences.setValues("url", it.toString())
                            }

                            finishAffinity()
                            val intent = Intent(this@SignUpPhotoActivity, HomeActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener {
                            signUpPhotoBinding.loading.visibility = View.INVISIBLE
                            Toast.makeText(
                                this@SignUpPhotoActivity,
                                "Upload failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Toast.makeText(
                        this@SignUpPhotoActivity,
                        "Filepath doesn't exist",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
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

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            statusAdd = true

            filePath = data.data!!
            Glide.with(this)
                .load(bitmap)
                .apply(RequestOptions.circleCropTransform())
                .into(signUpPhotoBinding.photoProfile)
            signUpPhotoBinding.btnSaveUpload.visibility = View.VISIBLE
            signUpPhotoBinding.btnUpload.background = getDrawable(R.drawable.ic_btn_delete)
        }
    }
}
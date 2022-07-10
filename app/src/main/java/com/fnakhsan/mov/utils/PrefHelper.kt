package com.fnakhsan.mov.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fnakhsan.mov.R

class PrefHelper(context: Context) {
    fun setProfile(
        preferences: Preferences,
        imageView: ImageView,
        profileData: String = ""
    ) {
        val profilePref = preferences.getValues("url")
        if (profilePref != null) {
            Glide.with(imageView)
                .load(profilePref.toString())
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
        } else if (profileData != "") {
            Glide.with(imageView)
                .load(profileData)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
        } else {
            Glide.with(imageView)
                .load(R.drawable.user_pic)
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
        }
    }
}
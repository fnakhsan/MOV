package com.fnakhsan.mov.utils

import android.content.Context
import android.content.SharedPreferences

class Preferences(val context: Context) {
    private var sharedPreferences = context.getSharedPreferences(USER_PREF, 0)

    fun setValues(key: String, value: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValues(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    companion object {
        const val USER_PREF = "USER_PREF"
    }
}
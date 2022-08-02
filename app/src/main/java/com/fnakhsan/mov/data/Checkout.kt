package com.fnakhsan.mov.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Checkout(
    var seat: String? = "",
    var status: String? = "",
    var price: Int? = 0
): Parcelable

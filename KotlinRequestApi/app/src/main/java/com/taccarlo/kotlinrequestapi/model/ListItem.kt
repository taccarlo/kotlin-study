package com.taccarlo.kotlinrequestapi.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ListItem(
    val mediaTitleCustom: String, val mediaUrl: String,
    val mediaDate: MediaDate
) : Parcelable

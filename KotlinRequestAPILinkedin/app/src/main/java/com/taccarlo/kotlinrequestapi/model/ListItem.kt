package com.taccarlo.kotlinrequestapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * <i>ListItem</i>: item of MainList
 * @author Carlo Tacchella
 * @version 0.0.1
 * @since 2021-07-06
 */
@Parcelize
class ListItem(
    val login: String,
    val id: String,
    val avatar_url: String,
    val url:String
) : Parcelable

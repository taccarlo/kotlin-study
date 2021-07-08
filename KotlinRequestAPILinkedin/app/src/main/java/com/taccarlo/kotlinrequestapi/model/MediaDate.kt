package com.taccarlo.kotlinrequestapi.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * <i>MediaDate</i>: Contains a sub item of Main Item. Contains a date and is used to handle the JSON Response.
 * @author Carlo Tacchella
 * @version 0.0.1
 * @since 2021-07-06
 */
@Parcelize
class MediaDate(val dateString: String) : Parcelable
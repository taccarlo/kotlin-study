package com.taccarlo.kotlinrequestapi.utility

import okhttp3.*

/**
 * <i>StringManager</i> class that implements Singleton to handle Strings.
 * @author Carlo Tacchella
 * @version 0.0.1
 * @since 2021-07-06
 */
object StringManager {

    fun dateConversion(dateString: String): String {
        val day = dateString.substring(5,7)
        val month = dateString.substring(8, 11)
        val year = dateString.substring(12,16)
        return "$day/$month/$year"
    }
}
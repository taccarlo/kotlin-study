package com.taccarlo.kotlinrequestapi.utility

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.taccarlo.kotlinrequestapi.model.HomeFeed
import com.taccarlo.kotlinrequestapi.view.MainAdapter
import okhttp3.*
import java.io.IOException

/**
 * <i>Requests</i> class that implements Singleton to emit a simple HTTP request.
 * @author Carlo Tacchella
 * @version 0.0.1
 * @since 2021-07-06
 */
object Requests {

    fun fetchJson(rView: RecyclerView, act: FragmentActivity?) {
        println("Attempting to fetch JSON")
        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println("URL response:$body")
                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body, HomeFeed::class.java)
                act?.runOnUiThread {
                    rView.adapter = MainAdapter(homeFeed) { position ->
                        println("Salve $position")
                    }
                }
            }
        })
    }

}
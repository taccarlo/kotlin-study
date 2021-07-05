package com.taccarlo.kotlinrequestapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rView: RecyclerView = findViewById(R.id.recyclerView_main)
            rView.layoutManager = LinearLayoutManager(this)
            //rView.adapter = MainAdapter()
            fetchJson(rView)
    }

    private fun fetchJson(rView: RecyclerView){
        println("Attempting to fetch JSON")
        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println("URL response:$body")
                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body, HomeFeed::class.java)
                runOnUiThread{
                    rView.adapter = MainAdapter(homeFeed)
                }
            }
        })
    }
}

class HomeFeed( val videos: List<ListItem>)
class ListItem(val id: Int, val name: String, val link:String, val imageUrl: String, numberOfViews: String,
                val channel:Channel)
class Channel(val name: String, val profileimageUrl:String)
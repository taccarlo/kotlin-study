package com.taccarlo.kotlinrequestapi


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment(), View.OnClickListener {

    var navController: NavController? = null
    lateinit var rView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)



        rView = view.findViewById(R.id.recyclerView_main)
        rView.layoutManager = LinearLayoutManager(this.context)
        //rView.adapter = MainAdapter()
        fetchJson(rView)
    }

    override fun onClick(v: View?) {
        /*
           when(v!!.id){
               R.id.view_transactions_btn-> navController!!.navigate(R.id.action_mainFragment_to_viewTransactionFragment)
               R.id.send_money_btn-> navController!!.navigate(R.id.action_mainFragment_to_chooseRecipientFragment)
               R.id.view_balance_btn-> navController!!.navigate(R.id.action_mainFragment_to_viewBalanceFragment)
           }*/
    }

    private fun fetchJson(rView: RecyclerView) {
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
                activity?.runOnUiThread {
                    rView.adapter = MainAdapter(homeFeed)
                }
            }
        })
    }


}
class HomeFeed(val videos: List<ListItem>)
class ListItem(
    val id: Int, val name: String, val link: String, val imageUrl: String, numberOfViews: String,
    val channel: Channel
)

class Channel(val name: String, val profileimageUrl: String)
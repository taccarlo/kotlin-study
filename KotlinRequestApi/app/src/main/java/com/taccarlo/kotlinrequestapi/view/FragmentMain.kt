package com.taccarlo.kotlinrequestapi.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.taccarlo.kotlinrequestapi.R
import com.taccarlo.kotlinrequestapi.model.HomeFeed
import okhttp3.*
import java.io.IOException

/**
 * <i>MainFragment</i> is the fragment that shows the response of the HTTP request.
 * @author Carlo Tacchella
 * @version 0.0.1
 * @since 2021-07-06
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
                    rView.adapter = MainAdapter(homeFeed){
                        position -> println("Salve $position")
                    }
                }
            }
        })
    }


}
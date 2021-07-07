package com.taccarlo.kotlinrequestapi.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.GsonBuilder
import com.taccarlo.kotlinrequestapi.R
import com.taccarlo.kotlinrequestapi.model.ListItem
import com.taccarlo.kotlinrequestapi.model.MainList
import com.taccarlo.kotlinrequestapi.utility.SwipeGesture
import okhttp3.*
import java.io.IOException

/**
 * <i>MainFragment</i> is the fragment that shows the response of the HTTP request on the main page.
 * @author Carlo Tacchella
 * @version 0.0.1
 * @since 2021-07-06
 */
class MainFragment : Fragment() {

    private var navController: NavController? = null
    private lateinit var rView: RecyclerView
    private lateinit var refreshLayout : SwipeRefreshLayout

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
        refreshLayout = view.findViewById(R.id.swipe_refresh)
        rView = view.findViewById(R.id.recyclerView_main)


        rView.layoutManager = LinearLayoutManager(this.context)
        fetchJson(rView, activity)


        refreshLayout.setOnRefreshListener {
            Toast.makeText(rView.context,R.string.refresh, Toast.LENGTH_SHORT).show()
            //TODO: improve this point
            this.activity?.recreate()
            refreshLayout.isRefreshing = false

        }



    }

    private fun fetchJson(rView: RecyclerView, act: FragmentActivity?) {
        println("Attempting to fetch JSON")
        val url = "https://www.monclergroup.com/wp-json/mobileApp/v1/getPressReleasesDocs"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }

            override fun onResponse(call: Call, response: Response) {
                manageResponse(rView, act, response)
            }
        })
    }

    private fun manageResponse(rView: RecyclerView, act: FragmentActivity?, response: Response) {

        val body = response.body?.string()
        println("URL response:$body")
        val gson = GsonBuilder().create()
        val homeFeed = gson.fromJson(body, MainList::class.java)
        act?.runOnUiThread {

            val mAdapt = MainAdapter(homeFeed) { position, listItem ->
                showItem(position, listItem)
            }
            rView.adapter = mAdapt

            activateGesture(mAdapt, rView)

        }
    }

    private fun activateGesture(mAdapt: MainAdapter, rView: RecyclerView) {

        val swipeGesture = object : SwipeGesture(rView.context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mAdapt.deleteItem(viewHolder.absoluteAdapterPosition)
            }
        }

        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(rView)
    }

    private fun showItem(position: Int, listItem: ListItem) {
        val bundle = bundleOf("itemId" to position.toString(), "itemPassed" to listItem)
        navController!!.navigate(
            R.id.action_mainFragment_to_fragmentListElement,
            bundle
        )
    }

}
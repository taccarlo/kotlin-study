package com.taccarlo.kotlinrequestapi.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.taccarlo.kotlinrequestapi.R
import com.taccarlo.kotlinrequestapi.model.ListItem
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
    private lateinit var progressBar: ProgressBar

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
        progressBar = view.findViewById(R.id.loading_bar)
        progressBar.visibility = View.VISIBLE

        rView.layoutManager = LinearLayoutManager(this.context)
        fetchJson(rView, activity)



    }

    private fun fetchJson(rView: RecyclerView, act: FragmentActivity?) {
        println("Attempting to fetch JSON")
        val url = "https://api.github.com/repos/immuni-app/immuni/stargazers"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
                Toast.makeText(rView.context, R.string.failed_connection, Toast.LENGTH_SHORT).show()
                hideProgressBar(act)
            }

            override fun onResponse(call: Call, response: Response) {
                manageResponse(rView, act, response)
                hideProgressBar(act)
            }
        })
    }

    private fun hideProgressBar(act: FragmentActivity?) {
        act?.runOnUiThread {
            progressBar.visibility = View.GONE
        }
    }

    private fun manageResponse(rView: RecyclerView, act: FragmentActivity?, response: Response) {

        val body = response.body?.string()
        println("URL response:$body")

        val homeFeed: MutableList<ListItem> = Gson().fromJson(body, object : TypeToken<List<ListItem?>?>() {}.type)

        act?.runOnUiThread {

            val mAdapt = MainAdapter(homeFeed) { position, listItem ->
                showItem(position, listItem)
            }
            rView.adapter = mAdapt

        }
    }

    private fun showItem(position: Int, listItem: ListItem) {
        val bundle = bundleOf("itemId" to position.toString(), "itemPassed" to listItem)
        navController!!.navigate(
            R.id.action_mainFragment_to_fragmentListElement,
            bundle
        )
    }

}
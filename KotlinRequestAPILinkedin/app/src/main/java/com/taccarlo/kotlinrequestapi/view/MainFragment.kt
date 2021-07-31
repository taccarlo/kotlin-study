package com.taccarlo.kotlinrequestapi.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.taccarlo.kotlinrequestapi.R
import com.taccarlo.kotlinrequestapi.data.LinkedinRepository
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
    private lateinit var btnSearch: Button
    private lateinit var repoName: EditText
    private lateinit var repoOwner: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
    }

    private fun setupUI(view: View){

        navController = Navigation.findNavController(view)
        rView = view.findViewById(R.id.recyclerView_main)
        progressBar = view.findViewById(R.id.loading_bar)
        btnSearch = view.findViewById(R.id.btn_search)
        repoName = view.findViewById(R.id.repo_name)
        repoOwner = view.findViewById(R.id.repo_owner)

        // just init the main screen to dev purposes
        fetchJson(rView, activity, "immuni-app", "immuni")

        btnSearch.setOnClickListener {
            val owner = repoOwner.text
            val repo = repoName.text
            if (owner.toString().isNotEmpty() && repo.toString().isNotEmpty()) {
                fetchJson(rView, activity, owner.toString(), repo.toString())
                owner.clear()
                repo.clear()
            } else {
                Toast.makeText(view.context, "Insert a repo name and a owner", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        progressBar.visibility = View.GONE
        rView.layoutManager = LinearLayoutManager(this.context)
    }

    private fun fetchJson(
        rView: RecyclerView,
        act: FragmentActivity?,
        owner: String,
        repo: String
    ) {
        showProgressBar(act)
        val url = "https://api.github.com/repos/$owner/$repo/stargazers"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        println("Attempting to fetch JSON")
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

    private fun showProgressBar(act: FragmentActivity?) {
        act?.runOnUiThread {
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar(act: FragmentActivity?) {
        act?.runOnUiThread {
            progressBar.visibility = View.GONE
        }
    }

    private fun manageResponse(rView: RecyclerView, act: FragmentActivity?, response: Response) {

        try {
            val body = response.body?.string()
            println("URL response:$body")


            val linkedinList: MutableList<LinkedinRepository> =
                Gson().fromJson(body, object : TypeToken<List<LinkedinRepository?>?>() {}.type)

            act?.runOnUiThread {

                val mAdapt = MainAdapter(linkedinList) { position, listItem ->
                    showItem(position, listItem)
                }
                rView.adapter = mAdapt

            }
        } catch (e: JsonSyntaxException) {
            act?.runOnUiThread {
                Toast.makeText(rView.context, "No data found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showItem(position: Int, linkedinRepository: LinkedinRepository) {
        val bundle = bundleOf("itemId" to position.toString(), "itemPassed" to linkedinRepository)
        navController!!.navigate(
            R.id.action_mainFragment_to_fragmentListElement,
            bundle
        )
    }

}
package com.taccarlo.kotlinrequestapi.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.taccarlo.kotlinrequestapi.R
import com.taccarlo.kotlinrequestapi.model.LinkedinRepository


/**
 * <i>FragmentListElement</i> class that show the details of a item of the list shown in <i>FragmentMain</i>.
 * @author Carlo Tacchella
 * @version 0.0.1
 * @since 2021-07-06
 */
class FragmentListElement : Fragment() {

    private lateinit var itemId: String
    private lateinit var linkedinRepository: LinkedinRepository
    private lateinit var buttonLink: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemId = requireArguments().getString("itemId").toString()
        linkedinRepository = requireArguments().getParcelable("itemPassed")!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_element, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.item_title).text = linkedinRepository.login
        view.findViewById<TextView>(R.id.item_date).text =linkedinRepository.id
        buttonLink = view.findViewById(R.id.item_url)
        buttonLink.text = getString(R.string.link_to_profile)

        buttonLink.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(linkedinRepository.html_url)
            startActivity(openURL)
        }

    }
}
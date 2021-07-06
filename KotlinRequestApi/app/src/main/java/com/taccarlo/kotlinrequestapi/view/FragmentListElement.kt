package com.taccarlo.kotlinrequestapi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.taccarlo.kotlinrequestapi.R
import com.taccarlo.kotlinrequestapi.model.ListItem
import com.taccarlo.kotlinrequestapi.utility.StringManager.dateConversion


class FragmentListElement : Fragment() {

    private lateinit var itemId:String
    private lateinit var listItem:ListItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        itemId = requireArguments().getString("itemId").toString()
        listItem = requireArguments().getParcelable("itemPassed")!!
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

        view.findViewById<TextView>(R.id.item_title).text = listItem.mediaTitleCustom
        view.findViewById<TextView>(R.id.item_date).text = dateConversion(listItem.mediaDate.dateString)
        view.findViewById<TextView>(R.id.item_url).text = listItem.mediaUrl
    }
}
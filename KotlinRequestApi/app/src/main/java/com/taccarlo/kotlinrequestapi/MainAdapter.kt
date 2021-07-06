package com.taccarlo.kotlinrequestapi;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(val homeFeed: HomeFeed) : RecyclerView.Adapter<CustomViewHolder>() {

    val itemTitles = listOf("element 1", "element 2", "element 3", "element 4")
    val itemSubitles = listOf("subelement 1", "subelement 2", "subelement 3", "subelement 4")

    // numberOfItems
    override fun getItemCount(): Int {
        return homeFeed.videos.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        // val itemTitle = itemTitles.get(position)
        val itemTitle = homeFeed.videos[position].name
        val itemSubtitle = itemSubitles[position]
        holder.view.findViewById<TextView>(R.id.item_title).text = itemTitle
        holder.view.findViewById<TextView>(R.id.item_subtitle).text = itemSubtitle
    }

}

class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

}
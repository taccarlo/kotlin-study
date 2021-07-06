package com.taccarlo.kotlinrequestapi.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taccarlo.kotlinrequestapi.R
import com.taccarlo.kotlinrequestapi.model.CustomViewHolder
import com.taccarlo.kotlinrequestapi.model.HomeFeed

/**
 * <i>MainAdapter</i> handles the binding of the <i>item_row.xml</i> layout to the RecyclerView. It also takes in a list of items and displays them to the user.
 * @author Carlo Tacchella
 * @version 0.0.1
 * @since 2021-07-06
 */
class MainAdapter(private val homeFeed: HomeFeed, private val listener: (position: Int) -> Unit) :
    RecyclerView.Adapter<CustomViewHolder>() {

    val itemTitles = listOf("element 1", "element 2", "element 3", "element 4")
    val itemSubtitles = listOf("subElement 1", "subElement 2", "subElement 3", "subElement 4")

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
        val itemSubtitle = itemSubtitles[position]
        holder.view.findViewById<TextView>(R.id.item_title).text = itemTitle
        holder.view.findViewById<TextView>(R.id.item_subtitle).text = itemSubtitle
        holder.itemView.setOnClickListener {
            listener.invoke(position)
        }
    }

}

package com.taccarlo.kotlinrequestapi.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taccarlo.kotlinrequestapi.R
import com.taccarlo.kotlinrequestapi.model.CustomViewHolder
import com.taccarlo.kotlinrequestapi.model.ListItem
import com.taccarlo.kotlinrequestapi.model.MainList

/**
 * <i>MainAdapter</i> handles the binding of the <i>item_row.xml</i> layout to the RecyclerView in <i>FragmentMain</i>.
 * It also takes in a list of items and displays them to the user.
 * @author Carlo Tacchella
 * @version 0.0.1
 * @since 2021-07-06
 */
class MainAdapter(
    private var mainList: MainList,
    private val listener: (position: Int, listItem: ListItem) -> Unit
) :
    RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return mainList.content.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = mainList.content[position]
        val itemTitle = item.mediaTitleCustom
        val itemSubtitle = "ciao"
        holder.view.findViewById<TextView>(R.id.item_title).text = itemTitle
        holder.view.findViewById<TextView>(R.id.item_subtitle).text = itemSubtitle
        holder.itemView.setOnClickListener {
            listener.invoke(position, item)
        }
    }

    fun deleteItem(i: Int) {
        mainList.content.removeAt(i)
        notifyDataSetChanged()
    }
}

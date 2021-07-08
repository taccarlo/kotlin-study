package com.taccarlo.kotlinrequestapi.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.taccarlo.kotlinrequestapi.R
import com.taccarlo.kotlinrequestapi.model.CustomViewHolder
import com.taccarlo.kotlinrequestapi.model.LinkedinRepository

/**
 * <i>MainAdapter</i> handles the binding of the <i>item_row.xml</i> layout to the RecyclerView in <i>FragmentMain</i>.
 * It also takes in a list of items and displays them to the user.
 * @author Carlo Tacchella
 * @version 0.0.1
 * @since 2021-07-06
 */
class MainAdapter(
    private var mainList: MutableList<LinkedinRepository>,
    private val listener: (position: Int, linkedinRepository: LinkedinRepository) -> Unit
) :
    RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return mainList.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = mainList[position]
        val itemTitle = item.login
        holder.view.findViewById<TextView>(R.id.item_title).text = itemTitle
        holder.itemView.setOnClickListener {
            listener.invoke(position, item)
        }
    }

    fun deleteItem(i: Int) {
        mainList.removeAt(i)
        notifyDataSetChanged()
    }
}

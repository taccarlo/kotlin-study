package com.taccarlo.kotlinrequestapi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.taccarlo.kotlinrequestapi.R
import com.taccarlo.kotlinrequestapi.models.CustomViewHolder
import com.taccarlo.kotlinrequestapi.models.LinkedinRepository

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
        val thumbnailImageView = holder.view.findViewById<ImageView>(R.id.item_image)

        holder.view.findViewById<TextView>(R.id.item_title).text = itemTitle
        holder.itemView.setOnClickListener {
            listener.invoke(position, item)
        }
        Picasso.get().load(item.avatar_url).into(thumbnailImageView)
    }

}

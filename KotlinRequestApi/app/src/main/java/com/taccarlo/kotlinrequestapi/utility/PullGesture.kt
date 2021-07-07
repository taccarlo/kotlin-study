package com.taccarlo.kotlinrequestapi.utility
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class PullGesture :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.UP) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

}
package com.example.samplecode.util.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.samplecode.ui.model.ShoppingItemViewData

class ShoppingListRecyclerViewDiffCallback(
    private val oldList: List<ShoppingItemViewData>,
    private val newList: List<ShoppingItemViewData>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.description == newItem.description &&
                oldItem.isChecked == newItem.isChecked
    }
}

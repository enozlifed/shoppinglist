package com.example.samplecode.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.samplecode.data.model.ShoppingItemEntity
import com.example.samplecode.databinding.ShoppinglistItemBinding
import com.example.samplecode.util.diffutil.ShoppingListRecyclerViewDiffCallback

class ShoppingItemListAdapter :
    RecyclerView.Adapter<ShoppingListRecyclerViewHolder>() {

    var onCheckBoxClick: ((ShoppingItemEntity) -> Unit)? = null

    private var items = emptyList<ShoppingItemEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListRecyclerViewHolder {
        val binding = ShoppinglistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShoppingListRecyclerViewHolder.ShoppingListViewHolder(binding, onCheckBoxClick)
    }

    override fun onBindViewHolder(holder: ShoppingListRecyclerViewHolder, position: Int) {
        when (holder) {
            is ShoppingListRecyclerViewHolder.ShoppingListViewHolder ->
                holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    fun setData(newList: List<ShoppingItemEntity>) {
        val diffUtil = ShoppingListRecyclerViewDiffCallback(items, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        items = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun getItemAt(position: Int): ShoppingItemEntity = items[position]
}

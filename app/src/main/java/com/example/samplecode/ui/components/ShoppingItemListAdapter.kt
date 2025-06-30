package com.example.samplecode.ui.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.samplecode.data.model.ShoppingItemEntity
import com.example.samplecode.databinding.ShoppinglistItemBinding
import com.example.samplecode.ui.model.ShoppingItemViewData
import com.example.samplecode.ui.model.toViewData
import com.example.samplecode.util.diffutil.ShoppingListRecyclerViewDiffCallback

class ShoppingItemListAdapter :
    RecyclerView.Adapter<ShoppingListRecyclerViewHolder>() {

    var onItemCheckedChanged : ((ShoppingItemViewData) -> Unit)? = null

    private var items = emptyList<ShoppingItemViewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListRecyclerViewHolder {
        val binding = ShoppinglistItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ShoppingListRecyclerViewHolder.ShoppingListViewHolder(binding, onItemCheckedChanged )
    }

    override fun onBindViewHolder(holder: ShoppingListRecyclerViewHolder, position: Int) {
        when (holder) {
            is ShoppingListRecyclerViewHolder.ShoppingListViewHolder ->
                holder.bind(items[position])
        }
    }

    override fun getItemCount(): Int = items.size

    fun setData(newList: List<ShoppingItemEntity>) {
        val viewDataList = newList.map { it.toViewData() }

        val diffUtil = ShoppingListRecyclerViewDiffCallback(items, viewDataList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        items = viewDataList
        diffResult.dispatchUpdatesTo(this)
    }

    fun getItemAt(position: Int): ShoppingItemViewData = items[position]
}

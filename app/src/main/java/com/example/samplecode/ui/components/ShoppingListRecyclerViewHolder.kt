package com.example.samplecode.ui.components

import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.samplecode.R
import com.example.samplecode.databinding.ShoppinglistItemBinding
import com.example.samplecode.ui.model.ShoppingItemViewData

sealed class ShoppingListRecyclerViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class ShoppingListViewHolder(
        private val binding: ShoppinglistItemBinding,
        private val onCheckBoxClick: ((ShoppingItemViewData) -> Unit)?
    ) : ShoppingListRecyclerViewHolder(binding) {

        fun bind(item: ShoppingItemViewData) = with(binding) {
            cbShoppingItem.isChecked = item.isChecked
            tvShoppingItem.text = item.description

            if (item.isChecked) {
                tvShoppingItem.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                tvShoppingItem.setTextColor(ContextCompat.getColor(tvShoppingItem.context, R.color.hintColor))
            } else {
                tvShoppingItem.paintFlags = tvShoppingItem.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                tvShoppingItem.setTextColor(ContextCompat.getColor(tvShoppingItem.context, R.color.primary_text_color))
            }

            val updatedItem = item.copy(isChecked = !item.isChecked)
            clShoppingItem.setOnClickListener {
                onCheckBoxClick?.invoke(updatedItem)
            }
        }
    }
}
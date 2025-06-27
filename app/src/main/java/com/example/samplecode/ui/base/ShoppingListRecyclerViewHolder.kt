package com.example.samplecode.ui.base

import android.graphics.Paint
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.samplecode.R
import com.example.samplecode.data.model.ShoppingItemEntity
import com.example.samplecode.databinding.ShoppinglistItemBinding

sealed class ShoppingListRecyclerViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class ShoppingListViewHolder(
        private val binding: ShoppinglistItemBinding,
        private val onCheckBoxClick: ((ShoppingItemEntity) -> Unit)?
    ) : ShoppingListRecyclerViewHolder(binding) {

        fun bind(item: ShoppingItemEntity) = with(binding) {
            cbShoppingItem.isChecked = item.checkBoxState
            tvShoppingItem.text = item.description

            if (item.checkBoxState) {
                tvShoppingItem.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                tvShoppingItem.setTextColor(ContextCompat.getColor(tvShoppingItem.context, R.color.hintColor))
            } else {
                tvShoppingItem.paintFlags = tvShoppingItem.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                tvShoppingItem.setTextColor(ContextCompat.getColor(tvShoppingItem.context, R.color.primary_text_color))
            }

            val updatedItem = item.copy(checkBoxState = !item.checkBoxState)
            cbShoppingItem.setOnClickListener { onCheckBoxClick?.invoke(updatedItem) }
            tvShoppingItem.setOnClickListener { onCheckBoxClick?.invoke(updatedItem) }
        }
    }
}
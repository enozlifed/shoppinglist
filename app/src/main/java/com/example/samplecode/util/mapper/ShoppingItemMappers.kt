package com.example.samplecode.util.mapper

import com.example.samplecode.data.model.ShoppingItemEntity
import com.example.samplecode.ui.model.ShoppingItemViewData

fun ShoppingItemEntity.toViewData(): ShoppingItemViewData {
    return ShoppingItemViewData(
        id = this.id,
        description = this.description,
        isChecked = this.isChecked
    )
}

fun ShoppingItemViewData.toEntity(): ShoppingItemEntity {
    return ShoppingItemEntity(
        id = this.id,
        description = this.description,
        isChecked = this.isChecked
    )
}
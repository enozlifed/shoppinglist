package com.example.samplecode.ui.model

import com.example.samplecode.data.model.ShoppingItemEntity

data class ShoppingItemViewData(
    val id: String,
    val description: String,
    val isChecked: Boolean
)

/**
 * Maps a Room entity [ShoppingItemEntity] to its corresponding UI model [ShoppingItemViewData].
 */
fun ShoppingItemEntity.toViewData(): ShoppingItemViewData {
    return ShoppingItemViewData(
        id = this.id,
        description = this.description,
        isChecked = this.isChecked
    )
}

/**
 * Maps UI model [ShoppingItemViewData] back to [ShoppingItemEntity].
 */
fun ShoppingItemViewData.toEntity(): ShoppingItemEntity {
    return ShoppingItemEntity(
        id = this.id,
        description = this.description,
        isChecked = this.isChecked
    )
}
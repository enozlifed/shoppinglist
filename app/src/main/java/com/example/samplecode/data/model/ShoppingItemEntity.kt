package com.example.samplecode.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "shopping_item_table")
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val description: String,
    val isChecked: Boolean
): Parcelable

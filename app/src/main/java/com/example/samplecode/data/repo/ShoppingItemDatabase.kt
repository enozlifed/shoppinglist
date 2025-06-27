package com.example.samplecode.data.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.samplecode.data.model.ShoppingItemEntity
import com.example.samplecode.data.repo.ShoppingItemDao

@Database(
    entities = [ShoppingItemEntity::class],
    version = 1,
    exportSchema = true
)
abstract class ShoppingItemDatabase : RoomDatabase() {
    abstract fun shoppingItemDao(): ShoppingItemDao
}
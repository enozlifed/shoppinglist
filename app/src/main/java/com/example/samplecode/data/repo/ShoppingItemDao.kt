package com.example.samplecode.data.repo

import androidx.room.*
import com.example.samplecode.data.model.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shoppingItemEntity: ShoppingItemEntity)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(shoppingItemEntity: ShoppingItemEntity)

    @Delete
    suspend fun delete(shoppingItemEntity: ShoppingItemEntity)

    @Query("SELECT * FROM shopping_item_table ORDER BY id ASC")
    fun fetchAllItems(): Flow<List<ShoppingItemEntity>>
}
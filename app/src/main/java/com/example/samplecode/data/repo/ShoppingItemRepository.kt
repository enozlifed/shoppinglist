package com.example.samplecode.data.repo

import com.example.samplecode.data.db.ShoppingItemDao
import com.example.samplecode.data.model.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

class ShoppingItemRepository(private val shoppingItemDao: ShoppingItemDao) {

    fun fetchAllItems(): Flow<List<ShoppingItemEntity>> =
        shoppingItemDao.fetchAllItems().distinctUntilChanged()

    suspend fun addItem(shoppingItemEntity: ShoppingItemEntity){
        shoppingItemDao.insert(shoppingItemEntity)
    }

    suspend fun updateItem(shoppingItemEntity: ShoppingItemEntity){
        shoppingItemDao.update(shoppingItemEntity)
    }

    suspend fun deleteItem(shoppingItemEntity: ShoppingItemEntity){
        shoppingItemDao.delete(shoppingItemEntity)
    }
}
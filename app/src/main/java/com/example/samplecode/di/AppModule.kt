package com.example.samplecode.di

import androidx.room.Room
import com.example.samplecode.data.repo.ShoppingItemDatabase
import com.example.samplecode.data.repo.ShoppingItemRepository
import com.example.samplecode.ui.shoppingList.ShoppingListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            ShoppingItemDatabase::class.java,
            "shopping_item_database"
        ).build()
    }

    single {
        get<ShoppingItemDatabase>().shoppingItemDao()
    }

    single {
        ShoppingItemRepository(get())
    }

    viewModel {
        ShoppingListViewModel(get())
    }
}
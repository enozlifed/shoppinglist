package com.example.samplecode.ui.shoppingList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplecode.data.model.ShoppingItemEntity
import com.example.samplecode.data.repo.ShoppingItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class ShoppingListViewModel(
    private val repositoryShoppingList: ShoppingItemRepository
) : ViewModel() {

    val uiModel: StateFlow<ShoppingListScreenUiModel> = repositoryShoppingList.fetchAllItems
        .map { ShoppingListScreenUiModel(it) }
        .distinctUntilChanged()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ShoppingListScreenUiModel(emptyList()))

    private val _eventFlow = MutableSharedFlow<ShoppingListEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onAddItem(description: String) {
        if (description.isBlank()) return
        val newItem = ShoppingItemEntity(
            id = UUID.randomUUID().toString(),
            description = description,
            checkBoxState = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            repositoryShoppingList.addItem(newItem)
        }
    }

    fun setCheckboxState(shoppingItemEntity: ShoppingItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryShoppingList.updateItem(shoppingItemEntity)
        }
    }

    fun onItemSwipedToDelete(item: ShoppingItemEntity) {
        viewModelScope.launch {
            _eventFlow.emit(ShoppingListEvent.ShowUndoDeleteSnackbar(item))
        }
    }

    fun confirmDeleteItem(item: ShoppingItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryShoppingList.deleteItem(item)
        }
    }

    data class ShoppingListScreenUiModel(
        val shoppingList: List<ShoppingItemEntity>
    )

    sealed class ShoppingListEvent {
        data class ShowUndoDeleteSnackbar(val item: ShoppingItemEntity) : ShoppingListEvent()
    }
}
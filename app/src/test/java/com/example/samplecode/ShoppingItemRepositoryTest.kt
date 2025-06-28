package com.example.samplecode

import com.example.samplecode.data.db.ShoppingItemDao
import com.example.samplecode.data.model.ShoppingItemEntity
import com.example.samplecode.data.repo.ShoppingItemRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingItemRepositoryTest {

    private lateinit var repository: ShoppingItemRepository
    private val dao: ShoppingItemDao = mock()

    @Before
    fun setUp() {
        repository = ShoppingItemRepository(dao)
    }

    @Test
    fun `addItem calls insert on dao`() = runTest {
        val item = ShoppingItemEntity("1", "Milk", false)
        repository.addItem(item)

        verify(dao).insert(item)
    }

    @Test
    fun `updateItem calls update on dao`() = runTest {
        val item = ShoppingItemEntity("1", "Milk", true)
        repository.updateItem(item)

        verify(dao).update(item)
    }

    @Test
    fun `deleteItem calls delete on dao`() = runTest {
        val item = ShoppingItemEntity("1", "Milk", false)
        repository.deleteItem(item)

        verify(dao).delete(item)
    }

    @Test
    fun `fetchAllItems returns flow from dao`() = runTest {
        val mockFlow = flowOf(
            listOf(
                ShoppingItemEntity("1", "Milk", false),
                ShoppingItemEntity("2", "Eggs", true)
            )
        )

        whenever(dao.fetchAllItems()).thenReturn(mockFlow)

        val items = repository.fetchAllItems().first()

        assertEquals(2, items.size)
        assertEquals("Milk", items[0].description)
        assertEquals("Eggs", items[1].description)
        assertTrue(items[0].isChecked.not())
        assertTrue(items[1].isChecked)
    }
}

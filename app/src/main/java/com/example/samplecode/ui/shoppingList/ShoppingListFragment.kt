package com.example.samplecode.ui.shoppingList

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samplecode.ui.components.ShoppingItemListAdapter
import com.example.samplecode.databinding.FragmentShoppingListBinding
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.samplecode.R
import com.example.samplecode.data.model.ShoppingItemEntity
import com.example.samplecode.ui.model.toEntity
import com.example.samplecode.util.swipe.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShoppingListFragment : Fragment() {

    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!
    private val shoppingListViewModel: ShoppingListViewModel by viewModel()
    private lateinit var shoppingListAdapter: ShoppingItemListAdapter
    private lateinit var recyclerViewShoppingList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        setupObservers()

        binding.fabShoppingList.setOnClickListener {
            showAddItemDialog()
        }
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                shoppingListViewModel.uiModel.collect { uiModel ->
                    shoppingListAdapter.setData(uiModel.shoppingList)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                shoppingListViewModel.eventFlow.collect { event ->
                    when (event) {
                        is ShoppingListViewModel.ShoppingListEvent.ShowUndoDeleteSnackbar -> {
                            showUndoSnackbar(event.item)
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        shoppingListAdapter = ShoppingItemListAdapter().apply {
            onItemCheckedChanged  = {
                shoppingListViewModel.setCheckboxState(it.toEntity())
            }
        }

        val deleteSwipeHandler = object : SwipeToDeleteCallback(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.layoutPosition
                val item = shoppingListAdapter.getItemAt(pos)
                shoppingListViewModel.onItemSwipedToDelete(item.toEntity())
            }
        }

        recyclerViewShoppingList = binding.recyclerViewShoppingList
        recyclerViewShoppingList.apply {
            adapter = shoppingListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(deleteSwipeHandler).attachToRecyclerView(this)
        }
    }

    private fun showAddItemDialog() {
        val inputEditTextField = EditText(requireContext())
        val marginInDp = 20
        val scale = resources.displayMetrics.density
        val marginInPx = (marginInDp * scale + 0.5f).toInt()

        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(marginInPx, marginInPx, marginInPx, 0)
        }

        val container = FrameLayout(requireContext()).apply {
            addView(inputEditTextField, params)
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Add new item to shopping list:")
            .setView(container)
            .setPositiveButton("OK") { _, _ ->
                val inputText = inputEditTextField.text.toString()
                shoppingListViewModel.onAddItem(inputText)
            }
            .setNegativeButton("CANCEL", null)
            .create()
            .show()
    }

    private fun showUndoSnackbar(item: ShoppingItemEntity) {
        Snackbar.make(
            binding.innerLayoutShoppingList,
            getString(R.string.deletionInProgress),
            Snackbar.LENGTH_SHORT
        )
            .setAnchorView(binding.fabShoppingList)
            .setBackgroundTint(resources.getColor(R.color.snackbar_background, resources.newTheme()))
            .setActionTextColor(resources.getColor(R.color.snackbar_action_text, resources.newTheme()))
            .setTextColor(resources.getColor(R.color.snackbar_text, resources.newTheme()))

            .addCallback(object : Snackbar.Callback() {
                override fun onShown(snackbar: Snackbar?) {
                    shoppingListViewModel.confirmDeleteItem(item)
                }
            })
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

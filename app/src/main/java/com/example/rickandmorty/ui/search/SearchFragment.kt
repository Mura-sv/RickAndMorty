package com.example.rickandmorty.ui.search

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.core.BaseFragment
import com.example.rickandmorty.core.network.Status
import com.example.rickandmorty.databinding.FragmentSearchBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {

    private val viewModel by viewModels<SearchViewModel>()
    private val adapter by lazy { SearchAdapter() }


    override fun inflateViewBinding(): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(layoutInflater)
    }

    override fun setupUI() {
        viewModel.progressBar.observe(viewLifecycleOwner) {
            binding.progressbar.isVisible = it
        }

        setupRecycler()
        binding.filters.setOnClickListener {
            filter()
        }
    }

    override fun setupLiveData() {
        binding.searchText.addTextChangedListener {
            it?.let {
                if (it.isNotEmpty()) {
                    viewModel.searchByName(it.toString()).observe(viewLifecycleOwner) { response ->
                        when (response.status) {
                            Status.SUCCESS -> {
                                response.data?.let { it1 ->
                                    adapter.differ.submitList(it1) {
                                        binding.recyclerview.scrollToPosition(0)
                                    }
                                }
                                viewModel.progressBar.value = false
                            }
                            Status.LOADING -> {
                                viewModel.progressBar.value = true
                            }
                            Status.ERROR -> {
                                viewModel.progressBar.value = false
                            }
                        }
                    }
                }
            }
        }


        viewModel
    }


    private fun setupRecycler() {
        binding.recyclerview.adapter = adapter
        adapter.setOnClickListener { searchModel ->
            val passSearchModel =
                SearchFragmentDirections.actionSearchFragmentToSearchDetail(searchModel)
            findNavController().navigate(passSearchModel)
        }
    }


    private fun filter() {
        // alert dialog
        val singleItems = arrayOf("By date")
        val checkedItem = 1

        context?.let { context ->
            MaterialAlertDialogBuilder(context)
                .setTitle("Filters")
                .setCancelable(true)
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.cancel()
                }
                .setSingleChoiceItems(singleItems, checkedItem) { dialog, _ ->
                    val sortedList = adapter.differ.currentList.sortedByDescending { it.created }
                    adapter.differ.submitList(sortedList) { binding.recyclerview.scrollToPosition(0) }

                    Toast.makeText(requireContext(),
                        "Sorted by date, newest to oldest",
                        Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .show()
        }
    }


}






package com.riocallos.breakingbad.ui.characters

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import com.riocallos.breakingbad.R
import com.riocallos.breakingbad.databinding.FragmentCharactersBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private val viewModel: CharactersViewModel by viewModel()

    private val characterAdapter: CharacterAdapter by lazy {
        CharacterAdapter(emptyArray()) { character, card ->
            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(
                    R.integer.reply_motion_duration_large
                ).toLong()
            }
            reenterTransition = MaterialElevationScale(true).apply {
                duration = resources.getInteger(
                    R.integer.reply_motion_duration_large
                ).toLong()
            }
            val extras = FragmentNavigatorExtras(
                card to "trans"
            )
            val directions = CharactersFragmentDirections.actionCharacter(character)
            findNavController().navigate(directions, extras)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeCharacters()
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.filter.visibility = View.GONE
            getCharacters(true)
        }
        getCharacters()

        binding.filter.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity());

            builder.setTitle("Filter Characters by Season")

            builder.setMultiChoiceItems(arrayOf("Season 1", "Season 2", "Season 3", "Season 4", "Season 5"), viewModel.getSelectedSeasons()) {
                    dialog, which, isChecked -> if(isChecked) viewModel.seasonsFilter.add(which + 1) else viewModel.seasonsFilter.remove(which + 1)
            }

            builder.setCancelable(false)

            builder.setPositiveButton("Done") { dialog, which ->
                viewModel.filterCharacters()
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_characters, menu)

        val actionSearch = menu.findItem(R.id.action_search)

        val searchView = actionSearch.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchCharacters(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchCharacters(newText ?: "")
                return true
            }

        })

    }

    private fun observeCharacters() {
        viewModel.characters.observe(viewLifecycleOwner) {
            it?.let { characters ->
                binding.swipeRefreshLayout.isRefreshing = false
                binding.recyclerView.apply {
                    layoutManager = LinearLayoutManager(requireActivity())
                    adapter = characterAdapter
                }
                characterAdapter.updateItems(characters)
                if(characters.isNotEmpty()) {
                    binding.filter.visibility = View.VISIBLE
                }
            }
        }

        viewModel.filteredCharacters.observe(viewLifecycleOwner) {
            characterAdapter.updateItems(it)
        }
    }

    private fun getCharacters(refresh: Boolean = false) {
        binding.swipeRefreshLayout.isRefreshing = true
        lifecycleScope.launch {
            viewModel.getCharacters(refresh)
        }
    }

}
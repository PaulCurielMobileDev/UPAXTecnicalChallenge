package com.mexicandeveloper.upaxpruebatecnica.ui.pokemon

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mexicandeveloper.upaxpruebatecnica.data.entities.PokemonEntity
import com.mexicandeveloper.upaxpruebatecnica.databinding.FragmentPokemonListBinding
import com.mexicandeveloper.upaxpruebatecnica.utils.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class PokemonListFragment : Fragment(), PokemonAdapter.RVListener {

    private var _binding: FragmentPokemonListBinding? = null
    private val viewModel: PokemonListViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)

        lifecycleScope.launch(Dispatchers.IO) {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    when (uiState) {
                        is State.LoadingState -> {
                            //Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                        }
                        is State.DataState -> {
                            uiState.data.let { items ->
                                lifecycleScope.launch(Dispatchers.Main) {
                                    if (binding.rvPokemon.adapter == null) {
                                        binding.rvPokemon.adapter =
                                            PokemonAdapter(items, this@PokemonListFragment)
                                    } else {
                                        val adapter = binding.rvPokemon.adapter as PokemonAdapter
                                        adapter.add(items)
                                    }
                                }
                            }
                        }
                        is State.ErrorState -> {
                            Toast.makeText(
                                requireContext(),
                                uiState.exception.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
        }
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvPokemon.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(item: PokemonEntity) {
        val action = PokemonListFragmentDirections.goToDetail(item.id)
        findNavController().navigate(action)

    }

    override fun onBottomReachListener(lastId: Int) {
        Log.d("AddPokemons", "Pidiendp ${lastId}<<<<<<<<<<<<<<<<<<")
        viewModel.addPokemon(lastId)
    }
}
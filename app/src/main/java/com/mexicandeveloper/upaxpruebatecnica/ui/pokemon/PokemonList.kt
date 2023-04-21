package com.mexicandeveloper.upaxpruebatecnica.ui.pokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mexicandeveloper.upaxpruebatecnica.R
import com.mexicandeveloper.upaxpruebatecnica.databinding.FragmentPokemonListBinding
import com.mexicandeveloper.upaxpruebatecnica.utils.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class PokemonList : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    private val viewModel: PokemonListViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        lifecycleScope.launch {
            viewModel.getPokemonListResponse().collect {

                when (it) {
                    is State.LoadingState -> {
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }
                    is State.DataState -> {
                        Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                    is State.ErrorState -> {
                        Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
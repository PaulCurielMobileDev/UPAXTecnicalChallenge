package com.mexicandeveloper.upaxpruebatecnica.ui.pokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.mexicandeveloper.upaxpruebatecnica.R
import com.mexicandeveloper.upaxpruebatecnica.databinding.FragmentPokemonDetailBinding
import com.mexicandeveloper.upaxpruebatecnica.ui.MainActivity
import com.mexicandeveloper.upaxpruebatecnica.utils.Constants
import com.mexicandeveloper.upaxpruebatecnica.utils.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {

    val args: PokemonDetailFragmentArgs by navArgs()

    private var _binding: FragmentPokemonDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).getToolBar()?.let {
            it.navigationIcon =
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_back)
        }

        lifecycleScope.launch {
            viewModel.getPokemon(args.pokeNumber).collect { uiState ->

                when (uiState) {
                    is State.LoadingState -> binding.pbDetail.visibility = View.VISIBLE
                    is State.DataState -> {
                        binding.pbDetail.visibility = View.GONE
                        uiState.data.apply {

                            binding.tvDetailExperience.text = "$baseExperience"
                            binding.tvDetailHeight.text = "$height"
                            binding.tvDetailId.text = "$id"
                            binding.tvDetailOrder.text = "$order"
                            binding.tvDetailWeight.text = "$weight"
                            binding.tvDetailType1.text = uiState.data.type1
                            binding.tvDetailType2.text = uiState.data.type2

                            (activity as MainActivity).getToolBar()?.title = name.uppercase()
                            binding.ivDetailSpriteDefault.setName(name)

                            binding.ivDetailSpriteDefault.setUrl(
                                Constants.BASE_URL_SPRITE_FRONTAL.replace(
                                    "{NUM}",
                                    "${id}"
                                )
                            )

                            binding.rvDetailImages.layoutManager = GridLayoutManager(
                                requireContext(),
                                2,
                                GridLayoutManager.VERTICAL,
                                false
                            )
                            binding.rvDetailImages.adapter = ImageGridAdapter(sprites)
                        }
                    }
                    is State.ErrorState -> {
                        Toast.makeText(
                            requireContext(),
                            uiState.exception.message,
                            Toast.LENGTH_LONG
                        ).show()
                        findNavController().navigateUp()
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
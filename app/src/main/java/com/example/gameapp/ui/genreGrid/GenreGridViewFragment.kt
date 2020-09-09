package com.example.gameapp.ui.genreGrid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gameapp.R
import com.example.gameapp.databinding.FragmentGenreGridViewBinding

class GenreGridViewFragment : Fragment() {

    private val gameViewModel: GameGridViewModel by lazy {
        ViewModelProvider(this).get(GameGridViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = DataBindingUtil.inflate<FragmentGenreGridViewBinding>(
            inflater,
            R.layout.fragment_genre_grid_view, container, false
        )
        // Setting view model
        binding.viewModel = gameViewModel
        binding.lifecycleOwner = this

        // Setting the adapter
        val adapter = GameGridAdapter(GameClickListener {
            findNavController().navigate(
                GenreGridViewFragmentDirections.actionGenreGridViewFragmentToGameDetailsFragment(it)
            )
        })
        binding.movieGridView.adapter = adapter
        gameViewModel.allGames.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        return binding.root
    }

}
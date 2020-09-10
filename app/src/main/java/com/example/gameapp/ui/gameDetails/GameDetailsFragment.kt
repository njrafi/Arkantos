package com.example.gameapp.ui.gameDetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.gameapp.R
import com.example.gameapp.databinding.FragmentGameDetailsBinding
import com.example.gameapp.repository.GameApiStatus
import com.example.gameapp.ui.genreGrid.GameGridViewModel

class GameDetailsFragment : Fragment() {
    private val gameDetailsViewModel: GameDetailsViewModel by lazy {
        ViewModelProvider(this).get(GameDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameDetailsBinding>(
            inflater,
            R.layout.fragment_game_details, container, false
        )
        binding.viewModel = gameDetailsViewModel
        binding.lifecycleOwner = this

        val args = arguments?.let { GameDetailsFragmentArgs.fromBundle(it) }
        args?.id?.let {
            gameDetailsViewModel.getSpecificGame(it)
        }
        return binding.root
    }
}
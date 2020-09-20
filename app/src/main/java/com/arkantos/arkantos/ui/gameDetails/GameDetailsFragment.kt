package com.arkantos.arkantos.ui.gameDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.arkantos.arkantos.R
import com.arkantos.arkantos.databinding.FragmentGameDetailsBinding

class GameDetailsFragment : Fragment() {
    private var gameId: Long = -1
    private val gameDetailsViewModel: GameDetailsViewModel by lazy {
        ViewModelProvider(this, GameDetailsViewModel.Factory(activity?.application!!, gameId)).get(
            GameDetailsViewModel::class.java
        )
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

        val args = arguments?.let { GameDetailsFragmentArgs.fromBundle(it) }
        args?.id?.let {
            gameId = it
        }
        binding.viewModel = gameDetailsViewModel
        binding.lifecycleOwner = this
        binding.favoriteButton.visibility = View.GONE

        setupFavoriteButton(binding)
        return binding.root
    }

    private fun setupFavoriteButton(binding: FragmentGameDetailsBinding) {
        binding.favoriteButton.setOnCheckStateChangeListener { _, checked ->
            if(checked) gameDetailsViewModel.addToFavorite()
            else gameDetailsViewModel.removeFromFavourite()
        }
        gameDetailsViewModel.favoriteGames.observe(viewLifecycleOwner, {
            // TODO: Update to individual function
            if (binding.favoriteButton.visibility == View.GONE) {
                binding.favoriteButton.visibility = View.VISIBLE
                var buttonNewStatus = false
                for (game in it) {
                    if (game.id == gameId) buttonNewStatus = true
                }
                binding.favoriteButton.isChecked = buttonNewStatus
            }
        })

    }
}
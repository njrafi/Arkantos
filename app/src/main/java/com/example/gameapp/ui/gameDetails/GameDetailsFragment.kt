package com.example.gameapp.ui.gameDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.gameapp.R
import com.example.gameapp.databinding.FragmentGameDetailsBinding
import com.like.LikeButton
import com.like.OnLikeListener

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
            setupFavoriteButton(binding, it)
        }

        return binding.root
    }

    private fun setupFavoriteButton(binding: FragmentGameDetailsBinding, gameId: Long) {
        binding.favoriteButton.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                gameDetailsViewModel.addToFavorite(gameId)
            }

            override fun unLiked(likeButton: LikeButton?) {
                gameDetailsViewModel.removeFromFavourite(gameId)
            }
        })

    }
}
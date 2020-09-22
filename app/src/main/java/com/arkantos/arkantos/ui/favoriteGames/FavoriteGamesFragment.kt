package com.arkantos.arkantos.ui.favoriteGames

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.arkantos.arkantos.R
import com.arkantos.arkantos.databinding.FragmentFavoriteGamesBinding
import com.arkantos.arkantos.databinding.FragmentGenreGridViewBinding
import com.arkantos.arkantos.ui.genreGrid.GameClickListener

class FavoriteGamesFragment : Fragment() {
    private val favoriteGamesViewModel: FavoriteGamesViewModel by lazy {
        ViewModelProvider(this).get(FavoriteGamesViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentFavoriteGamesBinding>(
            inflater,
            R.layout.fragment_favorite_games, container, false
        )
        binding.lifecycleOwner = this

        val adapter = FavoriteGamesListAdapter(GameClickListener {
            this.findNavController().navigate(
                FavoriteGamesFragmentDirections.actionFavoriteGamesFragmentToGameDetailsFragment(it)
            )
        })
        binding.favoriteGamesRecyclerView.adapter = adapter
        favoriteGamesViewModel.favoriteGamesList.observe(viewLifecycleOwner) {
            Log.i("favorite", it.size.toString())
            adapter.submitList(it)
        }

        return binding.root
    }

}
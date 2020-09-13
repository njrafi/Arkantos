package com.example.gameapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.example.gameapp.R
import com.example.gameapp.databinding.FragmentHomeBinding
import com.example.gameapp.domain.Game
import com.example.gameapp.network.GameApiBody
import com.example.gameapp.ui.genreGrid.GameClickListener

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home,
            container, false
        )

        binding.lifecycleOwner = this

        binding.showAllGames.text = getString(R.string.show_all_games)
        binding.showAllGames.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGenreGridViewFragment(
                    -1
                )
            )
        }

        setupGameContainer(
            binding.adventureGames,
            homeViewModel.adventureGamesPagedList,
            binding.viewAdventureGamesButton, GameApiBody.GenreString.Adventure.id
        )
        setupGameContainer(
            binding.rpgGames,
            homeViewModel.rpgGamesPagedList,
            binding.viewRpgGamesButton, GameApiBody.GenreString.RolePlaying.id
        )

        return binding.root
    }

    private fun setupGameContainer(
        container: RecyclerView,
        gameList: LiveData<PagedList<Game>>,
        viewAllButton: Button,
        genreId: Int
    ) {
        viewAllButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGenreGridViewFragment(
                    genreId
                )
            )
        }
        val adapter = GameListAdapter(GameClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGameDetailsFragment(it)
            )
        })
        container.adapter = adapter
        gameList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }


}
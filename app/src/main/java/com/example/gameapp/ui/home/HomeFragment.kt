package com.example.gameapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.gameapp.R
import com.example.gameapp.databinding.FragmentHomeBinding
import com.example.gameapp.network.GameApiBody
import com.example.gameapp.ui.genreGrid.GameClickListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


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

        binding.ggButton.text = "go to ggla"
        binding.ggButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGenreGridViewFragment(-1))
        }

        binding.viewAdventureGamesButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGenreGridViewFragment(
                    GameApiBody.GenreString.Adventure.id
                )
            )
        }

        binding.viewRpgGamesButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGenreGridViewFragment(
                    GameApiBody.GenreString.RolePlaying.id
                )
            )
        }

        val adventureGamesAdapter = GameListAdapter(GameClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGameDetailsFragment(it)
            )
        })
        binding.adventureGames.adapter = adventureGamesAdapter
        homeViewModel.adventureGamesPagedList.observe(viewLifecycleOwner, {
            adventureGamesAdapter.submitList(it)
        })

        val rpgGamesAdapter = GameListAdapter(GameClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGameDetailsFragment(it)
            )
        })
        binding.rpgGames.adapter = rpgGamesAdapter
        homeViewModel.rpgGamesPagedList.observe(viewLifecycleOwner, {
            rpgGamesAdapter.submitList(it)
        })

        return binding.root
    }


}
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
import com.example.gameapp.ui.genreGrid.GameClickListener
import com.example.gameapp.ui.genreGrid.GameGridAdapter
import com.example.gameapp.ui.genreGrid.GameGridViewModel
import com.example.gameapp.ui.genreGrid.GenreGridViewFragmentDirections

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
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(inflater,R.layout.fragment_home,
            container,false)

        binding.lifecycleOwner = this

        binding.ggButton.text = "go to ggla"
        binding.ggButton.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToGenreGridViewFragment())
        }

        val actionGameAdapter = GameGridAdapter(GameClickListener {
            findNavController().navigate(
               HomeFragmentDirections.actionHomeFragmentToGameDetailsFragment(it)
            )
        })
        binding.actionGames.adapter = actionGameAdapter
        homeViewModel.actionGamesPagedList.observe(viewLifecycleOwner, {
            actionGameAdapter.submitList(it)
        })

        val adventureGameAdapter = GameGridAdapter(GameClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGameDetailsFragment(it)
            )
        })
        binding.adventureGames.adapter = adventureGameAdapter
        homeViewModel.adventureGamesPagedList.observe(viewLifecycleOwner, {
            adventureGameAdapter.submitList(it)
        })

        return binding.root
    }


}
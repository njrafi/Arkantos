package com.example.gameapp.ui.home

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gameapp.R
import com.example.gameapp.databinding.FragmentHomeBinding
import com.example.gameapp.domain.Game
import com.example.gameapp.network.GameApiBody
import com.example.gameapp.setImageFromUrl
import com.example.gameapp.ui.genreGrid.GameClickListener
import com.synnapps.carouselview.ImageClickListener
import com.synnapps.carouselview.ImageListener

class HomeFragment : Fragment() {
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.hide()
        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater, R.layout.fragment_home,
            container, false
        )

        binding.lifecycleOwner = this

        binding.showAllGames.text = getString(R.string.show_all_games)
        binding.showAllGames.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGenreGridViewFragment(
                    -1, "All Games"
                )
            )
        }

        setupGameContainer(
            binding.adventureGames,
            homeViewModel.adventureGamesPagedList,
            binding.viewAdventureGamesButton, GameApiBody.GenreString.Adventure
        )
        setupGameContainer(
            binding.rpgGames,
            homeViewModel.rpgGamesPagedList,
            binding.viewRpgGamesButton, GameApiBody.GenreString.RolePlaying
        )
        setupCarouselView(binding)
        setupSplashScreen(binding)
        return binding.root
    }

    private fun setupSplashScreen(binding: FragmentHomeBinding) {
        homeViewModel.allGamesLoaded.observe(viewLifecycleOwner) {
            if (it == homeViewModel.totalApiCalls) {
                binding.logo.visibility = View.GONE
                binding.splashScreen.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                (activity as AppCompatActivity).supportActionBar?.show()
            }
        }
    }
    private fun setupCarouselView(binding: FragmentHomeBinding) {
        homeViewModel.popularGames.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.carouselView.setImageListener(ImageListener { position, imageView ->
                    imageView.setImageFromUrl(it[position].coverImageUrl)
                })
                binding.carouselView.setImageClickListener(ImageClickListener { position ->
                    Log.i("HomeFragment Touched", position.toString())
                    it[position].id?.let { gameId ->
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToGameDetailsFragment(
                                gameId
                            )
                        )
                    }

                })
                binding.carouselView.pageCount = it.size
            }
        }
    }

    private fun setupGameContainer(
        container: RecyclerView,
        gameList: LiveData<PagedList<Game>>,
        viewAllButton: Button,
        genreString: GameApiBody.GenreString
    ) {
        viewAllButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToGenreGridViewFragment(
                    genreString.id, "$genreString Games"
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
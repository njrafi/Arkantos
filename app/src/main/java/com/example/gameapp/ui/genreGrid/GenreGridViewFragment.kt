package com.example.gameapp.ui.genreGrid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.gameapp.R
import com.example.gameapp.databinding.FragmentGenreGridViewBinding
import com.example.gameapp.network.GameApiBody
import com.example.gameapp.repository.GameApiStatus
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

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

        val args = arguments?.let { GenreGridViewFragmentArgs.fromBundle(it)}
        for(genreString in GameApiBody.GenreString.values()) {
            if(genreString.id == args?.genreId) {
                gameViewModel.changeGenre(genreString)
            }
        }


        // Setting the adapter
        val adapter = GameGridAdapter(GameClickListener {
            findNavController().navigate(
                GenreGridViewFragmentDirections.actionGenreGridViewFragmentToGameDetailsFragment(it)
            )
        })
        binding.movieGridView.adapter = adapter
        gameViewModel.gamePagedList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        setupBottomSpinner(binding)
        return binding.root
    }

    private fun setupBottomSpinner(binding: FragmentGenreGridViewBinding) {
        gameViewModel.apiStatus.observe(viewLifecycleOwner, {
            if (it != GameApiStatus.LOADING) binding.progressBar.visibility = View.GONE
        })

        binding.movieGridView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.progressBar.animate()
                }
            }
        })
    }


}
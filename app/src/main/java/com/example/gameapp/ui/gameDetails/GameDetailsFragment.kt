package com.example.gameapp.ui.gameDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.gameapp.R
import com.example.gameapp.databinding.FragmentGameDetailsBinding

class GameDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentGameDetailsBinding>(inflater,
            R.layout.fragment_game_details,container,false)
        return binding.root
    }
}
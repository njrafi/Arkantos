package com.arkantos.arkantos.ui.profile

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.arkantos.arkantos.R
import com.arkantos.arkantos.databinding.FragmentProfileBinding
import com.arkantos.arkantos.enableEditing
import com.arkantos.arkantos.ui.home.HomeViewModel

class ProfileFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProvider(this).get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentProfileBinding>(
            inflater, R.layout.fragment_profile,
            container, false
        )
        binding.lifecycleOwner = this
        binding.viewModel = profileViewModel
        setupButtons(binding)
        return binding.root
    }

    private fun setupButtons(binding: FragmentProfileBinding) {
        binding.editNameButton.setOnClickListener {
            binding.nameTextView.enableEditing(true)
        }
        binding.editEmailButton.setOnClickListener {
            binding.emailTextView.enableEditing(true)
        }
        binding.submitButton.setOnClickListener {
            binding.nameTextView.enableEditing(false)
            binding.emailTextView.enableEditing(false)
        }
    }


}
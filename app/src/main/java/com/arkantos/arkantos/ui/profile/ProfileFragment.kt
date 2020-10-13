package com.arkantos.arkantos.ui.profile

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.arkantos.arkantos.R
import com.arkantos.arkantos.databinding.FragmentProfileBinding
import com.arkantos.arkantos.enableEditing
import com.arkantos.arkantos.helpers.ImageHelper
import com.arkantos.arkantos.helpers.UserHolder


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
        binding.profilePicture.setOnClickListener {
            selectImage(requireContext())
        }
        binding.editProfilePictureButton.setOnClickListener {
            selectImage(requireContext())
        }
        binding.editNameButton.setOnClickListener {
            binding.nameTextView.enableEditing(true)
            showAllButton(binding)
        }
        binding.editEmailButton.setOnClickListener {
            binding.emailTextView.enableEditing(true)
            showAllButton(binding)
        }
        binding.submitButton.setOnClickListener {
            hideAllButtonAndDisableEditing(binding)
            updateProfile(binding)
        }
        binding.cancelButton.setOnClickListener {
            hideAllButtonAndDisableEditing(binding)
            profileViewModel.updateUi()
        }
    }

    private fun updateProfile(binding: FragmentProfileBinding) {
        val newProfile = UserHolder.getUser().copy()
        newProfile.name = binding.nameTextView.text.toString()
        newProfile.email = binding.emailTextView.text.toString()
        profileViewModel.updateProfile(newProfile)
    }

    private fun hideAllButtonAndDisableEditing(binding: FragmentProfileBinding) {
        binding.cancelButton.visibility = View.GONE
        binding.submitButton.visibility = View.GONE
        binding.nameTextView.enableEditing(false)
        binding.emailTextView.enableEditing(false)
    }

    private fun showAllButton(binding: FragmentProfileBinding) {
        binding.cancelButton.visibility = View.VISIBLE
        binding.submitButton.visibility = View.VISIBLE
    }

    enum class PhotoRequestCode(val requestCode: Int) {
        CAMERA(0),
        GALLERY(1)
    }

    private fun selectImage(context: Context) {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose your profile picture")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePicture, PhotoRequestCode.CAMERA.requestCode)
                }
                options[item] == "Choose from Gallery" -> {
                    val pickPhoto =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickPhoto, PhotoRequestCode.GALLERY.requestCode)
                }
                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        })
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_CANCELED) {
            when (requestCode) {
                PhotoRequestCode.CAMERA.requestCode -> if (resultCode == RESULT_OK && data != null) {
                    val bitmapImage = data.extras?.get("data") as Bitmap?
                    val base64ImageString =
                        ImageHelper.convertBitmapImageToBase64Encoded(bitmapImage)
                    profileViewModel.updateProfilePicture(base64ImageString)
                }
                PhotoRequestCode.GALLERY.requestCode -> if (resultCode == RESULT_OK && data != null) {
                    val selectedImage = data.data
                    val bitmapImage =
                        ImageHelper.convertImageUriToBitmap(selectedImage, requireContext())
                    val base64ImageString =
                        ImageHelper.convertBitmapImageToBase64Encoded(bitmapImage)
                    profileViewModel.updateProfilePicture(base64ImageString)
                }
            }
        }
    }
}
package com.arkantos.arkantos.ui.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arkantos.arkantos.helpers.UserHolder
import com.arkantos.arkantos.repository.BackendRepository

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val backendRepository = BackendRepository(application)

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _email = MutableLiveData<String>()
    val email: LiveData<String>
        get() = _email

    private val _profilePictureUrl = MutableLiveData<String>()
    val profilePictureUrl: LiveData<String>
        get() = _profilePictureUrl

    init {
        updateUi()
    }

    private fun updateUi() {
        _name.value = UserHolder.getUser().name
        _email.value = UserHolder.getUser().email
        _profilePictureUrl.value = UserHolder.getUser().photoUrl
    }
}
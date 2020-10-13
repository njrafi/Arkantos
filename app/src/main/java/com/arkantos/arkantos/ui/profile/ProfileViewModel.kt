package com.arkantos.arkantos.ui.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arkantos.arkantos.helpers.UserHolder
import com.arkantos.arkantos.network.models.UserNetworkModel
import com.arkantos.arkantos.repository.BackendRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val job = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + job)
    private val backendRepository = BackendRepository(application)

    val apiStatus = backendRepository.apiStatus

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

    fun updateUi() {
        _name.value = UserHolder.getUser().name
        _email.value = UserHolder.getUser().email
        _profilePictureUrl.value = UserHolder.getUser().photoUrl
    }

    fun updateProfilePicture(base64ImageString: String?) {
        val newProfile = UserHolder.getUser().copy()
        newProfile.photoUrl = base64ImageString
        updateProfile(newProfile)
    }

    fun updateProfile(newProfile: UserNetworkModel) {
        viewModelScope.launch {
            try {
                backendRepository.updateProfile(newProfile)
                updateUi()
            } catch (t : Throwable) {
                Log.e("ProfileViewModel", t.message.toString())
            }
        }


    }
}
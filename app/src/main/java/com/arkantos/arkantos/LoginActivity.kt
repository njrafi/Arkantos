package com.arkantos.arkantos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.arkantos.arkantos.databinding.ActivityLoginBinding
import com.arkantos.arkantos.ui.home.HomeViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private val tag = "Login Activity"
    private lateinit var binding: ActivityLoginBinding
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_login
        )
        Firebase.auth.currentUser?.reload()

        if (Firebase.auth.currentUser == null) {
            createSignInIntent()
        } else {
            goToMainActivity()
        }
    }

    private fun createSignInIntent() {
        binding.loginButton.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
        binding.loginButton.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.FacebookBuilder().build(),
                AuthUI.IdpConfig.PhoneBuilder().build()
            )
            // Create and launch sign-in intent
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setIsSmartLockEnabled(false)
                    .setAvailableProviders(providers)
                    .setLogo(R.drawable.leonidas)
                    .build(),
                RC_SIGN_IN
            )
        }
    }

    private fun goToMainActivity() {
        binding.loginButton.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
        homeViewModel.allGamesLoaded.observe(this) {
            if (it == homeViewModel.totalApiCalls) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == RESULT_OK) {
                Log.i(tag, "Signed up Successfully in firebase server")
                homeViewModel.signUp { signUpCompleted ->
                    if (signUpCompleted) {
                        Log.i(tag, "Sign Up Finished in backend server")
                        goToMainActivity()
                    } else {
                        Toast.makeText(baseContext, "Sign Up Failed", Toast.LENGTH_SHORT).show()
                    }
                }
                return
                // ...
            } else {
                Log.i(tag, "Signed in Failed")
                if (response != null)
                    Log.i(tag, response.error.toString())

                if (response == null) {
                    // User pressed back button
                    Toast.makeText(baseContext, "Sign in cancelled", Toast.LENGTH_SHORT).show()
                    return
                }

                if (response.error?.errorCode == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(baseContext, "No internet", Toast.LENGTH_SHORT).show()
                    return
                }

                if (response.error?.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(baseContext, "Unknown error", Toast.LENGTH_SHORT).show()
                    return
                }
            }

            Toast.makeText(baseContext, "Unknown sign in response", Toast.LENGTH_SHORT).show()

        }
    }


    companion object {

        private const val RC_SIGN_IN = 123
    }

}
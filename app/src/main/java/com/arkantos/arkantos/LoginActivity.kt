package com.arkantos.arkantos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.arkantos.arkantos.databinding.ActivityLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(
            this,
            R.layout.activity_login
        )
        Firebase.auth.currentUser?.reload()
        if (Firebase.auth.currentUser == null)
            createSignInIntent()
        else
            goToMainActivity()
    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == RESULT_OK) {
                Log.i("firebase", "Signed in Successfully")
                goToMainActivity()
                return
                // ...
            } else {
                Log.i("firebase", "Signed in Failed")
                if (response != null)
                    Log.i("firebase", response.error.toString())

                if (response == null) {
                    // User pressed back button
                    Toast.makeText(baseContext, "Sign in cancelled", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.error?.errorCode == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(baseContext, "No internet", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.error?.errorCode == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(baseContext, "Unknown error", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Toast.makeText(baseContext, "Unknown sign in response", Toast.LENGTH_SHORT).show();

        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }


    companion object {

        private const val RC_SIGN_IN = 123
    }

}
package com.example.gameapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.gameapp.network.GameApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private fun testNetworkCall() {
        coroutineScope.launch {
            try {
                val gameList = GameApi.retrofitService.getGames()
                Log.i("gg", gameList.size.toString())
                for (game in gameList) {
                    game.name?.let { Log.i("gg", it) }
                    game.summary?.let { Log.i("gg", it) }
                    game.id?.let { Log.i("gg", it.toString()) }
                }
            } catch (t: Throwable) {
                if (t.message != null)
                    Log.i("gg", t.message!!)
                else
                    Log.i("gg", "error with null message")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = this.findNavController(R.id.main_navhost_fragmnet)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.main_navhost_fragmnet)
        return navController.navigateUp()
    }
}
package com.example.gameapp.ui.favoriteGames

import android.view.ViewGroup.LayoutParams
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gameapp.databinding.FavoriteListItemBinding
import com.example.gameapp.databinding.GridItemBinding
import com.example.gameapp.domain.Game
import com.example.gameapp.ui.genreGrid.GameClickListener
import com.example.gameapp.ui.genreGrid.GameDiffCallBack


class FavoriteGamesListAdapter(private val clickListener: GameClickListener) :
    ListAdapter<Game, FavoriteGamesListAdapter.CustomViewHolder>(GameDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteGamesListAdapter.CustomViewHolder {
        return CustomViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoriteGamesListAdapter.CustomViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class CustomViewHolder private constructor(private val binding: FavoriteListItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Game, clickListener: GameClickListener) {
            binding.game = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CustomViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FavoriteListItemBinding.inflate(layoutInflater,parent,false)
                return CustomViewHolder(binding)
            }
        }
    }
}
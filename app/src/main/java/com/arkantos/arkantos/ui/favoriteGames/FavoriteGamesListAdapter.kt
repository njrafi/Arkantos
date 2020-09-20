package com.arkantos.arkantos.ui.favoriteGames

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arkantos.arkantos.databinding.FavoriteListItemBinding
import com.arkantos.arkantos.databinding.GridItemBinding
import com.arkantos.arkantos.domain.Game
import com.arkantos.arkantos.ui.genreGrid.GameClickListener
import com.arkantos.arkantos.ui.genreGrid.GameDiffCallBack


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
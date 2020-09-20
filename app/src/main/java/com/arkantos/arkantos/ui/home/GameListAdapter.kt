package com.arkantos.arkantos.ui.home

import android.util.Log
import com.arkantos.arkantos.ui.genreGrid.GameClickListener
import com.arkantos.arkantos.ui.genreGrid.GameDiffCallBack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arkantos.arkantos.databinding.GridItemBinding
import com.arkantos.arkantos.databinding.ListItemBinding
import com.arkantos.arkantos.domain.Game

class GameListAdapter(private val clickListener: GameClickListener) :
    PagedListAdapter<Game, GameListAdapter.CustomViewHolder>(GameDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameListAdapter.CustomViewHolder {
        return CustomViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GameListAdapter.CustomViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null)
            holder.bind(item, clickListener)
        else {
            Log.i("GameListAdapter", "Item in null")
        }
    }

    class CustomViewHolder private constructor(private val binding: ListItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Game, clickListener: GameClickListener) {
            binding.game = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CustomViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater,parent,false)
                return CustomViewHolder(binding)
            }
        }
    }
}
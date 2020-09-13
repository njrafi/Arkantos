package com.example.gameapp.ui.home

import android.util.Log
import com.example.gameapp.ui.genreGrid.GameClickListener
import com.example.gameapp.ui.genreGrid.GameDiffCallBack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gameapp.databinding.GridItemBinding
import com.example.gameapp.databinding.ListItemBinding
import com.example.gameapp.domain.Game

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
            Log.i("GameGridAdapter", "Item in null")
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
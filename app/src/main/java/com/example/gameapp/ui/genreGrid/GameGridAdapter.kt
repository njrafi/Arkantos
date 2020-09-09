package com.example.gameapp.ui.genreGrid


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.gameapp.databinding.GridItemBinding
import com.example.gameapp.domain.Game

class GameGridAdapter(val clickListener: GameClickListener) :
    ListAdapter<Game, GameGridAdapter.CustomViewHolder>(GameDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameGridAdapter.CustomViewHolder {
        return CustomViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GameGridAdapter.CustomViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class CustomViewHolder private constructor(private val binding: GridItemBinding):
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Game, clickListener: GameClickListener) {
            binding.game = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CustomViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = GridItemBinding.inflate(layoutInflater,parent,false)
                return CustomViewHolder(binding)
            }
        }
    }

    class GameDiffCallBack: DiffUtil.ItemCallback<Game>() {
        override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
            return oldItem == newItem
        }
    }
}
class GameClickListener(val clickListener: (id: Long) -> Unit) {
    fun onClick(id: Long) = clickListener(id)
}

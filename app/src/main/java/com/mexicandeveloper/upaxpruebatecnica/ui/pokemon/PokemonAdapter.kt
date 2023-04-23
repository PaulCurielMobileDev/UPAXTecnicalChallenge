package com.mexicandeveloper.upaxpruebatecnica.ui.pokemon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mexicandeveloper.upaxpruebatecnica.data.entities.Pokemon
import com.mexicandeveloper.upaxpruebatecnica.databinding.RowPokemonBinding
import com.mexicandeveloper.upaxpruebatecnica.databinding.RowProgressbarBinding
import com.mexicandeveloper.upaxpruebatecnica.utils.Constants
import java.util.*

class PokemonAdapter(var items: List<Pokemon>, var listener: RVListener) :
    RecyclerView.Adapter<ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    override fun getItemViewType(position: Int): Int {
        return if (position < items.size) VIEW_TYPE_ITEM else VIEW_TYPE_LOADING
    }

    inner class PokemonHolder(private val binding: RowPokemonBinding) : ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener.onItemClick(items[adapterPosition])
            }
        }

        fun bind(item: Pokemon) {
            val text = "${adapterPosition + 1}  ${item.name.uppercase(Locale.getDefault())}"
            binding.tvName.text = text
            binding.ivImage.setName(item.name)
            binding.ivImage.setUrl(Constants.BASE_URL_SPRITE_FRONTAL.replace("{NUM}","${adapterPosition+1}"))
        }
    }

    inner class ProgressBarHolder(itemView: View) : ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val binding: RowPokemonBinding =
                    RowPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PokemonHolder(binding)
            }
            else -> {
                val bindingPB = RowProgressbarBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ProgressBarHolder(bindingPB.root)
            }
        }

    }

    override fun getItemCount() = items.size + 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is PokemonHolder -> holder.bind(items[position])
            is ProgressBarHolder -> listener.onBottomReachListener(items.size)
        }


    }

    fun add(newItems: List<Pokemon>) {
            val size=items.size
            items = newItems
            notifyItemRangeChanged(size, size + 1)
            notifyItemRangeInserted(size, items.size + 1)

    }

    interface RVListener {
        fun onItemClick(item: Pokemon)
        fun onBottomReachListener(size: Int)
    }
}
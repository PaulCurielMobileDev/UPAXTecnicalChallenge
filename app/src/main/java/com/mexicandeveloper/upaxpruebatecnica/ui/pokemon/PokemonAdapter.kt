package com.mexicandeveloper.upaxpruebatecnica.ui.pokemon

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mexicandeveloper.upaxpruebatecnica.data.entities.PokemonEntity
import com.mexicandeveloper.upaxpruebatecnica.databinding.RowPokemonBinding
import com.mexicandeveloper.upaxpruebatecnica.databinding.RowProgressbarBinding
import com.mexicandeveloper.upaxpruebatecnica.utils.Constants
import java.util.*

class PokemonAdapter(var items: List<PokemonEntity>, var listener: RVListener) :
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

        fun bind(item: PokemonEntity) {
            val text = "${item.id}  ${item.name.uppercase(Locale.getDefault())}"
            binding.tvName.text = text
            binding.ivImage.setName(item.name)
            binding.ivImage.setUrl(Constants.BASE_URL_SPRITE_FRONTAL.replace("{NUM}", "${item.id}"))
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

    override fun getItemCount() = if (items.isEmpty()) 6 else items.size + 2

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is PokemonHolder -> {
                holder.bind(items[position])
                // if (position > items.size - 2) listener.onBottomReachListener(items.last().id)
            }
            is ProgressBarHolder -> listener.onBottomReachListener(if (items.isEmpty()) 0 else items.last().id)
        }


    }

    fun add(newItems: List<PokemonEntity>) {

        Log.d("AddPokemons", "Se agregaron ${newItems.size}")
        val size = items.size
        items = newItems
        notifyItemRangeChanged(size, size + 2)
        notifyItemRangeInserted(size + 2, items.size + 2)
        Log.d("AddPokemons", "ya quedaron ${items.size}")

    }

    interface RVListener {
        fun onItemClick(item: PokemonEntity)
        fun onBottomReachListener(lastId: Int)
    }
}
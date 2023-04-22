package com.mexicandeveloper.upaxpruebatecnica.ui.pokemon

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.mexicandeveloper.upaxpruebatecnica.data.entities.Sprites

class ImageGridAdapter(private val sprites: Sprites) :
    RecyclerView.Adapter<ImageGridAdapter.ImageViewHolder>() {

    val imageList: List<String>
        get() {
            val mutableList = mutableListOf<String>()
            if (!sprites.backDefault.isNullOrEmpty()) mutableList.add(sprites.backDefault!!)
            if (!sprites.backFemale.isNullOrEmpty()) mutableList.add(sprites.backFemale!!)
            if (!sprites.backShiny.isNullOrEmpty()) mutableList.add(sprites.backShiny!!)
            if (!sprites.backShinyFemale.isNullOrEmpty()) mutableList.add(sprites.backShinyFemale!!)
            if (!sprites.frontDefault.isNullOrEmpty()) mutableList.add(sprites.frontDefault!!)
            if (!sprites.frontFemale.isNullOrEmpty()) mutableList.add(sprites.frontFemale!!)
            if (!sprites.frontShiny.isNullOrEmpty()) mutableList.add(sprites.frontShiny!!)
            if (!sprites.frontShinyFemale.isNullOrEmpty()) mutableList.add(sprites.frontShinyFemale!!)
            return mutableList.toList()
        }


    inner class ImageViewHolder(private var theImage: ImageView) : ViewHolder(theImage) {
        fun bind(url: String) {
            Glide.with(itemView.context).load(url).into(theImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(ImageView(parent.context))
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position])
    }
}
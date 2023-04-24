package com.mexicandeveloper.upaxpruebatecnica.ui.pokemon

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.mexicandeveloper.upaxpruebatecnica.data.models.Sprites

class ImageGridAdapter(private val imageList: ArrayList<String>) :
    RecyclerView.Adapter<ImageGridAdapter.ImageViewHolder>() {


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
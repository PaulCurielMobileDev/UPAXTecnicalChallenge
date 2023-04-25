package com.mexicandeveloper.mylocations.ui

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class LocationsAdapter : RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>() {

    val locations = mutableListOf<String>()

    fun add(newLocation: String) {
        locations.add(newLocation)
        notifyItemInserted(locations.size - 1)
    }

    inner class LocationsViewHolder(private val myTextView: TextView) : ViewHolder(myTextView) {

        fun bind(location: String) {
            myTextView.text = location
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        val myTextView = TextView(parent.context)
        return LocationsViewHolder(myTextView)
    }

    override fun getItemCount() = locations.size

    override fun onBindViewHolder(holder: LocationsViewHolder, position: Int) {
        holder.bind(location = locations[position])
    }
}
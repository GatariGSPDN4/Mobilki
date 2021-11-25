package com.example.mapv2

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.mapv2.databinding.MarkerItemBinding
import com.example.mapv2.databinding.RecyclerItemBinding
import org.w3c.dom.Text


class MarkerRecycler(private val markers: Array<Marker>) :
    RecyclerView.Adapter<MarkerRecycler.MarkerViewHolder>() {

    inner class MarkerViewHolder(private val binding : MarkerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        var name: TextView? = null
        var city: TextView? = null
        var rate: TextView? = null

        init {
            name = binding.name
            city = binding.city
            rate = binding.rate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerViewHolder {
        return MarkerViewHolder(
            MarkerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: MarkerViewHolder, position: Int) {
        holder.name?.text = markers[position].name
        holder.city?.text = markers[position].city
        holder.rate?.text = markers[position].rate.toString()
    }

    override fun getItemCount() = markers.size

}
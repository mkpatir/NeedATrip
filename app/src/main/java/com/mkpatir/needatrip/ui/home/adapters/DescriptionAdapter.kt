package com.mkpatir.needatrip.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkpatir.needatrip.databinding.ItemDescriptionBinding

class DescriptionAdapter: RecyclerView.Adapter<DescriptionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemDescriptionBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(){

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemDescriptionBinding.inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1
}
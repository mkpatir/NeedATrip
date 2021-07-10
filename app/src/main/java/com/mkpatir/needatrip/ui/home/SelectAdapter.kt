package com.mkpatir.needatrip.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkpatir.needatrip.api.models.response.BusLocationData
import com.mkpatir.needatrip.databinding.ItemSelectBinding

class SelectAdapter: RecyclerView.Adapter<SelectAdapter.ViewHolder>() {

    private val items: MutableList<BusLocationData> = mutableListOf()

    internal var onClick:(BusLocationData) -> Unit = {}

    inner class ViewHolder(private val binding: ItemSelectBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: BusLocationData){
            binding.textStation.text = item.name.orEmpty()
            binding.textStation.setOnClickListener {
                onClick(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemSelectBinding.inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateAdapter(items: MutableList<BusLocationData>){
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

}
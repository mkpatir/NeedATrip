package com.mkpatir.needatrip.ui.journey

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkpatir.needatrip.api.models.response.BusJourneyData
import com.mkpatir.needatrip.databinding.ItemJourneyBinding

class JourneyAdapter: RecyclerView.Adapter<JourneyAdapter.ViewHolder>() {

    private val items: ArrayList<BusJourneyData> = arrayListOf()

    inner class ViewHolder(private val binding: ItemJourneyBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BusJourneyData){
            binding.viewState = JourneyViewState(item.journey)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemJourneyBinding.inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateAdapter(items: ArrayList<BusJourneyData>){
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }
}
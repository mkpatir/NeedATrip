package com.mkpatir.needatrip.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkpatir.needatrip.api.models.response.BusLocationData
import com.mkpatir.needatrip.databinding.ItemOriginDestinationBinding

class OriginDestinationAdapter: RecyclerView.Adapter<OriginDestinationAdapter.ViewHolder>() {

    private var origin: BusLocationData? = null
    private var destination: BusLocationData? = null

    private var items: ArrayList<BusLocationData> = arrayListOf()

    inner class ViewHolder(private val binding: ItemOriginDestinationBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(){
            setOriginAndDestination()
            binding.buttonChange.setOnClickListener {
                val temp = origin
                origin = destination
                destination = temp
                setOriginAndDestination()
            }
        }

        private fun setOriginAndDestination(){
            binding.apply {
                textOrigin.text = origin?.name.orEmpty()
                textDestination.text = destination?.name.orEmpty()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OriginDestinationAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemOriginDestinationBinding.inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: OriginDestinationAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

    fun updateAdapter(origin: BusLocationData?,destination: BusLocationData?,items: ArrayList<BusLocationData>?){
        this.origin = origin
        this.destination = destination
        this.items.apply {
            clear()
            items?.let { addAll(it) }
        }
        notifyDataSetChanged()
    }

    fun getOriginAndDestination(): Pair<BusLocationData?,BusLocationData?> = Pair(origin,destination)

}
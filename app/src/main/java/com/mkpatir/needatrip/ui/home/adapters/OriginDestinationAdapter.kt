package com.mkpatir.needatrip.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkpatir.needatrip.R
import com.mkpatir.needatrip.api.models.response.BusLocationData
import com.mkpatir.needatrip.databinding.ItemOriginDestinationBinding
import com.mkpatir.needatrip.internal.extention.setOnClickListeners

class OriginDestinationAdapter(
    private val type: Type
): RecyclerView.Adapter<OriginDestinationAdapter.ViewHolder>() {

    enum class Key {
        KEY_ORIGIN,
        KEY_DESTINATION
    }

    enum class Type {
        BUS,
        FLIGHT
    }

    private var origin: BusLocationData? = null
    private var destination: BusLocationData? = null

    private var items: ArrayList<BusLocationData> = arrayListOf()

    internal var onClick:(Type, Key, ArrayList<BusLocationData>) -> Unit = { type, key, list -> }
    internal var errorListener:(String) -> Unit = {}

    inner class ViewHolder(private val binding: ItemOriginDestinationBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(){
            setOriginAndDestination()
            binding.buttonChange.setOnClickListener {
                val temp = origin
                origin = destination
                destination = temp
                setOriginAndDestination()
            }
            setOnClickListeners(arrayOf(binding.textOrigin,binding.titleOrigin)) {
                onClick(type, Key.KEY_ORIGIN,items)
            }
            setOnClickListeners(arrayOf(binding.textDestination,binding.titleDestination)){
                onClick(type, Key.KEY_DESTINATION,items)
            }
        }

        private fun setOriginAndDestination(){
            binding.apply {
                textOrigin.text = origin?.name.orEmpty()
                textDestination.text = destination?.name.orEmpty()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemOriginDestinationBinding.inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

    fun updateAdapter(origin: BusLocationData?,destination: BusLocationData?,items: ArrayList<BusLocationData>){
        this.origin = origin
        this.destination = destination
        this.items.apply {
            clear()
            addAll(items)
        }
        notifyDataSetChanged()
    }

    fun getOriginAndDestination(): Pair<BusLocationData?,BusLocationData?> = Pair(origin,destination)

    fun updateLocation(context: Context, key: Key, locationData: BusLocationData) {
        when(key){
            Key.KEY_ORIGIN -> {
                when {
                    locationData.id == destination?.id -> {
                        errorListener(context.getString(R.string.error_same_location))
                    }
                    locationData.parentId == destination?.parentId -> {
                        errorListener(context.getString(R.string.error_same_city_location))
                    }
                    else -> {
                        origin = locationData
                        notifyDataSetChanged()
                    }
                }
            }
            Key.KEY_DESTINATION -> {
                when {
                    locationData.id == origin?.id -> {
                        errorListener(context.getString(R.string.error_same_location))
                    }
                    locationData.parentId == origin?.parentId -> {
                        errorListener(context.getString(R.string.error_same_city_location))
                    }
                    else -> {
                        destination = locationData
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

}
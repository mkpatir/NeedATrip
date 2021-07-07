package com.mkpatir.needatrip.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkpatir.needatrip.databinding.ItemDateForFlightBinding
import com.mkpatir.needatrip.internal.helpers.DateHelper

class DateForFlightAdapter: RecyclerView.Adapter<DateForFlightAdapter.ViewHolder>() {

    private var selectedGoingDate: String = DateHelper.getFutureDatePair().second
    private var selectedReturnDate: String? = DateHelper.getFutureDatePair(3).second

    internal var findTicketClickListener: (String,String?) -> Unit = { goingDate, returnDate -> }

    inner class ViewHolder(private val binding: ItemDateForFlightBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(){
            binding.apply {
                buttonFindTicket.setOnClickListener {
                    findTicketClickListener(selectedGoingDate,selectedReturnDate)
                }

                buttonReturnCancel.setOnClickListener {
                    selectedReturnDate = null
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemDateForFlightBinding.inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

}
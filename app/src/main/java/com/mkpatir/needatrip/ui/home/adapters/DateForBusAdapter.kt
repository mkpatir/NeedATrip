package com.mkpatir.needatrip.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mkpatir.needatrip.databinding.ItemDateForBusBinding
import com.mkpatir.needatrip.internal.extention.openDatePicker
import com.mkpatir.needatrip.internal.helpers.DateHelper
import java.util.*

class DateForBusAdapter: RecyclerView.Adapter<DateForBusAdapter.ViewHolder>() {

    private var selectedDate: String = DateHelper.getFutureDatePair().second

    internal var findTicketClickListener: (String) -> Unit = {}

    inner class ViewHolder(private val binding: ItemDateForBusBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(){
            binding.apply {
                textCalendar.text = DateHelper.getBusDateFromString(selectedDate).first
                buttonToday.setOnClickListener {
                    textCalendar.text = DateHelper.getTodayPair().first
                    selectedDate = DateHelper.getTodayPair().second
                }
                buttonTomorrow.setOnClickListener {
                    textCalendar.text = DateHelper.getFutureDatePair().first
                    selectedDate = DateHelper.getFutureDatePair().second
                }
                buttonFindTicket.setOnClickListener {
                    findTicketClickListener(selectedDate)
                }
                textCalendar.setOnClickListener {
                    textCalendar.openDatePicker(DateHelper.getDateFromString(selectedDate), Date()) {
                        selectedDate = it.first
                        textCalendar.text = it.second
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemDateForBusBinding.inflate(layoutInflater,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 1

    /**
     * Kalkış tarihini günceller.
     * */
    fun updateDate(date: String){
        selectedDate = DateHelper.getBusDateFromString(date).second
        notifyDataSetChanged()
    }
}
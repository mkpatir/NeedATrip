package com.mkpatir.needatrip.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.mkpatir.needatrip.R
import com.mkpatir.needatrip.databinding.ItemDateForFlightBinding
import com.mkpatir.needatrip.internal.extention.openDatePicker
import com.mkpatir.needatrip.internal.extention.setOnClickListeners
import com.mkpatir.needatrip.internal.helpers.DateHelper
import java.util.*

class DateForFlightAdapter: RecyclerView.Adapter<DateForFlightAdapter.ViewHolder>() {

    private var selectedGoingDate: String = DateHelper.getFutureDatePair().second
    private var selectedReturnDate: String? = DateHelper.getFutureDatePair(3).second

    private var grownCount = 1
    private var childrenCount = 0

    internal var findTicketClickListener: (String,String?) -> Unit = { goingDate, returnDate -> }
    internal var passengerClickListener:(Int,Int) -> Unit = { grown, chidren -> }

    inner class ViewHolder(private val binding: ItemDateForFlightBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(){
            binding.apply {
                setGoingTextViews()
                setReturnTextViews()

                textGrown.text = root.context.getString(R.string.grown_with_number,grownCount)

                textChildren.text = if (childrenCount > 0) root.context.getString(R.string.children_with_number,childrenCount) else ""

                buttonFindTicket.setOnClickListener {
                    findTicketClickListener(selectedGoingDate,selectedReturnDate)
                }

                buttonReturnCancel.setOnClickListener {
                    selectedReturnDate = null
                    setReturnTextViews()
                }

                bgPassenger.setOnClickListener {
                    passengerClickListener(grownCount,childrenCount)
                }

                setOnClickListeners(arrayOf(textGoingDayNumber,textGoingMonth,textGoingDay)){
                    textGoingDayNumber.openDatePicker(DateHelper.getDateFromString(selectedGoingDate), Date()){
                        selectedGoingDate = it.first
                        setGoingTextViews()
                        if (DateHelper.isAfterSecondDate(selectedReturnDate.orEmpty(),selectedGoingDate)){
                            selectedReturnDate = selectedGoingDate
                            setReturnTextViews()
                        }
                    }
                }

                setOnClickListeners(arrayOf(textReturnDayNumber,textReturnMonth,textReturnDay)){
                    textReturnDayNumber.openDatePicker(DateHelper.getDateFromString(selectedReturnDate ?: selectedGoingDate), DateHelper.getDateFromString(selectedGoingDate)){
                        selectedReturnDate = it.first
                        setReturnTextViews()
                    }
                }
            }
        }

        /**
         * Verilen tarih ile text'leri değiştirir.
         * */
        private fun setDateToTextViews(
            dayNumberView: AppCompatTextView,
            monthView: AppCompatTextView,
            dayView: AppCompatTextView,
            date: Triple<String,String,String>
        ){
            dayNumberView.text = date.first
            monthView.text = date.second
            dayView.text = date.third
        }

        /**
         * Gidiş terihi text'lerini değiştirir.
         * */
        private fun setGoingTextViews(){
            binding.apply {
                setDateToTextViews(
                    textGoingDayNumber,
                    textGoingMonth,
                    textGoingDay,
                    DateHelper.getDateForFlight(selectedGoingDate)
                )
            }
        }

        /**
         * Dönüş terihi text'lerini değiştirir.
         * */
        private fun setReturnTextViews(){
            binding.apply {
                setDateToTextViews(
                    textReturnDayNumber,
                    textReturnMonth,
                    textReturnDay,DateHelper.getDateForFlight(selectedReturnDate.orEmpty())
                )
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

    /**
     * Yeni yolcu eklendiğinde adapter'i günceller.
     * */
    fun updatePassenger(grown: Int, children: Int){
        grownCount = grown
        childrenCount = children
        notifyDataSetChanged()
    }

}
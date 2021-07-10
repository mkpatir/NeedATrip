package com.mkpatir.needatrip.internal.extention

import android.app.DatePickerDialog
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.tabs.TabLayout
import com.mkpatir.needatrip.internal.helpers.DateHelper
import java.util.*

fun setOnClickListeners(views: Array<View>, onClickListener: () -> Unit){
    views.forEach {
        it.setOnClickListener {
            onClickListener()
        }
    }
}

fun AppCompatTextView.openDatePicker(selectedDate: Date, minDate: Date,onDateSelectListener:(Pair<String,String>) -> Unit){
    val calendar = Calendar.getInstance().apply {
        time = selectedDate
    }
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelectListener(DateHelper.convertDateToPair(year,month+1,dayOfMonth))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        datePicker.minDate = minDate.time
        show()
    }
}

fun TabLayout.onTabSelected(listener:(tab: TabLayout.Tab?) -> Unit){
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            listener(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabReselected(tab: TabLayout.Tab?) {

        }

    })
}
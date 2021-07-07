package com.mkpatir.needatrip.internal.helpers

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    private const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
    private const val DATE_PATTERN_WITHOUT_HOUR = "yyyy-MM-dd"
    private const val BUS_DATE_PATTERN = "d MMMM yyyy EEEE"

    private val simpleDateFormatWithDatePattern = SimpleDateFormat(DATE_PATTERN, Locale("tr"))
    private val simpleDateFormatWithDateWithoutHourPattern = SimpleDateFormat(DATE_PATTERN_WITHOUT_HOUR, Locale("tr"))
    private val simpleDateFormatWithBusDatePattern = SimpleDateFormat(BUS_DATE_PATTERN, Locale("tr"))

    fun getCurrentTime():String = simpleDateFormatWithDatePattern.format(Date())

    fun getTodayPair(): Pair<String,String> = Pair(
        simpleDateFormatWithBusDatePattern.format(Date()),
        addZeroHourToDate(simpleDateFormatWithDateWithoutHourPattern.format(Date()))
    )

    fun getFutureDatePair(amount: Int = 1): Pair<String,String> {
        val date = Calendar.getInstance().apply {
            add(Calendar.DATE,amount)
        }.time
        return Pair(
            simpleDateFormatWithBusDatePattern.format(date),
            addZeroHourToDate(simpleDateFormatWithDateWithoutHourPattern.format(date))
        )
    }

    private fun addZeroHourToDate(date: String): String = "${date}T00:00:00"
}
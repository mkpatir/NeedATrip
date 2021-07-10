package com.mkpatir.needatrip.internal.helpers

import com.mkpatir.needatrip.internal.extention.addZeroIfSmall
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    private const val DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
    private const val DATE_PATTERN_WITHOUT_HOUR = "yyyy-MM-dd"
    private const val BUS_DATE_PATTERN = "d MMMM yyyy EEEE"
    private const val JOURNEY_DATE_PATTERN = "d MMMM EEEE"
    private const val HOUR_PATTERN = "HH:mm"

    private const val DAY_NUMBER_PATTERN = "dd"
    private const val MONTH_PATTERN = "MMMM"
    private const val DAY_STRING_PATTERN = "EEEE"

    private val sdfWithDatePattern = SimpleDateFormat(DATE_PATTERN, Locale("tr"))
    private val sdfWithDateWithoutHourPattern = SimpleDateFormat(DATE_PATTERN_WITHOUT_HOUR, Locale("tr"))
    private val sdfWithBusDatePattern = SimpleDateFormat(BUS_DATE_PATTERN, Locale("tr"))
    private val sdfWithJourneyDatePattern = SimpleDateFormat(JOURNEY_DATE_PATTERN, Locale("tr"))
    private val sdfWithHourPattern = SimpleDateFormat(HOUR_PATTERN, Locale("tr"))

    private val sdfWithDayNumberPattern = SimpleDateFormat(DAY_NUMBER_PATTERN, Locale("tr"))
    private val sdfWithMonthPattern = SimpleDateFormat(MONTH_PATTERN, Locale("tr"))
    private val sdfWithDayStringPattern = SimpleDateFormat(DAY_STRING_PATTERN, Locale("tr"))

    fun getCurrentTime():String = sdfWithDatePattern.format(Date())

    fun getDateFromString(dateString: String): Date = sdfWithDatePattern.parse(dateString) ?: Date()

    fun getTodayPair(): Pair<String,String> = Pair(
        sdfWithBusDatePattern.format(Date()),
        addZeroHourToDate(sdfWithDateWithoutHourPattern.format(Date()))
    )

    fun getFutureDatePair(amount: Int = 1): Pair<String,String> {
        val date = Calendar.getInstance().apply {
            add(Calendar.DATE,amount)
        }.time
        return Pair(
            sdfWithBusDatePattern.format(date),
            addZeroHourToDate(sdfWithDateWithoutHourPattern.format(date))
        )
    }

    fun getHour(dateString: String): String {
        return try {
            val date = sdfWithDatePattern.parse(dateString)
            sdfWithHourPattern.format(date)
        }
        catch (ex: Exception){
            ""
        }
    }

    fun convertDateToJourneyDate(dateString: String): String {
        return try {
            val date = sdfWithDatePattern.parse(dateString)
            return sdfWithJourneyDatePattern.format(date)
        }
        catch (ex: Exception){
            ""
        }
    }

    fun convertDateToPair(year: Int, month: Int, day: Int): Pair<String,String>{
        return try {
            val dateString = "${year}-${month.addZeroIfSmall()}-${day.addZeroIfSmall()}T00:00:00"
            Pair(dateString, sdfWithBusDatePattern.format(sdfWithDatePattern.parse(dateString)))
        }
        catch (ex: Exception){
            Pair("","")
        }
    }

    fun getDateForFlight(dateString: String): Triple<String,String,String>{
        return try {
            val date = sdfWithDatePattern.parse(dateString)
            Triple(sdfWithDayNumberPattern.format(date), sdfWithMonthPattern.format(date), sdfWithDayStringPattern.format(date))
        }
        catch (ex: Exception){
            Triple("","","")
        }
    }

    fun isAfterSecondDate(dateFirst: String, dateSecond: String): Boolean{
        return try {
            sdfWithDatePattern.parse(dateSecond).after(sdfWithDatePattern.parse(dateFirst))
        }
        catch (ex: Exception){
            true
        }
    }

    fun getBusDateFromString(dateString: String): Pair<String,String> {
        return try {
            Pair(
                sdfWithBusDatePattern.format(sdfWithDatePattern.parse(dateString)),
                addZeroHourToDate(sdfWithDateWithoutHourPattern.format(sdfWithDatePattern.parse(dateString)))
            )
        }
        catch (ex: Exception){
            getFutureDatePair()
        }
    }

    private fun addZeroHourToDate(date: String): String = "${date}T00:00:00"
}
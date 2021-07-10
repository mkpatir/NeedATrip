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

    /**
     * Anlık olarak tarihi döner.
     * */
    fun getCurrentTime():String = sdfWithDatePattern.format(Date())

    /**
     * String'i date objesine çevirir.
     * */
    fun getDateFromString(dateString: String): Date = sdfWithDatePattern.parse(dateString) ?: Date()

    /**
     * İki farklı formatta Pair olarak bugün'ü döner.
     * */
    fun getTodayPair(): Pair<String,String> = Pair(
        sdfWithBusDatePattern.format(Date()),
        addZeroHourToDate(sdfWithDateWithoutHourPattern.format(Date()))
    )

    /**
     * İki farklı formatta Pair olarak belirtilen gün kadar ilerisini döner.
     * */
    fun getFutureDatePair(amount: Int = 1): Pair<String,String> {
        val date = Calendar.getInstance().apply {
            add(Calendar.DATE,amount)
        }.time
        return Pair(
            sdfWithBusDatePattern.format(date),
            addZeroHourToDate(sdfWithDateWithoutHourPattern.format(date))
        )
    }

    /**
     * Date string'den saati ve dakikayı döner.
     * */
    fun getHour(dateString: String): String {
        return try {
            val date = sdfWithDatePattern.parse(dateString)
            sdfWithHourPattern.format(date)
        }
        catch (ex: Exception){
            ""
        }
    }

    /**
     * Date string'i seyahat sayfasındaki tarihe dönüştürür.
     * */
    fun convertDateToJourneyDate(dateString: String): String {
        return try {
            val date = sdfWithDatePattern.parse(dateString)
            return sdfWithJourneyDatePattern.format(date)
        }
        catch (ex: Exception){
            ""
        }
    }

    /**
     * Yıl, ay ve gün alıp tarihe dönüştürüp Pair olarak döner.
     * */
    fun convertDateToPair(year: Int, month: Int, day: Int): Pair<String,String>{
        return try {
            val dateString = "${year}-${month.addZeroIfSmall()}-${day.addZeroIfSmall()}T00:00:00"
            Pair(dateString, sdfWithBusDatePattern.format(sdfWithDatePattern.parse(dateString)))
        }
        catch (ex: Exception){
            Pair("","")
        }
    }

    /**
     * Date string alır ve uçuş seçilmiş tarihi döner. ( int olarak gün, string olarak ay, string olarak gün)
     * */
    fun getDateForFlight(dateString: String): Triple<String,String,String>{
        return try {
            val date = sdfWithDatePattern.parse(dateString)
            Triple(sdfWithDayNumberPattern.format(date), sdfWithMonthPattern.format(date), sdfWithDayStringPattern.format(date))
        }
        catch (ex: Exception){
            Triple("","","")
        }
    }

    /**
     * Verilen iki tarihi kontrol eder.
     * */
    fun isAfterSecondDate(dateFirst: String, dateSecond: String): Boolean{
        return try {
            sdfWithDatePattern.parse(dateSecond).after(sdfWithDatePattern.parse(dateFirst))
        }
        catch (ex: Exception){
            true
        }
    }

    /**
     * Verilen date string'ten otobüs biletindeki tarih alanında kullanılan tarihi döner.
     * */
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

    /**
     * Verilen tarihin sonuna sıfır ekler.
     * */
    private fun addZeroHourToDate(date: String): String = "${date}T00:00:00"
}
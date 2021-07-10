package com.mkpatir.needatrip.ui.journey

import com.mkpatir.needatrip.api.models.response.Journey
import com.mkpatir.needatrip.internal.extention.orZero
import com.mkpatir.needatrip.internal.helpers.DateHelper

/**
 * Seferlerin görüntülendiği recycler view adapter'i için kullanılır, ilgili değerleri almak içindir.
 * */
data class JourneyViewState(
    private val journey: Journey?
) {

    fun getJourney(): String = "${journey?.origin.orEmpty()} - ${journey?.destination.orEmpty()}"

    fun getPrice(): String = "${journey?.internetPrice.orZero()},00 ${journey?.currency.orEmpty()}"

    fun getDepartureHour(): String = DateHelper.getHour(journey?.departure.orEmpty())

    fun getArrivalHour(): String = DateHelper.getHour(journey?.arrival.orEmpty())

}
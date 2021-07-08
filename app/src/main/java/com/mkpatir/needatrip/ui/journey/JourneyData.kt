package com.mkpatir.needatrip.ui.journey

import android.os.Parcelable
import com.mkpatir.needatrip.api.models.response.BusLocationData
import com.mkpatir.needatrip.internal.helpers.DateHelper
import kotlinx.android.parcel.Parcelize

@Parcelize
data class JourneyData(
    val origin: BusLocationData?,
    val destination: BusLocationData?,
    val departureDate: String
) : Parcelable {

    fun getJourney(): String = "${origin?.name.orEmpty()} - ${destination?.name.orEmpty()}"

    fun getJourneyDate(): String = DateHelper.convertDateToJourneyDate(departureDate)

}
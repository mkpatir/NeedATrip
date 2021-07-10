package com.mkpatir.needatrip.api.models.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Seyahat bilgilerini çekmek için kullanılan request'tir.
 * */

data class BusJourneyRequest(
    @SerializedName("data") val data: BusJourneyRequestData
) : BaseRequest()

@Parcelize
data class BusJourneyRequestData(
    @SerializedName("origin-id") val originId: Int,
    @SerializedName("destination-id") val destinationId: Int,
    @SerializedName("departure-date") val departureDate: String
): Parcelable
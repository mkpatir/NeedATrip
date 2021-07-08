package com.mkpatir.needatrip.api.models.response

import com.google.gson.annotations.SerializedName

data class BusJourneyResponse(
    @SerializedName("data") val data: ArrayList<BusJourneyData>?
) : BaseResponse()

data class BusJourneyData(
    @SerializedName("journey") val journey: Journey?
)

data class Journey(
    @SerializedName("origin") val origin: String?,
    @SerializedName("destination") val destination: String?,
    @SerializedName("departure") val departure: String?,
    @SerializedName("arrival") val arrival: String?,
    @SerializedName("currency") val currency: String?,
    @SerializedName("duration") val duration: String?,
    @SerializedName("original-price") val originalPrice: Int?,
    @SerializedName("internet-price") val internetPrice: Int?
)
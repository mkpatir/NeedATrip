package com.mkpatir.needatrip.api.models.response

import com.google.gson.annotations.SerializedName

data class BusLocationResponse(
    @SerializedName("data") val data: ArrayList<BusLocationData>?
): BaseResponse()

data class BusLocationData(
    @SerializedName("id") val id: Int?,
    @SerializedName("parent-id") val parentId: Int?,
    @SerializedName("type") val type: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("geo-location") val geoLocation: GeoLocationModel?
)

data class GeoLocationModel(
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("zoom") val zoom: Int?
)
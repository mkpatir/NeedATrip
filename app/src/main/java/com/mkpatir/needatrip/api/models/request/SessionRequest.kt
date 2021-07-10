package com.mkpatir.needatrip.api.models.request

import com.google.gson.annotations.SerializedName
import com.mkpatir.needatrip.BuildConfig

/**
 * Session oluşturmak için kullanılan request'tir.
 * */

data class SessionRequest(
    @SerializedName("type") val type: Int = 3,
    @SerializedName("connection") val connection: Connection,
    @SerializedName("application") val application: Application
)

data class Connection(
    @SerializedName("ip-address") val ipAddress: String
)

data class Application(
    @SerializedName("version") val version: String = BuildConfig.VERSION_NAME,
    @SerializedName("equipment-id") val equipmentId: String
)
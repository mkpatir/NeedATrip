package com.mkpatir.needatrip.api.models

import com.google.gson.annotations.SerializedName

data class SessionModel(
    @SerializedName("session-id") val sessionId: String?,
    @SerializedName("device-id") val deviceId: String?
)
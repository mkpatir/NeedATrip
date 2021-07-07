package com.mkpatir.needatrip.api.models.request

import com.google.gson.annotations.SerializedName
import com.mkpatir.needatrip.api.models.SessionModel

class BaseRequest {
    @SerializedName("device-session") var deviceSession: SessionModel? = null
    @SerializedName("date") var date: String? = null
    @SerializedName("language") var language: String = "tr-TR"
}
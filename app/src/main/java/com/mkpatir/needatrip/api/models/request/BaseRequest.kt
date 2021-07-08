package com.mkpatir.needatrip.api.models.request

import com.google.gson.annotations.SerializedName
import com.mkpatir.needatrip.api.models.SessionModel
import com.mkpatir.needatrip.internal.helpers.DateHelper

open class BaseRequest {
    @SerializedName("device-session") var deviceSession: SessionModel? = null
    @SerializedName("date") var date: String = DateHelper.getCurrentTime()
    @SerializedName("language") var language: String = "tr-TR"
}
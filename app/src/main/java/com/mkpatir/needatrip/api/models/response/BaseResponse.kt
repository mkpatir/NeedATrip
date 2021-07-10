package com.mkpatir.needatrip.api.models.response

import com.google.gson.annotations.SerializedName

/**
 * Genel response'dur, bütün response'lar buradan extend alır.
 * */
open class BaseResponse {
    @SerializedName("status") var status: String? = null
    @SerializedName("message") var message: String? = null
    @SerializedName("user-message") var userMessage: String? = null
    @SerializedName("api-request-id") var apiRequestId: String? = null
    @SerializedName("controller") var controller: String? = null
}
package com.mkpatir.needatrip.api.models.response

import com.google.gson.annotations.SerializedName
import com.mkpatir.needatrip.api.models.SessionModel

data class SessionResponse(
    @SerializedName("data") val data: SessionModel?
): BaseResponse()
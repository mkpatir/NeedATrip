package com.mkpatir.needatrip.api

import com.mkpatir.needatrip.api.models.request.SessionRequest
import com.mkpatir.needatrip.api.models.response.SessionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AppService {

    @POST("client/getsession")
    suspend fun getSession(@Body sessionRequest: SessionRequest): SessionResponse

}
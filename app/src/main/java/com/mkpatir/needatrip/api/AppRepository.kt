package com.mkpatir.needatrip.api

import com.mkpatir.needatrip.api.models.request.BaseRequest
import com.mkpatir.needatrip.api.models.request.SessionRequest
import com.mkpatir.needatrip.api.models.response.BusLocationResponse
import com.mkpatir.needatrip.api.models.response.SessionResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val appService: AppService
) {

    suspend fun getSession(sessionRequest: SessionRequest): Flow<SessionResponse> = flow { emit(appService.getSession(sessionRequest)) }

    suspend fun getBusLocations(baseRequest: BaseRequest): Flow<BusLocationResponse> = flow { emit(appService.getBusLocations(baseRequest)) }

}
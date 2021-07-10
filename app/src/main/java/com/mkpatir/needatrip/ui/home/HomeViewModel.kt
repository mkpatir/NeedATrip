package com.mkpatir.needatrip.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mkpatir.needatrip.api.AppRepository
import com.mkpatir.needatrip.api.models.request.Application
import com.mkpatir.needatrip.api.models.request.BaseRequest
import com.mkpatir.needatrip.api.models.request.Connection
import com.mkpatir.needatrip.api.models.request.SessionRequest
import com.mkpatir.needatrip.api.models.response.BusLocationData
import com.mkpatir.needatrip.api.models.response.BusLocationResponse
import com.mkpatir.needatrip.internal.extention.orArrayList
import com.mkpatir.needatrip.internal.helpers.ApplicationHelper
import com.mkpatir.needatrip.internal.helpers.SharedPrefHelper
import com.mkpatir.needatrip.ui.base.BaseViewModel
import com.mkpatir.needatrip.ui.home.adapters.OriginDestinationAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val sharedPrefHelper: SharedPrefHelper
): BaseViewModel() {

    val busLocationsLiveData = MutableLiveData<Triple<BusLocationData?,BusLocationData?,ArrayList<BusLocationData>>>()
    val selectLocationLiveData = MutableLiveData<Triple<OriginDestinationAdapter.Type, OriginDestinationAdapter.Key,BusLocationData>>()
    val passengerChangeLiveData = MutableLiveData<Pair<Int,Int>>()

    init {
        getSession()
    }

    fun saveBusHistory(originId: Int?, destinationId: Int?, departureDate: String){
        sharedPrefHelper.busHistory = HistoryModel(originId, destinationId, departureDate)
    }

    fun getBusHistoryDate() = sharedPrefHelper.busHistory?.departureDate.orEmpty()

    private fun getSession() {
        val sessionRequest = SessionRequest(
            connection = Connection(ApplicationHelper.getIpv4HostAddress()),
            application = Application(
                equipmentId = getOrCreateEquipmentId()
            )
        )
        viewModelScope.launch {
            callService(appRepository.getSession(sessionRequest)) {
                sharedPrefHelper.session = it.data
                getBusLocations()
            }
        }
    }

    private fun getBusLocations(){
        val request = BaseRequest().apply {
            deviceSession = sharedPrefHelper.session
        }
        viewModelScope.launch {
            callService(appRepository.getBusLocations(request)){
                val origin = getOrigin(it)
                val destination = getDestination(it,origin)
                busLocationsLiveData.value = Triple(origin,destination,it.data.orArrayList())
            }
        }
    }

    private fun getOrCreateEquipmentId(): String {
        sharedPrefHelper.equipmentId?.let {
            return it
        } ?: run {
            val id = UUID.randomUUID().toString()
            sharedPrefHelper.equipmentId = id
            return id
        }
    }

    private fun getOrigin(response: BusLocationResponse): BusLocationData? {
        sharedPrefHelper.busHistory?.let {
            return response.data?.find { item -> item.id == it.originId }
        } ?: run {
            return if (response.data?.isNotEmpty() == true) response.data[0] else null
        }
    }

    private fun getDestination(response: BusLocationResponse, origin: BusLocationData?): BusLocationData? {
        sharedPrefHelper.busHistory?.let {
            return response.data?.find { item -> item.id == it.destinationId }
        } ?: run {
            return response.data?.find { item -> item.id != origin?.id && item.parentId != origin?.parentId }
        }
    }
}
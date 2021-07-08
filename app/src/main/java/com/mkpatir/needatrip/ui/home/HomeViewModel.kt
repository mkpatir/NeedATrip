package com.mkpatir.needatrip.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mkpatir.needatrip.api.AppRepository
import com.mkpatir.needatrip.api.models.request.Application
import com.mkpatir.needatrip.api.models.request.BaseRequest
import com.mkpatir.needatrip.api.models.request.Connection
import com.mkpatir.needatrip.api.models.request.SessionRequest
import com.mkpatir.needatrip.api.models.response.BusLocationData
import com.mkpatir.needatrip.internal.helpers.SharedPrefHelper
import com.mkpatir.needatrip.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.Inet4Address
import java.net.NetworkInterface
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val sharedPrefHelper: SharedPrefHelper
): BaseViewModel() {

    val busLocationsLiveData = MutableLiveData<Triple<BusLocationData?,BusLocationData?,ArrayList<BusLocationData>?>>()
    val selectLocationLiveData = MutableLiveData<Pair<OriginDestinationAdapter.Key,BusLocationData>>()

    init {
        getSession()
    }

    fun findTicket(origin: BusLocationData?, destination: BusLocationData?, date: String){

    }

    private fun getSession() {
        val sessionRequest = SessionRequest(
            connection = Connection(getIpv4HostAddress()),
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
                val origin = if (it.data?.isNotEmpty() == true) it.data[0] else null
                val destination = it.data?.find { item -> item.id != origin?.id && item.parentId != origin?.parentId }
                busLocationsLiveData.value = Triple(origin,destination,it.data)
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

    private fun getIpv4HostAddress(): String {
        NetworkInterface.getNetworkInterfaces()?.toList()?.map { networkInterface ->
            networkInterface.inetAddresses?.toList()?.find {
                !it.isLoopbackAddress && it is Inet4Address
            }?.let { return it.hostAddress }
        }
        return ""
    }
}
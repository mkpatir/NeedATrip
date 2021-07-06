package com.mkpatir.needatrip.ui.home

import androidx.lifecycle.viewModelScope
import com.mkpatir.needatrip.api.AppRepository
import com.mkpatir.needatrip.api.models.request.Application
import com.mkpatir.needatrip.api.models.request.Connection
import com.mkpatir.needatrip.api.models.request.SessionRequest
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

    fun getSession() {
        val sessionRequest = SessionRequest(
            connection = Connection(getIpv4HostAddress()),
            application = Application(
                equipmentId = getOrCreateEquipmentId()
            )
        )
        viewModelScope.launch {
            callService(appRepository.getSession(sessionRequest)) {
                sharedPrefHelper.session = it.data
                getIpv4HostAddress()
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
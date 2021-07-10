package com.mkpatir.needatrip.internal.helpers

import java.net.Inet4Address
import java.net.NetworkInterface

object ApplicationHelper {

    fun getIpv4HostAddress(): String {
        NetworkInterface.getNetworkInterfaces()?.toList()?.map { networkInterface ->
            networkInterface.inetAddresses?.toList()?.find {
                !it.isLoopbackAddress && it is Inet4Address
            }?.let { return it.hostAddress }
        }
        return ""
    }

}
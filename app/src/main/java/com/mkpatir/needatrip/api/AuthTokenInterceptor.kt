package com.mkpatir.needatrip.api

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Request'e token eklemek için kullanılır.
 * */
class AuthTokenInterceptor : Interceptor {

    private val apiClientToken = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder().apply {
            addHeader("Content-Type","application/json")
            addHeader("Authorization","Basic $apiClientToken")
        }
        return chain.proceed(requestBuilder.build())
    }
}
package com.mkpatir.needatrip.internal.helpers

import android.content.Context
import com.google.gson.Gson
import com.mkpatir.needatrip.api.models.SessionModel
import javax.inject.Inject

class SharedPrefHelper @Inject constructor (
    context: Context
) {

    companion object {
        private const val APP_NAME = "com.mkpatir.needatrip"
        private const val EQUIPMENT_ID = "equipment-id"
        private const val SESSION = "session"
    }

    private val sharedPref = context.getSharedPreferences(APP_NAME,Context.MODE_PRIVATE)

    var equipmentId: String?
        get() = sharedPref.getString(EQUIPMENT_ID,null)
        set(value) = sharedPref.edit().putString(EQUIPMENT_ID,value).apply()

    var session: SessionModel?
        get() = getObjectFromJson(sharedPref.getString(SESSION,null))
        set(value) = sharedPref.edit().putString(SESSION,convertObjectToJson(value)).apply()

    private fun <T> convertObjectToJson(data: T?): String = Gson().toJson(data)

    private inline fun <reified T> getObjectFromJson(json: String?): T? = Gson().fromJson(json,T::class.java)
}
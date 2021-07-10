package com.mkpatir.needatrip.ui.home

import androidx.lifecycle.MutableLiveData
import com.mkpatir.needatrip.api.models.response.BusLocationData
import com.mkpatir.needatrip.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class SelectViewModel @Inject constructor() : BaseViewModel() {

    private val busLocationList: ArrayList<BusLocationData> = arrayListOf()

    val busLocationsLiveData = MutableLiveData<MutableList<BusLocationData>>()

    /**
     * Konumların olduğu liste view modele yazılıyor.
     * */
    fun setBusLocationList(busLocationList: ArrayList<BusLocationData>) {
        this.busLocationList.clear()
        this.busLocationList.addAll(busLocationList)
        busLocationsLiveData.postValue(busLocationList)
    }

    /**
     * View model'e yazılmış olan liste içinde arama yapar.
     * */
    fun search(text: String){
        if (text.length >= 3){
            val list = busLocationList.filter { item -> item.name?.toLowerCase(Locale("tr"))?.contains(text.toLowerCase(Locale("tr"))) == true }.toMutableList()
            busLocationsLiveData.postValue(list)
        }
        else {
            busLocationsLiveData.postValue(busLocationList)
        }
    }
}
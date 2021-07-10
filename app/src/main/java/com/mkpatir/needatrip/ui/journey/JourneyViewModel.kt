package com.mkpatir.needatrip.ui.journey

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mkpatir.needatrip.api.AppRepository
import com.mkpatir.needatrip.api.models.request.BusJourneyRequest
import com.mkpatir.needatrip.api.models.request.BusJourneyRequestData
import com.mkpatir.needatrip.api.models.response.BusJourneyData
import com.mkpatir.needatrip.internal.extention.orArrayList
import com.mkpatir.needatrip.internal.helpers.SharedPrefHelper
import com.mkpatir.needatrip.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JourneyViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val sharedPrefHelper: SharedPrefHelper
) : BaseViewModel() {

    val journeys = MutableLiveData<ArrayList<BusJourneyData>>()

    /**
     * Servisten seferleri getirir.
     * */
    fun getBusJourneys(busJourneyRequestData: BusJourneyRequestData){
        viewModelScope.launch {
            val request = BusJourneyRequest(busJourneyRequestData).apply {
                deviceSession = sharedPrefHelper.session
            }
            callService(appRepository.getBusJourneys(request)){
                journeys.value = it.data.orArrayList()
            }
        }
    }
}
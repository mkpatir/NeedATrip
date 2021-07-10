package com.mkpatir.needatrip.ui.home

import com.mkpatir.needatrip.databinding.ActivityHomeBinding
import com.mkpatir.needatrip.ui.base.BaseActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.mkpatir.needatrip.R
import com.mkpatir.needatrip.internal.extention.onTabSelected
import com.mkpatir.needatrip.internal.extention.showToast
import com.mkpatir.needatrip.ui.base.Navigator
import com.mkpatir.needatrip.ui.home.adapters.DateForBusAdapter
import com.mkpatir.needatrip.ui.home.adapters.DateForFlightAdapter
import com.mkpatir.needatrip.ui.home.adapters.DescriptionAdapter
import com.mkpatir.needatrip.ui.home.adapters.OriginDestinationAdapter
import com.mkpatir.needatrip.ui.journey.JourneyData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity: BaseActivity<ActivityHomeBinding,HomeViewModel>() {

    private val descriptionAdapter = DescriptionAdapter()

    private var busOriginDestinationAdapter = OriginDestinationAdapter(OriginDestinationAdapter.Type.BUS)
    private var dateForBusAdapter = DateForBusAdapter()
    private var busAdapter = ConcatAdapter(
        busOriginDestinationAdapter,
        dateForBusAdapter,
        descriptionAdapter
    )

    private var flightOriginDestinationAdapter = OriginDestinationAdapter(OriginDestinationAdapter.Type.FLIGHT)
    private var dateForFlightAdapter = DateForFlightAdapter()
    private var flightAdapter = ConcatAdapter(
        flightOriginDestinationAdapter,
        dateForFlightAdapter,
        descriptionAdapter
    )

    override fun setLayout(): Int = R.layout.activity_home

    override fun setViewModel(): Lazy<HomeViewModel> = viewModels()

    override fun setupUI() {
        initObservers()
        initListeners()
        getDataBinding().recyclerView.adapter = busAdapter
        dateForBusAdapter.updateDate(getViewModel().getBusHistoryDate())
    }

    private fun initObservers(){
        getViewModel().apply {
            busLocationsLiveData.observe(this@HomeActivity, {
                busOriginDestinationAdapter.updateAdapter(it.first,it.second,it.third)
                flightOriginDestinationAdapter.updateAdapter(it.first,it.second,it.third)
            })
            selectLocationLiveData.observe(this@HomeActivity, {
                when(it.first){
                    OriginDestinationAdapter.Type.BUS -> {
                        busOriginDestinationAdapter.updateLocation(this@HomeActivity,it.second,it.third)
                    }
                    OriginDestinationAdapter.Type.FLIGHT -> {
                        flightOriginDestinationAdapter.updateLocation(this@HomeActivity,it.second,it.third)
                    }
                }
            })
            passengerChangeLiveData.observe(this@HomeActivity){
                dateForFlightAdapter.updatePassenger(it.first,it.second)
            }
        }
    }

    private fun initListeners(){
        getDataBinding().apply {
            tabs.onTabSelected {
                when(it?.position){
                    0 -> recyclerView.adapter = busAdapter
                    1 -> recyclerView.adapter = flightAdapter
                }
            }
        }
        dateForBusAdapter.findTicketClickListener = { date ->
            val originAndDestination = busOriginDestinationAdapter.getOriginAndDestination()
            val journeyData = JourneyData(
                origin = originAndDestination.first,
                destination = originAndDestination.second,
                departureDate = date
            )
            getViewModel().saveBusHistory(originAndDestination.first?.id,originAndDestination.second?.id,date)
            Navigator.navigateToJourney(this,journeyData)
        }
        dateForFlightAdapter.findTicketClickListener = { goingDate, _ ->
            val originAndDestination = flightOriginDestinationAdapter.getOriginAndDestination()
            val journeyData = JourneyData(
                origin = originAndDestination.first,
                destination = originAndDestination.second,
                departureDate = goingDate
            )
            Navigator.navigateToJourney(this,journeyData)
        }
        dateForFlightAdapter.passengerClickListener = { grown, children ->
            SelectPassengerSheet.show(supportFragmentManager, grown, children)
        }
        busOriginDestinationAdapter.onClick = { type, key, list ->
            SelectBottomSheet.show(supportFragmentManager,type, key, list)
        }
        flightOriginDestinationAdapter.onClick = { type, key, list ->
            SelectBottomSheet.show(supportFragmentManager,type, key, list)
        }
        busOriginDestinationAdapter.errorListener = {
            showToast(it)
        }
        flightOriginDestinationAdapter.errorListener = {
            showToast(it)
        }
    }
}
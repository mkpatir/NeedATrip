package com.mkpatir.needatrip.ui.home

import com.mkpatir.needatrip.databinding.ActivityHomeBinding
import com.mkpatir.needatrip.ui.base.BaseActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.mkpatir.needatrip.R
import com.mkpatir.needatrip.internal.extention.showToast
import com.mkpatir.needatrip.ui.base.Navigator
import com.mkpatir.needatrip.ui.journey.JourneyData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity: BaseActivity<ActivityHomeBinding,HomeViewModel>() {

    private var busOriginDestinationAdapter = OriginDestinationAdapter()
    private var dateForBusAdapter = DateForBusAdapter()
    private var busAdapter = ConcatAdapter(
        busOriginDestinationAdapter,
        dateForBusAdapter
    )

    private var flightOriginDestinationAdapter = OriginDestinationAdapter()
    private var dateForFlightAdapter = DateForFlightAdapter()
    private var flightAdapter = ConcatAdapter(
        flightOriginDestinationAdapter,
        dateForFlightAdapter
    )

    override fun setLayout(): Int = R.layout.activity_home

    override fun setViewModel(): Lazy<HomeViewModel> = viewModels()

    override fun setupUI() {
        initObservers()
        initListeners()
        getDataBinding().recyclerView.adapter = busAdapter
    }

    private fun initObservers(){
        getViewModel().apply {
            busLocationsLiveData.observe(this@HomeActivity, {
                busOriginDestinationAdapter.updateAdapter(it.first,it.second,it.third)
            })
            selectLocationLiveData.observe(this@HomeActivity, {
                busOriginDestinationAdapter.updateLocation(this@HomeActivity,it.first,it.second)
            })
        }
    }

    private fun initListeners(){
        dateForBusAdapter.findTicketClickListener = { date ->
            val originAndDestination = busOriginDestinationAdapter.getOriginAndDestination()
            val journeyData = JourneyData(
                origin = originAndDestination.first,
                destination = originAndDestination.second,
                departureDate = date
            )
            Navigator.navigateToJourney(this,journeyData)
        }
        busOriginDestinationAdapter.onClick = { key, list ->
            SelectBottomSheet.show(supportFragmentManager,key, list)
        }
        busOriginDestinationAdapter.errorListener = {
            showToast(it)
        }
    }
}
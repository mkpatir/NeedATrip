package com.mkpatir.needatrip.ui.home

import com.mkpatir.needatrip.databinding.ActivityHomeBinding
import com.mkpatir.needatrip.ui.base.BaseActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.mkpatir.needatrip.R
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
        }
    }

    private fun initListeners(){
        dateForBusAdapter.findTicketClickListener = { date ->
            val originAndDestination = busOriginDestinationAdapter.getOriginAndDestination()
            getViewModel().findTicket(originAndDestination.first,originAndDestination.second,date)
        }
    }
}
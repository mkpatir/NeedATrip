package com.mkpatir.needatrip.ui.journey

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.mkpatir.needatrip.R
import com.mkpatir.needatrip.api.models.request.BusJourneyRequestData
import com.mkpatir.needatrip.databinding.ActivityJourneyBinding
import com.mkpatir.needatrip.internal.extention.orZero
import com.mkpatir.needatrip.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JourneyActivity: BaseActivity<ActivityJourneyBinding,JourneyViewModel>() {

    private val journeyAdapter = JourneyAdapter()

    companion object {
        private const val BUS_JOURNEY_REQUEST_DATA = "BUS_JOURNEY_REQUEST_DATA"

        fun newIntent(context: Context, journeyData: JourneyData): Intent = Intent(context,JourneyActivity::class.java).apply {
            putExtra(BUS_JOURNEY_REQUEST_DATA, journeyData)
        }
    }

    override fun setLayout(): Int = R.layout.activity_journey

    override fun setViewModel(): Lazy<JourneyViewModel> = viewModels()

    override fun setupUI() {
        getDataBinding().rvJourney.adapter = journeyAdapter
        initObservers()
        initListeners()
        handleIntent()
    }

    private fun handleIntent(){
        intent.getParcelableExtra<JourneyData>(BUS_JOURNEY_REQUEST_DATA)?.let {
            val busJourneyRequestData = BusJourneyRequestData(
                originId = it.origin?.id.orZero(),
                destinationId = it.destination?.id.orZero(),
                departureDate = it.departureDate
            )
            getViewModel().getBusJourneys(busJourneyRequestData)
            getDataBinding().journeyData = it
        }
    }

    private fun initObservers(){
        getViewModel().apply {
            journeys.observe(this@JourneyActivity, {
                journeyAdapter.updateAdapter(it)
            })
        }
    }

    private fun initListeners(){
        getDataBinding().buttonBack.setOnClickListener {
            finish()
        }
    }
}
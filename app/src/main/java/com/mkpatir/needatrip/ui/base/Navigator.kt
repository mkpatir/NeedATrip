package com.mkpatir.needatrip.ui.base

import android.content.Context
import com.mkpatir.needatrip.ui.journey.JourneyActivity
import com.mkpatir.needatrip.ui.journey.JourneyData

object Navigator {

    fun navigateToJourney(context: Context,journeyData: JourneyData){
        context.startActivity(JourneyActivity.newIntent(context,journeyData))
    }

}
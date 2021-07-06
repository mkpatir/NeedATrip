package com.mkpatir.needatrip.ui.home

import com.mkpatir.needatrip.databinding.ActivityHomeBinding
import com.mkpatir.needatrip.ui.base.BaseActivity
import androidx.activity.viewModels
import com.mkpatir.needatrip.R

class HomeActivity: BaseActivity<ActivityHomeBinding,HomeViewModel>() {

    override fun setLayout(): Int = R.layout.activity_home

    override fun setViewModel(): Lazy<HomeViewModel> = viewModels()

    override fun setupUI() {

    }
}
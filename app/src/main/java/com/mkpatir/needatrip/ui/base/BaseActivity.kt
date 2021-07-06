package com.mkpatir.needatrip.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<D: ViewDataBinding,VM: BaseViewModel>: AppCompatActivity() {

    private lateinit var dataBinding: D
    private lateinit var viewModel: VM

    private var loadingFullScreen: LoadingFullScreen = LoadingFullScreen().apply {
        isCancelable = false
    }

    abstract fun setLayout(): Int

    abstract fun setViewModel(): Lazy<VM>

    abstract fun setupUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this,setLayout())
        setupUI()
        initObservers()
    }

    fun getDataBinding() = dataBinding

    fun getViewModel() = setViewModel().value

    private fun initObservers(){
        getViewModel().apply {
            progressLiveData.observe(this@BaseActivity){
                if (it){
                    loadingFullScreen.show(supportFragmentManager, LOADING_TAG)
                }
                else {
                    loadingFullScreen.dismiss()
                }
            }
        }
    }

    companion object {
        private const val LOADING_TAG = "Loading"
    }
}
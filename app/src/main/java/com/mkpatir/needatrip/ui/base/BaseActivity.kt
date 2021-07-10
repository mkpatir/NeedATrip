package com.mkpatir.needatrip.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<D: ViewDataBinding,VM: BaseViewModel>: AppCompatActivity() {

    private lateinit var dataBinding: D
    private lateinit var viewModel: VM

    /**
     * Servis çağrıları sırasında görünen tam ekran loading içindir.
     * */
    private var loadingFullScreen: LoadingFullScreen = LoadingFullScreen().apply {
        isCancelable = false
    }

    /**
     * Laout id alıp data binding ile sayfayı oluşturmak içindir.
     * */
    abstract fun setLayout(): Int

    /**
     * View modeli almak içindir.
     * */
    abstract fun setViewModel(): Lazy<VM>

    /**
     * Sayfa oluştuktan sonra gerekli ui işlemlerini yapmak içindir.
     * */
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
                    if (loadingFullScreen.isAdded.not()){
                        loadingFullScreen.show(supportFragmentManager, LOADING_TAG)
                    }
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
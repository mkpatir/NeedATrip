package com.mkpatir.needatrip.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<VM :BaseViewModel,D : ViewDataBinding>: BottomSheetDialogFragment() {

    private lateinit var dataBinding: D
    private lateinit var viewModel: VM

    abstract fun setLayout(): Int

    abstract fun setViewModel(): Lazy<VM>

    abstract fun setupUI()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,setLayout(),container,false)
        viewModel = setViewModel().value
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    fun getViewModel() = setViewModel().value

    fun getDataBinding() = dataBinding

}
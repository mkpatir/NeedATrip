package com.mkpatir.needatrip.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseBottomSheetDialogFragment<VM : BaseViewModel, D : ViewDataBinding>: BottomSheetDialogFragment() {

    private lateinit var dataBinding: D
    private lateinit var viewModel: VM

    abstract fun setLayout(): Int

    abstract fun setViewModel(): Lazy<VM>

    abstract fun setupUI()

    abstract fun isFullScreen(): Boolean

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, setLayout(), container, false)
        viewModel = setViewModel().value
        return dataBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        if (isFullScreen()){
            dialog.setOnShowListener {
                val bottomSheetDialog = it as BottomSheetDialog
                val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                parentLayout?.let { layout ->
                    val behaviour = BottomSheetBehavior.from(layout)
                    setupFullHeight(layout)
                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    fun getViewModel() = setViewModel().value

    fun getDataBinding() = dataBinding

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

}
package com.mkpatir.needatrip.ui.home

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.mkpatir.needatrip.R
import com.mkpatir.needatrip.databinding.SheetPassengerSelectBinding
import com.mkpatir.needatrip.ui.base.BaseBottomSheetDialogFragment
import com.mkpatir.needatrip.ui.base.EmptyViewModel

class SelectPassengerSheet: BaseBottomSheetDialogFragment<EmptyViewModel,SheetPassengerSelectBinding>() {

    private lateinit var parentViewModel: HomeViewModel

    companion object {

        private const val TAG = "SELECT_PASSENGER"
        private const val GROWN = "GROWN"
        private const val CHILDREN = "CHILDREN"

        fun show(fm: FragmentManager, grown: Int, children: Int){
            val bundle = Bundle().apply {
                putInt(GROWN,grown)
                putInt(CHILDREN,children)
            }
            SelectPassengerSheet().apply {
                arguments = bundle
            }.show(fm, TAG)
        }

    }

    override fun setLayout(): Int = R.layout.sheet_passenger_select

    override fun setViewModel(): Lazy<EmptyViewModel> = viewModels()

    override fun isFullScreen(): Boolean = false

    override fun setupUI() {
        parentViewModel = (requireActivity() as HomeActivity).getViewModel()
        getDataBinding().apply {
            pickerGrown.minValue = 1
            pickerGrown.maxValue = 20
            pickerGrown.value = requireArguments().getInt(GROWN,1)
            pickerChildren.maxValue = 20
            pickerChildren.value = requireArguments().getInt(CHILDREN,0)

            buttonSelect.setOnClickListener {
                parentViewModel.passengerChangeLiveData.value = Pair(pickerGrown.value,pickerChildren.value)
                dismiss()
            }
        }
    }
}
package com.mkpatir.needatrip.ui.home

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.mkpatir.needatrip.R
import com.mkpatir.needatrip.api.models.response.BusLocationData
import com.mkpatir.needatrip.databinding.SheetSelectBinding
import com.mkpatir.needatrip.ui.base.BaseBottomSheetDialogFragment
import com.mkpatir.needatrip.ui.base.EmptyViewModel

class SelectBottomSheet: BaseBottomSheetDialogFragment<EmptyViewModel,SheetSelectBinding>() {

    private val selectAdapter = SelectAdapter()
    private lateinit var parentViewModel: HomeViewModel

    companion object {
        private const val TAG = "SELECT_SHEET"
        private const val LOCATION_LIST = "LOCATION_LIST"
        private const val KEY = "KEY"

        fun show(fm: FragmentManager, key: OriginDestinationAdapter.Key, busLocationsList: ArrayList<BusLocationData>){
            val bundle = Bundle().apply {
                putParcelableArrayList(LOCATION_LIST,busLocationsList)
                putSerializable(KEY,key)
            }
            SelectBottomSheet().apply {
                arguments = bundle
            }.show(fm, TAG)
        }

    }

    override fun setLayout(): Int = R.layout.sheet_select

    override fun setViewModel(): Lazy<EmptyViewModel> = viewModels()

    override fun setupUI() {
        parentViewModel = (requireActivity() as HomeActivity).getViewModel()
        getDataBinding().rvSelect.adapter = selectAdapter
        requireArguments().apply {
            getParcelableArrayList<BusLocationData>(LOCATION_LIST)?.let {
                selectAdapter.updateAdapter(it)
            }
        }
        initListeners()
    }

    private fun initListeners(){
        selectAdapter.onClick = {
            parentViewModel.selectLocationLiveData.value = Pair(getKey(),it)
            dismiss()
        }
    }

    private fun getKey() = requireArguments().getSerializable(KEY) as OriginDestinationAdapter.Key
}
package com.mkpatir.needatrip.ui.home

import android.os.Bundle
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.mkpatir.needatrip.R
import com.mkpatir.needatrip.api.models.response.BusLocationData
import com.mkpatir.needatrip.databinding.SheetSelectBinding
import com.mkpatir.needatrip.ui.base.BaseBottomSheetDialogFragment
import com.mkpatir.needatrip.ui.home.adapters.OriginDestinationAdapter

class SelectBottomSheet: BaseBottomSheetDialogFragment<SelectViewModel,SheetSelectBinding>() {

    private val selectAdapter = SelectAdapter()
    private lateinit var parentViewModel: HomeViewModel

    companion object {
        private const val TAG = "SELECT_SHEET"
        private const val LOCATION_LIST = "LOCATION_LIST"
        private const val KEY = "KEY"
        private const val TYPE = "TYPE"

        fun show(fm: FragmentManager, type: OriginDestinationAdapter.Type, key: OriginDestinationAdapter.Key, busLocationsList: ArrayList<BusLocationData>){
            val bundle = Bundle().apply {
                putParcelableArrayList(LOCATION_LIST,busLocationsList)
                putSerializable(KEY,key)
                putSerializable(TYPE,type)
            }
            SelectBottomSheet().apply {
                arguments = bundle
            }.show(fm, TAG)
        }

    }

    override fun setLayout(): Int = R.layout.sheet_select

    override fun setViewModel(): Lazy<SelectViewModel> = viewModels()

    override fun isFullScreen(): Boolean = true

    override fun setupUI() {
        parentViewModel = (requireActivity() as HomeActivity).getViewModel()
        getDataBinding().rvSelect.adapter = selectAdapter
        requireArguments().apply {
            getParcelableArrayList<BusLocationData>(LOCATION_LIST)?.let {
                getViewModel().setBusLocationList(it)
            }
        }
        initListeners()
        initObservers()
    }

    private fun initListeners(){
        selectAdapter.onClick = {
            parentViewModel.selectLocationLiveData.value = Triple(getType(),getKey(),it)
            dismiss()
        }
        getDataBinding().apply {
            edtSearch.doOnTextChanged { _, _, _, _ ->
                getViewModel().search(edtSearch.text.toString())
            }
        }
    }

    private fun initObservers(){
        getViewModel().apply {
            busLocationsLiveData.observe(this@SelectBottomSheet){
                selectAdapter.updateAdapter(it)
            }
        }
    }

    private fun getKey() = requireArguments().getSerializable(KEY) as OriginDestinationAdapter.Key

    private fun getType() = requireArguments().getSerializable(TYPE) as OriginDestinationAdapter.Type

}
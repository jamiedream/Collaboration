package com.viwave.collaborationproject.fragments

import android.os.Bundle
import android.view.*
import android.widget.EditText
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.utils.InputControlUtil
import com.viwave.collaborationproject.utils.InputFormatUtil
import com.viwave.collaborationproject.utils.LogUtil
import java.lang.ref.WeakReference

class ManualInputFragment(private val bioType: BioLiveData.Companion.BioType): BaseFragment(), BackPressedDelegate {

    override fun onBackPressed(): Boolean {
        replaceFragment(this@ManualInputFragment, MeasurementDashboardFragment(), getString(R.string.tag_case_dashboard))
//        fragmentManager?.popBackStack()
        return true
    }

    private val TAG = this::class.java

    private val textValue by lazy { view!!.findViewById<EditText>(R.id.value_measurement) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        when(bioType){
            BioLiveData.Companion.BioType.BloodGlucose -> {}
            BioLiveData.Companion.BioType.BloodPressure -> {}
            BioLiveData.Companion.BioType.Height -> {}
            BioLiveData.Companion.BioType.Oxygen -> {}
            BioLiveData.Companion.BioType.Pulse -> {}
            BioLiveData.Companion.BioType.Respire -> {}
            BioLiveData.Companion.BioType.Temperature -> {}
            BioLiveData.Companion.BioType.Weight -> {}

        }

        val view = inflater.inflate(R.layout.layout_manual_temp, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_manual_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.manual_add -> {
                LogUtil.logD("ManualInputFragment", "新增資料")
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
//        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(getString(R.string.manual_input))
        setToolbarLeftIcon(false)

        textValue.filters = arrayOf(InputFormatUtil(3, 1))

    }

    override fun onStop() {
        InputControlUtil.hideKeyboard(WeakReference((activity)))
        super.onStop()
    }

}
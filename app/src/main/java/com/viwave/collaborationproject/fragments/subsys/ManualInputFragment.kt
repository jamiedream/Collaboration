package com.viwave.collaborationproject.fragments.subsys

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.utils.InputControlUtil
import com.viwave.collaborationproject.utils.InputFormatUtil
import com.viwave.collaborationproject.utils.LogUtil
import java.lang.ref.WeakReference

class ManualInputFragment(private val bioType: BioLiveData.Companion.BioType): BaseFragment(), BackPressedDelegate {

    override fun onBackPressed(): Boolean {
        replaceFragment(this@ManualInputFragment,
            MeasurementDashboardFragment(), getString(R.string.tag_case_dashboard))
        return true
    }

    private val TAG = this::class.java.simpleName

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

                when(bioType){
                    BioLiveData.Companion.BioType.BloodGlucose -> {}
                    BioLiveData.Companion.BioType.BloodPressure -> {}
                    BioLiveData.Companion.BioType.Height -> {}
                    BioLiveData.Companion.BioType.Oxygen -> {}
                    BioLiveData.Companion.BioType.Pulse -> {}
                    BioLiveData.Companion.BioType.Respire -> {}
                    BioLiveData.Companion.BioType.Temperature -> {
                        //34~42.2
                        val value = textValue.text.toString().replace(",", ".").toFloat()
                        LogUtil.logD(TAG, value)
                        when(value){
                            in 34f..42.2f ->  {
                                bioViewModel.getDemoTempData().value = value
                                onBackPressed()
                            }
                            else -> Toast.makeText(context, "Warning! Data is not save since exceed regular range.", Toast.LENGTH_LONG).show()
                        }
                    }
                    BioLiveData.Companion.BioType.Weight -> {}

                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(getString(R.string.manual_input))
        setToolbarLeftIcon(false)

        textValue.filters = arrayOf(InputFormatUtil(3, 1))
        textValue.requestFocus()

        InputControlUtil.showKeyboard(WeakReference(activity))

    }

}
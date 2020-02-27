package com.viwave.collaborationproject.fragments.subsys

import android.os.Bundle
import android.text.InputFilter
import android.util.SparseArray
import android.view.*
import android.widget.EditText
import android.widget.Toast
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.ITogglePressedListener
import com.viwave.collaborationproject.fragments.adapter.GridViewAdapter
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.widgets.AutoFitRecyclerView
import com.viwave.collaborationproject.fragments.widgets.ManualInputLayout
import com.viwave.collaborationproject.utils.InputControlUtil
import com.viwave.collaborationproject.utils.InputFormatUtil
import com.viwave.collaborationproject.utils.LogUtil
import java.lang.ref.WeakReference

class ManualInputFragment(): BaseFragment(), BackPressedDelegate, ITogglePressedListener {

    override fun onBackPressed(): Boolean {
        replaceFragment(this@ManualInputFragment,
            MeasurementDashboardFragment(), getString(R.string.tag_case_dashboard))
        return true
    }

    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = bioViewModel.getSelectedTypeManualLayout().value
        setHasOptionsMenu(true)
        return inflater.inflate(layout?: R.layout.layout_manual_temp, container, false)
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

                when(bioViewModel.getSelectedType().value){
                    BioLiveData.Companion.BioType.BloodGlucose -> {
                        //20~600
                        val value = glucoseEditText.text.toString().replace(",", ".").toInt()
                        when(value){
                            in 20..600 -> {
                                bioViewModel.getDemoGlucoseData().value = value
                                onBackPressed()
                            }
                            else -> Toast.makeText(context, "Warning! Data is not save since exceed regular range.", Toast.LENGTH_LONG).show()
                        }
                    }
                    BioLiveData.Companion.BioType.BloodPressure -> {}
                    BioLiveData.Companion.BioType.Height -> {}
                    BioLiveData.Companion.BioType.Oxygen -> {}
                    BioLiveData.Companion.BioType.Pulse -> {}
                    BioLiveData.Companion.BioType.Respire -> {}
                    BioLiveData.Companion.BioType.Temperature -> {
                        //34~42.2
                        val value = tempEditText.text.toString().replace(",", ".").toFloat()
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

    private lateinit var tempEditText: EditText
    private lateinit var glucoseEditText: EditText

    override fun onResume() {
        super.onResume()
        setToolbarTitle(getString(R.string.manual_input))
        setToolbarLeftIcon(false)

        when(bioViewModel.getSelectedType().value){
            BioLiveData.Companion.BioType.BloodGlucose -> {

                glucoseEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_glucose).findViewById(R.id.value_measurement)
                glucoseEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                glucoseEditText.requestFocus()

                val autoFitView = view!!.findViewById<AutoFitRecyclerView>(R.id.view_auto_fit)
                val listItem = SparseArray<String>()
                listItem.append(0, context?.getString(R.string.fasting))
                listItem.append(1, context?.getString(R.string.before_meal))
                listItem.append(2, context?.getString(R.string.after_meal))
                val gridAdapter = GridViewAdapter(listItem, this)
                autoFitView.adapter = gridAdapter
            }
            BioLiveData.Companion.BioType.BloodPressure -> {}
            BioLiveData.Companion.BioType.Height -> {}
            BioLiveData.Companion.BioType.Oxygen -> {}
            BioLiveData.Companion.BioType.Pulse -> {}
            BioLiveData.Companion.BioType.Respire -> {}
            BioLiveData.Companion.BioType.Temperature -> {
                tempEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_temp).findViewById(R.id.value_measurement)
                tempEditText.filters = arrayOf(InputFormatUtil(3, 1))
                tempEditText.requestFocus()
            }
            BioLiveData.Companion.BioType.Weight -> {}

        }

        //show keyboard
        InputControlUtil.showKeyboard(WeakReference(activity))
    }

    override fun pressedToggle(toggleName: String) {
        LogUtil.logD(TAG, toggleName)
    }

}
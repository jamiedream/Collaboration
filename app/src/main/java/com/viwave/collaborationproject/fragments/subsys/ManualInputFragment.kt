package com.viwave.collaborationproject.fragments.subsys

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.util.SparseArray
import android.view.*
import android.widget.EditText
import android.widget.Toast
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.ITogglePressedListener
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.widgets.AutoFitRecyclerView
import com.viwave.collaborationproject.fragments.widgets.ManualInputLayout
import com.viwave.collaborationproject.fragments.widgets.adapter.GridViewAdapter
import com.viwave.collaborationproject.utils.InputControlUtil
import com.viwave.collaborationproject.utils.InputFormatUtil
import com.viwave.collaborationproject.utils.LogUtil
import java.lang.ref.WeakReference

class ManualInputFragment(): BaseFragment(), BackPressedDelegate, ITogglePressedListener {

    override fun onBackPressed(): Boolean {
        fragmentManager?.popBackStack()
        return true
    }

    private val TAG = this::class.java.simpleName

    private lateinit var note: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = bioViewModel.getSelectedTypeManualLayout().value
        setHasOptionsMenu(true)
        return inflater.inflate(layout?: R.layout.layout_manual_temp, container, false)
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
                        if(!glucoseEditText.text.isNullOrEmpty()) {
                            //20~600
                            val value = glucoseEditText.text.toString().toInt()
                            when (value) {
                                in 20..600 -> {
                                    onBackPressed()
                                }
                                else -> Toast.makeText(
                                    context,
                                    "Warning! Data is not save since exceed regular range.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }else
                            Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()
                    }
                    BioLiveData.Companion.BioType.BloodPressure -> {}
                    BioLiveData.Companion.BioType.Height -> {
                        if(!heightEditText.text.isNullOrEmpty()) {
                            val value = heightEditText.text.toString().replace(",", ".").toFloat()
                            onBackPressed()
                        }else
                            Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()
                    }
                    BioLiveData.Companion.BioType.Oxygen -> {
                        when{
                            oxygenEditText.text.isNullOrEmpty() && oxygenPulseEditText.text.isNullOrEmpty() ->
                                Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()
                            oxygenEditText.text.isNullOrEmpty() && !oxygenPulseEditText.text.isNullOrEmpty() ->
                                Toast.makeText(context, "Warning! Oxygen data is empty.", Toast.LENGTH_LONG).show()
                            !oxygenEditText.text.isNullOrEmpty() && oxygenPulseEditText.text.isNullOrEmpty() ->
                                Toast.makeText(context, "Warning! Pulse data is empty.", Toast.LENGTH_LONG).show()
                            else -> {
                                //35~100
                                val oxygenValue = oxygenEditText.text.toString().toInt()
                                //40~199
                                val pulseValue = oxygenPulseEditText.text.toString().toInt()
                                when(oxygenValue) {
                                    in 35..100 -> {
                                        when(pulseValue){
                                            in 40..199 -> {
                                                onBackPressed()
                                            }
                                            else ->
                                                Toast.makeText(context, "Warning! Pulse data exceed regular range.", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                    else -> {
                                        when(pulseValue){
                                            in 40..199 ->
                                                Toast.makeText(context, "Warning! Oxygen data exceed regular range.", Toast.LENGTH_LONG).show()
                                            else ->
                                                Toast.makeText(context, "Warning! Oxygen and pulse data exceed regular range.", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                    BioLiveData.Companion.BioType.Pulse -> {
                        if(!pulseEditText.text.isNullOrEmpty()) {
                            val value = pulseEditText.text.toString().toInt()
                            //40~199
                            when (value) {
                                in 40..199 -> {
                                    onBackPressed()
                                }
                                else -> Toast.makeText(
                                    context,
                                    "Warning! Data is not save since exceed regular range.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }else
                            Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()
                    }
                    BioLiveData.Companion.BioType.Respire -> {
                        if(!respireEditText.text.isNullOrEmpty()) {
                            val value = respireEditText.text.toString().toInt()
                            onBackPressed()
                        }else
                            Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()
                    }
                    BioLiveData.Companion.BioType.Temperature -> {
                        //34~42.2
                        if(!tempEditText.text.isNullOrEmpty()){
                            val value = tempEditText.text.toString().replace(",", ".").toFloat()
                            when(value){
                                in 34f..42.2f ->  {
                                    onBackPressed()
                                }
                                else -> Toast.makeText(context, "Warning! Data is not save since exceed regular range.", Toast.LENGTH_LONG).show()
                            }
                        }else
                            Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()

                    }
                    BioLiveData.Companion.BioType.Weight -> {
                        if(!weightEditText.text.isNullOrEmpty()) {
                            val value = weightEditText.text.toString().replace(",", ".").toFloat()
                            when (value) {
                                in 11f..360f -> {
                                    onBackPressed()
                                }
                                else -> Toast.makeText(
                                    context,
                                    "Warning! Data is not save since exceed regular range.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }else
                            Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()
                    }

                }

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private lateinit var tempEditText: EditText
    private lateinit var glucoseEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var respireEditText: EditText
    private lateinit var heightEditText: EditText
    private lateinit var pulseEditText: EditText

    private lateinit var systolicEditText: EditText
    private lateinit var diastolicEditText: EditText
    private lateinit var bpPulseEditText: EditText

    private lateinit var oxygenEditText: EditText
    private lateinit var oxygenPulseEditText: EditText

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

                note = listItem[listItem.size() - 1]
            }
            BioLiveData.Companion.BioType.BloodPressure -> {
                systolicEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_bp_sys).findViewById(R.id.value_measurement)
                diastolicEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_bp_dia).findViewById(R.id.value_measurement)
                view!!.findViewById<ManualInputLayout>(R.id.manual_bp_dia).enableEdit(false)
                bpPulseEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_bp_pulse).findViewById(R.id.value_measurement)
                systolicEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                diastolicEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                bpPulseEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                systolicEditText.requestFocus()

                systolicEditText.addTextChangedListener(
                    object: TextWatcher{
                        override fun afterTextChanged(s: Editable?) {
                            if(!s.isNullOrEmpty())
                                view!!.findViewById<ManualInputLayout>(R.id.manual_bp_dia).enableEdit(true)
                            else
                                view!!.findViewById<ManualInputLayout>(R.id.manual_bp_dia).enableEdit(false)
                        }

                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {

                        }

                    }
                )
                diastolicEditText.addTextChangedListener(
                    object: TextWatcher{
                        override fun afterTextChanged(s: Editable?) {

                        }

                        override fun beforeTextChanged(
                            s: CharSequence?,
                            start: Int,
                            count: Int,
                            after: Int
                        ) {

                        }

                        override fun onTextChanged(
                            s: CharSequence?,
                            start: Int,
                            before: Int,
                            count: Int
                        ) {
                            if(systolicEditText.text.isNullOrEmpty()){
                                view!!.findViewById<ManualInputLayout>(R.id.manual_bp_dia).enableEdit(false)
                                diastolicEditText.text.clear()
                            }else {
                                if(!s.isNullOrEmpty()){
                                    s.toString().toInt().run {
                                        if(this > systolicEditText.text.toString().toInt()){
                                            diastolicEditText.text.clear()
                                            Toast.makeText(context!!, "Diastolic data can not exceed systolic.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }

                            }
                        }

                    }
                )


            }
            BioLiveData.Companion.BioType.Height -> {
                heightEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_height).findViewById(R.id.value_measurement)
                heightEditText.filters = arrayOf(InputFormatUtil(4, 1))
                heightEditText.requestFocus()
            }
            BioLiveData.Companion.BioType.Oxygen -> {
                oxygenEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_oxygen).findViewById(R.id.value_measurement)
                oxygenPulseEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_oxygen_pulse).findViewById(R.id.value_measurement)
                oxygenEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                oxygenPulseEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                oxygenEditText.requestFocus()
            }
            BioLiveData.Companion.BioType.Pulse -> {
                pulseEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_pulse).findViewById(R.id.value_measurement)
                pulseEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                pulseEditText.requestFocus()
            }
            BioLiveData.Companion.BioType.Respire -> {
                respireEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_respire).findViewById(R.id.value_measurement)
                respireEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                respireEditText.requestFocus()
            }
            BioLiveData.Companion.BioType.Temperature -> {
                tempEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_temp).findViewById(R.id.value_measurement)
                tempEditText.filters = arrayOf(InputFormatUtil(3, 1))
                tempEditText.requestFocus()
            }
            BioLiveData.Companion.BioType.Weight -> {
                weightEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_weight).findViewById(R.id.value_measurement)
                weightEditText.filters = arrayOf(InputFormatUtil(4, 1))
                weightEditText.requestFocus()
            }

        }

        //show keyboard
        InputControlUtil.showKeyboard(WeakReference(activity))
    }

    override fun pressedToggle(toggleName: String) {
        note = toggleName
        LogUtil.logD(TAG, toggleName)
    }

}
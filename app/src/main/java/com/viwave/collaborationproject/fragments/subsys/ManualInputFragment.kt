/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/31/20 1:15 PM
 */

package com.viwave.collaborationproject.fragments.subsys

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.data.bios.BioUpload
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.ITogglePressedListener
import com.viwave.collaborationproject.fragments.subsys.MeasurementDashboardFragment.Companion.MEASURE_CASE_NO
import com.viwave.collaborationproject.fragments.subsys.MeasurementDashboardFragment.Companion.MEASURE_SCDID
import com.viwave.collaborationproject.fragments.subsys.MeasurementDashboardFragment.Companion.MEASURE_STAFF_ID
import com.viwave.collaborationproject.fragments.subsys.MeasurementDashboardFragment.Companion.MEASURE_SYS_CODE
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.widgets.ManualInputLayout
import com.viwave.collaborationproject.fragments.widgets.TabView
import com.viwave.collaborationproject.utils.*
import java.lang.ref.WeakReference

class ManualInputFragment(): BaseFragment(), BackPressedDelegate{

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
                    BioLiveData.Companion.BioType.BloodGlucose -> uploadBG()
                    BioLiveData.Companion.BioType.BloodPressure -> uploadBP()
                    BioLiveData.Companion.BioType.Height -> uploadHeight()
                    BioLiveData.Companion.BioType.Oxygen -> uploadOxygen()
                    BioLiveData.Companion.BioType.Pulse -> uploadPulse()
                    BioLiveData.Companion.BioType.Respire -> uploadRespire()
                    BioLiveData.Companion.BioType.Temperature -> uploadTemp()
                    BioLiveData.Companion.BioType.Weight -> uploadWeight()
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
    private lateinit var frameDiastolic: FrameLayout

    private lateinit var oxygenEditText: EditText

    private lateinit var caseNo: String
    private lateinit var SCDID: String
    private lateinit var staffId: String
    private lateinit var sysCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        caseNo = this.arguments?.getString(MEASURE_CASE_NO)?: ""
        SCDID = this.arguments?.getString(MEASURE_SCDID)?: ""
        staffId = this.arguments?.getString(MEASURE_STAFF_ID)?: ""
        sysCode = this.arguments?.getString(MEASURE_SYS_CODE)?: ""

        note = context?.getString(R.string.after_meal)?: ""
    }
    override fun onResume() {
        super.onResume()
        setToolbarTitle(getString(R.string.manual_input))
        setToolbarLeftIcon(false)

        when(bioViewModel.getSelectedType().value){
            BioLiveData.Companion.BioType.BloodGlucose -> {

                glucoseEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_glucose).findViewById(R.id.value_measurement)
                glucoseEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                glucoseEditText.requestFocus()

                val autoFitView = view!!.findViewById<TabView>(R.id.view_auto_fit)
                autoFitView.setSelectedTab(note)
                autoFitView.setToggleListener(
                    object : ITogglePressedListener{
                        override fun pressedToggle(toggleName: String) {
                            note = toggleName
                            LogUtil.logD(TAG, toggleName)
                        }
                    }
                )

                view!!.findViewById<ManualInputLayout>(R.id.manual_glucose).setIMECallback(
                    object: IUploadListener{
                        override fun upload() {
                            uploadBG()
                        }
                    }
                )
            }
            BioLiveData.Companion.BioType.BloodPressure -> {
                systolicEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_bp_sys).findViewById(R.id.value_measurement)
                diastolicEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_bp_dia).findViewById(R.id.value_measurement)
                frameDiastolic = view!!.findViewById(R.id.frame_manual_bp_dia)
                frameDiastolic.visibility = View.VISIBLE
                view!!.findViewById<ManualInputLayout>(R.id.manual_bp_dia).enableEdit(false)
                systolicEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                diastolicEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                systolicEditText.requestFocus()


                view!!.findViewById<ManualInputLayout>(R.id.manual_bp_dia).setIMECallback(
                    object: IUploadListener{
                        override fun upload() {
                            uploadBP()
                        }
                    }
                )

                systolicEditText.addTextChangedListener(
                    object: TextWatcher{
                        override fun afterTextChanged(s: Editable?) {
                            if(!s.isNullOrEmpty()){
                                frameDiastolic.visibility = View.GONE
                                view!!.findViewById<ManualInputLayout>(R.id.manual_bp_dia).enableEdit(true)
                            } else {
                                frameDiastolic.visibility = View.VISIBLE
                                view!!.findViewById<ManualInputLayout>(R.id.manual_bp_dia).enableEdit(false)
                            }
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
                                frameDiastolic.visibility = View.VISIBLE
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
                view!!.findViewById<ManualInputLayout>(R.id.manual_height).setIMECallback(
                    object: IUploadListener{
                        override fun upload() {
                            uploadHeight()
                        }
                    }
                )
            }
            BioLiveData.Companion.BioType.Oxygen -> {
                oxygenEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_oxygen).findViewById(R.id.value_measurement)
                oxygenEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                oxygenEditText.requestFocus()
                view!!.findViewById<ManualInputLayout>(R.id.manual_oxygen).setIMECallback(
                    object: IUploadListener{
                        override fun upload() {
                            uploadOxygen()
                        }
                    }
                )
            }
            BioLiveData.Companion.BioType.Pulse -> {
                pulseEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_pulse).findViewById(R.id.value_measurement)
                pulseEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                pulseEditText.requestFocus()
                view!!.findViewById<ManualInputLayout>(R.id.manual_pulse).setIMECallback(
                    object: IUploadListener{
                        override fun upload() {
                            uploadPulse()
                        }
                    }
                )
            }
            BioLiveData.Companion.BioType.Respire -> {
                respireEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_respire).findViewById(R.id.value_measurement)
                respireEditText.filters = arrayOf(InputFilter.LengthFilter(3))
                respireEditText.requestFocus()
                view!!.findViewById<ManualInputLayout>(R.id.manual_respire).setIMECallback(
                    object: IUploadListener{
                        override fun upload() {
                            uploadRespire()
                        }
                    }
                )
            }
            BioLiveData.Companion.BioType.Temperature -> {
                tempEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_temp).findViewById(R.id.value_measurement)
                tempEditText.filters = arrayOf(InputFormatUtil(3, 1))
                tempEditText.requestFocus()
                view!!.findViewById<ManualInputLayout>(R.id.manual_temp).setIMECallback(
                    object: IUploadListener{
                        override fun upload() {
                            uploadTemp()
                        }
                    }
                )
            }
            BioLiveData.Companion.BioType.Weight -> {
                weightEditText = view!!.findViewById<ManualInputLayout>(R.id.manual_weight).findViewById(R.id.value_measurement)
                weightEditText.filters = arrayOf(InputFormatUtil(4, 1))
                weightEditText.requestFocus()
                view!!.findViewById<ManualInputLayout>(R.id.manual_weight).setIMECallback(
                    object: IUploadListener{
                        override fun upload() {
                            uploadWeight()
                        }
                    }
                )
            }

        }

        //show keyboard
        InputControlUtil.showKeyboard(WeakReference(activity))
    }

    private fun uploadBG(){
        if(!glucoseEditText.text.isNullOrEmpty()) {
            //20~600
            val value = glucoseEditText.text.toString().toInt()
            when (value) {
                in 20..600 -> {
                    val takenAt = DateUtil.getNowTimestamp().div(1000L).toString()
                    val bgUploadData =
                        BioUpload(
                            caseNo,
                            staffId,
                            SCDID,
                            sysCode,
                            getString(R.string.blood_glucose),
                            takenAt,
                            "$value",
                            note
                        )
                    onBackPressed()
                }
                else -> {
                    AlertDialog.Builder(this@ManualInputFragment.activity)
                        .setTitle(getString(R.string.blood_glucose))
                        .setIcon(R.drawable.ic_count_bg)
                        .setMessage(String.format(getString(R.string.data_not_in_range), "$value ${getString(R.string.unit_blood_glucose)}"))
                        .setPositiveButton(getString(R.string.ok)
                        ) { dialog, _ -> dialog.dismiss()}
                        .show()
                }
            }
        }else Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()
    }

    private fun uploadTemp(){
        //34~42.2
        if(!tempEditText.text.isNullOrEmpty()){
            val value = tempEditText.text.toString().replace(",", ".").toFloat()
            when(value){
                in 34f..42.2f ->  {
                    val takenAt = DateUtil.getNowTimestamp().div(1000L).toString()
                    val tempUploadData =
                        BioUpload(
                            caseNo,
                            staffId,
                            SCDID,
                            sysCode,
                            getString(R.string.temperature),
                            takenAt,
                            DataFormatUtil.formatString(value),
                            ""
                        )
                    onBackPressed()
                }
                else -> {
                    AlertDialog.Builder(this@ManualInputFragment.activity)
                        .setTitle(getString(R.string.temperature))
                        .setIcon(R.drawable.ic_count_temp)
                        .setMessage(String.format(getString(R.string.data_not_in_range), "$value ${getString(R.string.unit_celsius)}"))
                        .setPositiveButton(getString(R.string.ok)
                        ) { dialog, _ -> dialog.dismiss()}
                        .show()
                }
            }
        }else Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()
    }

    private fun uploadBP(){
        when {
            systolicEditText.text.isNullOrEmpty() && diastolicEditText.text.isNullOrEmpty() ->
                Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()
            systolicEditText.text.isNullOrEmpty() && !diastolicEditText.text.isNullOrEmpty() ->
                Toast.makeText(context, "Warning! Systolic data is empty.", Toast.LENGTH_LONG).show()
            !systolicEditText.text.isNullOrEmpty() && diastolicEditText.text.isNullOrEmpty() ->
                Toast.makeText(context, "Warning! Diastolic data is empty.", Toast.LENGTH_LONG).show()
            else -> {
                //30..260
                val valueSys = systolicEditText.text.toString().toInt()
                val valueDia = diastolicEditText.text.toString().toInt()
                when(valueSys){
                    in 30..260 -> {
                        when(valueDia){
                            in 30..260 -> {
                                val takenAt = DateUtil.getNowTimestamp().div(1000L).toString()
                                val bpUploadData =
                                    BioUpload(
                                        caseNo,
                                        staffId,
                                        SCDID,
                                        sysCode,
                                        getString(R.string.blood_pressure),
                                        takenAt,
                                        "${valueSys}/${valueDia}",
                                        ""
                                    )
                                onBackPressed()
                            }
                            else -> {
                                AlertDialog.Builder(this@ManualInputFragment.activity)
                                    .setTitle(getString(R.string.blood_pressure))
                                    .setIcon(R.drawable.ic_count_bp)
                                    .setMessage(String.format(getString(R.string.data_not_in_range), "${valueSys}/${valueDia} ${getString(R.string.unit_blood_pressure)}"))
                                    .setPositiveButton(getString(R.string.ok)
                                    ) { dialog, _ -> dialog.dismiss()}
                                    .show()
                            }
                        }
                    }
                    else -> {
                        AlertDialog.Builder(this@ManualInputFragment.activity)
                            .setTitle(getString(R.string.blood_pressure))
                            .setIcon(R.drawable.ic_count_bp)
                            .setMessage(String.format(getString(R.string.data_not_in_range), "${valueSys}/${valueDia} ${getString(R.string.unit_blood_pressure)}"))
                            .setPositiveButton(getString(R.string.ok)
                            ) { dialog, _ -> dialog.dismiss()}
                            .show()
                    }
                }

            }
        }
    }

    private fun uploadPulse(){
        if(!pulseEditText.text.isNullOrEmpty()) {
            val value = pulseEditText.text.toString().toInt()
            //40~199
            when (value) {
                in 40..199 -> {
                    val takenAt = DateUtil.getNowTimestamp().div(1000L).toString()
                    val pulseUploadData =
                        BioUpload(
                            caseNo,
                            staffId,
                            SCDID,
                            sysCode,
                            getString(R.string.pulse),
                            takenAt,
                            "$value",
                            ""
                        )
                    onBackPressed()
                }
                else -> {
                    AlertDialog.Builder(this@ManualInputFragment.activity)
                        .setTitle(getString(R.string.pulse))
                        .setIcon(R.drawable.ic_count_pulse)
                        .setMessage(String.format(getString(R.string.data_not_in_range), "$value ${getString(R.string.unit_pulse)}"))
                        .setPositiveButton(getString(R.string.ok)
                        ) { dialog, _ -> dialog.dismiss()}
                        .show()
                }
            }
        }else Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()
    }

    private fun uploadRespire(){
        if(!respireEditText.text.isNullOrEmpty()) {
            val value = respireEditText.text.toString().toInt()
            when (value) {
                //todo, temp range
                in 10..40 -> {
                    val takenAt = DateUtil.getNowTimestamp().div(1000L).toString()
                    val respireUploadData =
                        BioUpload(
                            caseNo,
                            staffId,
                            SCDID,
                            sysCode,
                            getString(R.string.respire),
                            takenAt,
                            "$value",
                            ""
                        )
                    onBackPressed()
                }
                else -> {
                    AlertDialog.Builder(this@ManualInputFragment.activity)
                        .setTitle(getString(R.string.respire))
                        .setIcon(R.drawable.ic_count_respire)
                        .setMessage(String.format(getString(R.string.data_not_in_range), "$value ${getString(R.string.unit_respire)}"))
                        .setPositiveButton(getString(R.string.ok)
                        ) { dialog, _ -> dialog.dismiss()}
                        .show()
                }
            }
        }else Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()
    }

    private fun uploadWeight(){
        if(!weightEditText.text.isNullOrEmpty()) {
            val value = weightEditText.text.toString().replace(",", ".").toFloat()
            when (value) {
                in 11f..360f -> {
                    val takenAt = DateUtil.getNowTimestamp().div(1000L).toString()
                    val weightUploadData =
                        BioUpload(
                            caseNo,
                            staffId,
                            SCDID,
                            sysCode,
                            getString(R.string.weight),
                            takenAt,
                            DataFormatUtil.formatString(value),
                            ""
                        )
                    onBackPressed()
                }
                else -> {
                    AlertDialog.Builder(this@ManualInputFragment.activity)
                        .setTitle(getString(R.string.weight))
                        .setIcon(R.drawable.ic_count_weight)
                        .setMessage(String.format(getString(R.string.data_not_in_range), "$value ${getString(R.string.unit_weight)}"))
                        .setPositiveButton(getString(R.string.ok)
                        ) { dialog, _ -> dialog.dismiss()}
                        .show()
                }
            }
        }else Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()
    }

    private fun uploadOxygen(){
        when{
            oxygenEditText.text.isNullOrEmpty() ->
                Toast.makeText(context, "Warning! Oxygen data is empty.", Toast.LENGTH_LONG).show()
            else -> {
                //35~100
                val oxygenValue = oxygenEditText.text.toString().toInt()
                when(oxygenValue) {
                    in 35..100 -> {
                        val takenAt = DateUtil.getNowTimestamp().div(1000L).toString()
                        val oxygenUploadData =
                            BioUpload(
                                caseNo,
                                staffId,
                                SCDID,
                                sysCode,
                                getString(R.string.oxygen),
                                takenAt,
                                "$oxygenValue",
                                ""
                            )
                        onBackPressed()
                    }
                    else -> {
                        AlertDialog.Builder(this@ManualInputFragment.activity)
                            .setTitle(getString(R.string.oxygen))
                            .setIcon(R.drawable.ic_count_oxygen)
                            .setMessage(String.format(getString(R.string.data_not_in_range), "$oxygenValue ${getString(R.string.percentage)}"))
                            .setPositiveButton(getString(R.string.ok)
                            ) { dialog, _ -> dialog.dismiss()}
                            .show()

                    }
                }
            }
        }
    }

    private fun uploadHeight(){
        if(!heightEditText.text.isNullOrEmpty()) {
            val value = heightEditText.text.toString().replace(",", ".").toFloat()
            when(value){
                in 20f..200f -> {
                    val takenAt = DateUtil.getNowTimestamp().div(1000L).toString()
                    val heightUploadData =
                        BioUpload(
                            caseNo,
                            staffId,
                            SCDID,
                            sysCode,
                            getString(R.string.height),
                            takenAt,
                            DataFormatUtil.formatString(value),
                            ""
                        )
                    onBackPressed()
                }
                else -> {
                    AlertDialog.Builder(this@ManualInputFragment.activity)
                        .setTitle(getString(R.string.height))
                        .setIcon(R.drawable.ic_count_height)
                        .setMessage(String.format(getString(R.string.data_not_in_range), "$value ${getString(R.string.unit_height)}"))
                        .setPositiveButton(getString(R.string.ok)
                        ) { dialog, _ -> dialog.dismiss()}
                        .show()
                }
            }
        }else
            Toast.makeText(context, "Data is empty.", Toast.LENGTH_LONG).show()
    }

}
package com.viwave.collaborationproject.fragments.subsys

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.DB.cache.DeviceKey
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.DB.cache.SysKey.DAILY_NURSING_CODE
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.MainActivity.Companion.generalViewModel
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.data.bios.BioUpload
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.FEMALE
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.MALE
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.caseViewModel
import com.viwave.collaborationproject.fragments.subsys.history.HistoryFragment
import com.viwave.collaborationproject.fragments.widgets.MeasurementItemLayout
import com.viwave.collaborationproject.utils.DateUtil
import com.viwave.collaborationproject.utils.LogUtil
import com.viwave.collaborationproject.utils.PreferenceUtil
import com.viwaveulife.vuioht.*
import com.viwaveulife.vuioht.model.device_data.*
import com.viwaveulife.vuioht.model.unit.VU_GLUCOSE_UNIT
import com.viwaveulife.vuioht.model.unit.VU_PRESSURE_UNIT
import com.viwaveulife.vuioht.model.unit.VU_TEMPERATURE_UNIT

class MeasurementDashboardFragment: BaseFragment(), BackPressedDelegate {

    override fun onBackPressed(): Boolean {
        fragmentManager?.popBackStack()
        return true
    }

    private val TAG = this::class.java.simpleName

    private val caseGender by lazy { view!!.findViewById<ImageView>(R.id.case_gender) }
    private val caseName by lazy { view!!.findViewById<TextView>(R.id.case_name) }
    private val caseNumber by lazy { view!!.findViewById<TextView>(R.id.case_number) }

    private val cardRespire by lazy { view!!.findViewById<CardView>(R.id.block_respire) }
    private val cardHeight by lazy { view!!.findViewById<CardView>(R.id.block_height) }
    private val cardOxygen by lazy { view!!.findViewById<CardView>(R.id.block_oxygen) }
    private val cardWeight by lazy { view!!.findViewById<CardView>(R.id.block_weight) }

    private val borderTemp by lazy { view!!.findViewById<FrameLayout>(R.id.border_temp) }
    private val borderBP by lazy { view!!.findViewById<FrameLayout>(R.id.border_blood_pressure) }
    private val borderBG by lazy { view!!.findViewById<FrameLayout>(R.id.border_blood_glucose) }
    private val borderOxygen by lazy { view!!.findViewById<FrameLayout>(R.id.border_oxygen) }
    private val borderWeight by lazy { view!!.findViewById<FrameLayout>(R.id.border_weight) }

    private val valueTemp by lazy { view!!.findViewById<MeasurementItemLayout>(R.id.value_temp) }
    private val valuePulse by lazy { view!!.findViewById<MeasurementItemLayout>(R.id.value_pulse) }
    private val valueRespire by lazy { view!!.findViewById<MeasurementItemLayout>(R.id.value_respire) }
    private val valueBloodPressure by lazy { view!!.findViewById<MeasurementItemLayout>(R.id.value_blood_pressure) }
    private val valueBloodPressurePulse by lazy { view!!.findViewById<MeasurementItemLayout>(R.id.value_blood_pressure_pulse) }
    private val valueBloodGlucose by lazy { view!!.findViewById<MeasurementItemLayout>(R.id.value_blood_glucose) }
    private val valueWeight by lazy { view!!.findViewById<MeasurementItemLayout>(R.id.value_weight) }
    private val valueHeight by lazy { view!!.findViewById<MeasurementItemLayout>(R.id.value_height) }
    private val valueOxygen by lazy { view!!.findViewById<MeasurementItemLayout>(R.id.value_oxygen) }
    private val valueOxygenPulse by lazy { view!!.findViewById<MeasurementItemLayout>(R.id.value_oxygen_pulse) }

    private val uploadTemp by lazy { view!!.findViewById<TextView>(R.id.upload_status_temp) }
    private val uploadPulse by lazy { view!!.findViewById<TextView>(R.id.upload_status_pulse) }
    private val uploadRespire by lazy { view!!.findViewById<TextView>(R.id.upload_status_respire) }
    private val uploadBloodPressure by lazy { view!!.findViewById<TextView>(R.id.upload_status_blood_pressure) }
    private val uploadBloodGlucose by lazy { view!!.findViewById<TextView>(R.id.upload_status_blood_glucose) }
    private val uploadWeight by lazy { view!!.findViewById<TextView>(R.id.upload_status_weight) }
    private val uploadHeight by lazy { view!!.findViewById<TextView>(R.id.upload_status_height) }
    private val uploadOxygen by lazy { view!!.findViewById<TextView>(R.id.upload_status_oxygen) }

    private val vuBleManager by lazy { VUBleManager.getInstance() }
    private var scanFilterList:ArrayList<VUBleScanFilter>? = null
    private var obsDeviceMap:MutableMap<String, VUBleDevice>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_case_dashboard, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTempView(view)
        initBloodGlucose(view)
        initWeight(view)
        initRespire(view)
        initHeight(view)
        initPulse(view)
        initBloodPressure(view)
        initOxygen(view)

    }

    private lateinit var caseNo: String
    private lateinit var SCDID: String
    private lateinit var staffId: String
    private lateinit var sysCode: String
    private var note: String = ""

    private val selectedCaseObserver =
        Observer<CaseEntity>{
            caseName.text = it.getCaseName
            caseNumber.text = it.getCaseNumber
            caseGender.setImageResource(
                    when(it.getCaseGender){
                        FEMALE -> R.drawable.ic_gender_female
                        else -> R.drawable.ic_gender_male
                    }
            )
            caseNo = it.getCaseNumber
            SCDID = it.getSCDID?: ""
        }

    override fun onPause() {
        super.onPause()
        startScan(false)
    }

    override fun onStart() {
        super.onStart()
        obsDeviceMap = VUBleManager.getInstance().allObserveDevice
        generateScanFilter()
    }

    override fun onResume() {
        super.onResume()
        caseViewModel.getSelectedCase().observe(this, selectedCaseObserver)
        staffId = generalViewModel.getLoginUser().value?.id?: ""
        sysCode = generalViewModel.getSelectedSubSys().value?.sysCode?: ""
        setToolbarTitle(generalViewModel.getSelectedSubSys().value?.sysName?: "")
        setToolbarLeftIcon(false)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        if(sysCode != DAILY_NURSING_CODE){
            cardRespire.visibility = View.GONE
            cardHeight.visibility = View.GONE
            cardWeight.visibility = View.GONE
            cardOxygen.visibility = View.GONE
        }

        if(!VUBleManager.getInstance().isEnabled) {
            //is bluetooth enable?
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, 0)
        } else if(!VUBleManager.getInstance().isLocationServiceEnable) {
            //is location enable?
            VUBleManager.getInstance().requestLocationService(activity)
        } else if(!VUBleManager.getInstance().isGetLocationPermission) {
            //is get location permission?
            VUBleManager.getInstance().requestLocationPermission(this)
        } else {
            //all down, start scan
            startScan(true)
        }

    }

    override fun onStop() {
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        caseViewModel.getSelectedCase().removeObserver(selectedCaseObserver)
        super.onStop()
    }


    private fun generateScanFilter() {
        scanFilterList = ArrayList()
        for(bleDevice:VUBleDevice in obsDeviceMap!!.values) {
            val builder:VUBleScanFilter.Builder = VUBleScanFilter.Builder()
            builder.setMacAddress(bleDevice.id)
            scanFilterList!!.add(builder.build())
        }
    }

    private fun startScan(enable: Boolean){
        LogUtil.logD(TAG, enable)
        if(enable){
            VUBleManager.getInstance().startScan(scanFilterList, 0)
            vuBleManager.setScanCallback(mScanCallback)
            vuBleManager.setOnConnectionStateChangeListener(mOnConnectionChangeListener)
            vuBleManager.setOnMeasurementReceiveListener(generateMeasureListener)
        }else{
            vuBleManager.close()
            vuBleManager.setScanCallback(null)
            vuBleManager.setOnConnectionStateChangeListener(null)
            vuBleManager.setOnMeasurementReceiveListener(null)
        }
    }

    //儲存單一連線裝置名稱
    private val deviceCategoryList = arrayListOf<String>()

    private val mScanCallback = object : VUScanCallback() {
        override fun onScanFinish() {
            super.onScanFinish()
        }

        override fun onScanFailed(p0: Int) {
            super.onScanFailed(p0)
        }

        override fun onScanResult(p0: VUBleDevice?) {
            super.onScanResult(p0)
            LogUtil.logV(TAG, "Scan device " + p0?.id + " " + p0?.name)
            p0?.let {
                PreferenceUtil.loadDevice(p0.id)?.let {
                    LogUtil.logD(TAG, deviceCategoryList.contains(it.deviceCategory))
                    when(deviceCategoryList.contains(it.deviceCategory)){
                        false -> doConnect(p0, it.deviceCategory)
                    }
                }

            }
        }
    }

    private fun doConnect(device: VUBleDevice, category: String){
        LogUtil.logV(TAG, "Scan device connect" + device.id + " " + device.name)
        deviceCategoryList.add(category)
        vuBleManager.connect(device, 10)
    }

    private val mOnConnectionChangeListener =
        object : VUBleManager.VUOnConnectionStateChangeListener {
            override fun onConnectionReady(p0: VUBleDevice?) {
                p0?.let {
                    val measurementDevice = PreferenceUtil.loadDevice(p0.id)
                    activity?.runOnUiThread {
                        when(measurementDevice?.deviceCategory){
                            DeviceKey.DEVICE_KEY_BP -> borderBP.setBackgroundResource(R.drawable.bg_border_device_connect)
                            DeviceKey.DEVICE_KEY_BG -> borderBG.setBackgroundResource(R.drawable.bg_border_device_connect)
                            DeviceKey.DEVICE_KEY_TEMP -> borderTemp.setBackgroundResource(R.drawable.bg_border_device_connect)
                            DeviceKey.DEVICE_KEY_OXYGEN -> borderOxygen.setBackgroundResource(R.drawable.bg_border_device_connect)
                            DeviceKey.DEVICE_KEY_WEIGHT -> borderWeight.setBackgroundResource(R.drawable.bg_border_device_connect)
                            else -> {}
                        }
                    }
                }
            }

            override fun onDisconnected(p0: VUBleDevice?, p1: Int) {
                p0?.let {
                    val measurementDevice = PreferenceUtil.loadDevice(p0.id)
                    activity?.runOnUiThread {
                        deviceCategoryList.remove(measurementDevice?.deviceCategory)
                        when(measurementDevice?.deviceCategory){
                            DeviceKey.DEVICE_KEY_BP -> borderBP.setBackgroundResource(0)
                            DeviceKey.DEVICE_KEY_BG -> borderBG.setBackgroundResource(0)
                            DeviceKey.DEVICE_KEY_TEMP -> borderTemp.setBackgroundResource(0)
                            DeviceKey.DEVICE_KEY_OXYGEN -> borderOxygen.setBackgroundResource(0)
                            DeviceKey.DEVICE_KEY_WEIGHT -> borderWeight.setBackgroundResource(0)
                            else -> {}
                        }
                    }
                }
            }

            override fun onTimeoutExpired(p0: VUBleDevice?) {

            }

            override fun onConnectionRetry(p0: VUBleDevice?, p1: Int) {

            }
        }

    private val generateMeasureListener =
        object: VUBleManager.VUOnMeasurementReceiveListener {

            override fun onBloodPressureReceive(
                p0: VUBleDevice?,
                p1: VUBloodPressure?,
                p2: VUError?
            ) {
                when(p2 == null){
                    false -> LogUtil.logE(TAG, p2.msg)
                    true -> {
                        when(p1 == null){
                            false -> {
                                val takenAt = DateUtil.getNowTimestamp().div(1000L).toString()
                                val bpUploadData =
                                    BioUpload(
                                        caseNo,
                                        staffId,
                                        SCDID,
                                        sysCode,
                                        getString(R.string.blood_pressure),
                                        takenAt,
                                        "${p1.getSystolic(VU_PRESSURE_UNIT.mmHg).toInt()}/${p1.getDiastolic(VU_PRESSURE_UNIT.mmHg).toInt()}",
                                        ""
                                    )

                                val bpPulseUploadData =
                                    BioUpload(
                                        caseNo,
                                        staffId,
                                        SCDID,
                                        sysCode,
                                        getString(R.string.pulse),
                                        takenAt,
                                        "${p1.pulse}",
                                        ""
                                    )

                                LogUtil.logD(TAG, "systolic/diastolic: ${p1.getSystolic(VU_PRESSURE_UNIT.mmHg)}/${p1.getDiastolic(VU_PRESSURE_UNIT.mmHg)}")
                                LogUtil.logD(TAG, "pulse: ${p1.pulse}")
                                LogUtil.logD(TAG, "takenAt: $takenAt")
                            }
                        }
                    }
                }


            }

            override fun onWeightReceive(p0: VUBleDevice?, p1: VUWeight?, p2: VUError?) {

            }

            override fun onTemperatureReceive(p0: VUBleDevice?, p1: VUTemperature?, p2: VUError?) {
                when(p2 == null){
                    false -> LogUtil.logE(TAG, p2.msg)
                    true -> {
                        when(p1 == null){
                            false -> {
                                val takenAt = DateUtil.getNowTimestamp().div(1000L).toString()
                                val tempUploadData =
                                    BioUpload(
                                        caseNo,
                                        staffId,
                                        SCDID,
                                        sysCode,
                                        getString(R.string.temperature),
                                        takenAt,
                                        "${p1.getTemperature(VU_TEMPERATURE_UNIT.C).toInt()}",
                                        ""
                                    )

                                LogUtil.logD(TAG, "glucose: ${p1.getTemperature(VU_TEMPERATURE_UNIT.C)}")
                                LogUtil.logD(TAG, "takenAt: $takenAt")
                            }
                        }
                    }
                }
            }

            override fun onPulseOximetryReceive(
                p0: VUBleDevice?,
                p1: VUPulseOximetry?,
                p2: VUError?
            ) {

            }

            override fun onGlucoseReceive(p0: VUBleDevice?, p1: VUGlucose?, p2: VUError?) {

                when(p2 == null){
                    false -> LogUtil.logE(TAG, p2.msg)
                    true -> {
                        when(p1 == null){
                            false -> {
                                val takenAt = DateUtil.getNowTimestamp().div(1000L).toString()
                                val bgUploadData =
                                    BioUpload(
                                        caseNo,
                                        staffId,
                                        SCDID,
                                        sysCode,
                                        getString(R.string.blood_glucose),
                                        takenAt,
                                        "${p1.getGlucose(VU_GLUCOSE_UNIT.mg_dl).toInt()}",
                                        ""
                                    )

                                LogUtil.logD(TAG, "glucose: ${p1.getGlucose(VU_GLUCOSE_UNIT.mg_dl)}")
                                LogUtil.logD(TAG, "takenAt: $takenAt")
                            }
                        }
                    }
                }

            }

            override fun onPulseOximetryPPGDataReceive(
                p0: VUBleDevice?,
                p1: ArrayList<Double>?,
                p2: VUError?
            ) {

            }

            override fun onBloodPressureRawDataReceive(
                p0: VUBleDevice?,
                p1: Int,
                p2: Int,
                p3: Int
            ) {

            }

            override fun onDescriptorReadError(
                p0: VUBleDevice?,
                p1: BluetoothGattDescriptor?,
                p2: Int
            ) {

            }

            override fun onProgressUpdate(p0: VUBleDevice?, p1: Int, p2: Int) {

            }

            override fun onCharacteristicReadError(
                p0: VUBleDevice?,
                p1: BluetoothGattCharacteristic?,
                p2: Int
            ) {

            }

            override fun onHistoryDataReceive(
                p0: VUBleDevice?,
                p1: MutableList<out AbsDeviceData>?,
                p2: Class<*>?
            ) {

            }

            override fun onBloodPressureIntermediateCuffReceive(p0: VUBleDevice?, p1: Int) {

            }

            override fun onDescriptorWriteError(
                p0: VUBleDevice?,
                p1: BluetoothGattDescriptor?,
                p2: Int
            ) {

            }

            override fun onSetDateTimeFinish(p0: VUBleDevice?, p1: Boolean) {

            }

            override fun onCharacteristicWriteError(
                p0: VUBleDevice?,
                p1: BluetoothGattCharacteristic?,
                p2: Int
            ) {

            }

            override fun onServiceDiscoverError(p0: VUBleDevice?, p1: Int) {

            }

        }

    private fun initTempView(view: View){

        view.findViewById<ImageView>(R.id.add_temp).setOnClickListener {
            intentToManualInput(BioLiveData.Companion.BioType.Temperature)
        }

        view.findViewById<CardView>(R.id.block_temp).setOnClickListener {
            intentToHistoryDiagram(BioLiveData.Companion.BioType.Temperature)
        }

    }

    private fun initBloodGlucose(view: View){

        view.findViewById<ImageView>(R.id.add_blood_glucose).setOnClickListener {
            intentToManualInput(BioLiveData.Companion.BioType.BloodGlucose)
        }

        view.findViewById<CardView>(R.id.block_blood_glucose).setOnClickListener {
            intentToHistoryDiagram(BioLiveData.Companion.BioType.BloodGlucose)
        }

    }

    private fun initWeight(view: View){

        view.findViewById<ImageView>(R.id.add_weight).setOnClickListener {
            intentToManualInput(BioLiveData.Companion.BioType.Weight)
        }

        view.findViewById<CardView>(R.id.block_weight).setOnClickListener {
            intentToHistoryDiagram(BioLiveData.Companion.BioType.Weight)
        }

    }

    private fun initRespire(view: View){

        view.findViewById<ImageView>(R.id.add_respire).setOnClickListener {
            intentToManualInput(BioLiveData.Companion.BioType.Respire)
        }

        view.findViewById<CardView>(R.id.block_respire).setOnClickListener {
            intentToHistoryDiagram(BioLiveData.Companion.BioType.Respire)
        }

    }

    private fun initHeight(view: View){

        view.findViewById<ImageView>(R.id.add_height).setOnClickListener {
            intentToManualInput(BioLiveData.Companion.BioType.Height)
        }

        view.findViewById<CardView>(R.id.block_height).setOnClickListener {
            intentToHistoryDiagram(BioLiveData.Companion.BioType.Height)
        }

    }

    private fun initPulse(view: View){

        view.findViewById<ImageView>(R.id.add_pulse).setOnClickListener {
            intentToManualInput(BioLiveData.Companion.BioType.Pulse)
        }

        view.findViewById<CardView>(R.id.block_pulse).setOnClickListener {
            intentToHistoryDiagram(BioLiveData.Companion.BioType.Pulse)
        }

    }

    private fun initBloodPressure(view: View){

        view.findViewById<ImageView>(R.id.add_blood_pressure).setOnClickListener {
            intentToManualInput(BioLiveData.Companion.BioType.BloodPressure)
        }

        view.findViewById<CardView>(R.id.block_blood_pressure).setOnClickListener {
            intentToHistoryDiagram(BioLiveData.Companion.BioType.BloodPressure)
        }

    }

    private fun initOxygen(view: View){

        view.findViewById<ImageView>(R.id.add_oxygen).setOnClickListener {
            intentToManualInput(BioLiveData.Companion.BioType.Oxygen)
        }

        view.findViewById<CardView>(R.id.block_oxygen).setOnClickListener {
            intentToHistoryDiagram(BioLiveData.Companion.BioType.Oxygen)
        }

    }

    companion object{
        const val MEASURE_STAFF_ID = "MEASURE_STAFF_ID"
        const val MEASURE_CASE_NO = "MEASURE_CASE_NO"
        const val MEASURE_SCDID = "MEASURE_SCDID"
        const val MEASURE_SYS_CODE = "MEASURE_SYS_CODE"
    }

    private fun intentToManualInput(bioType: BioLiveData.Companion.BioType){

        bioViewModel.getSelectedType().value = bioType

        val bundle = Bundle()
        bundle.putString(MEASURE_CASE_NO, caseNo)
        bundle.putString(MEASURE_STAFF_ID, staffId)
        bundle.putString(MEASURE_SCDID, SCDID)
        bundle.putString(MEASURE_SYS_CODE, sysCode)
        val fragment = ManualInputFragment()
        fragment.arguments = bundle
        replaceFragment(this@MeasurementDashboardFragment, fragment, getString(R.string.tag_case_manual))
    }

    private fun intentToHistoryDiagram(bioType: BioLiveData.Companion.BioType){

        if(sysCode == SysKey.DAILY_NURSING_CODE){

            bioViewModel.getSelectedType().value = bioType

            replaceFragment(
                this@MeasurementDashboardFragment,
                HistoryFragment(),
                getString(R.string.tag_case_history_diagram)
            )
        }
    }

}
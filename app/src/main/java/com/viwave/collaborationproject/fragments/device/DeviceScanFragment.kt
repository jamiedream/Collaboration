/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/31/20 5:33 PM
 */

package com.viwave.collaborationproject.fragments.device

import android.app.AlertDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.DB.cache.DeviceKey
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.device.adapter.ScanListAdapter
import com.viwave.collaborationproject.fragments.device.data.DeviceTypeData
import com.viwave.collaborationproject.fragments.device.data.MeasurementDevice
import com.viwave.collaborationproject.utils.LogUtil
import com.viwave.collaborationproject.utils.PreferenceUtil
import com.viwaveulife.vuioht.VUBleDevice
import com.viwaveulife.vuioht.VUBleManager
import com.viwaveulife.vuioht.VUBleScanFilter
import com.viwaveulife.vuioht.VUScanCallback
import java.util.*

class DeviceScanFragment(private val typeInfo: DeviceTypeData): BaseFragment(), IDeviceClicked, BackPressedDelegate {

    override fun onBackPressed(): Boolean {
        fragmentManager?.popBackStack()
        return true
    }

    private val TAG = this::class.java.simpleName
    private val imgDevice by lazy { view!!.findViewById<ImageView>(R.id.device_img) }
    private val desDevice by lazy { view!!.findViewById<TextView>(R.id.device_description) }
    private val listDevice by lazy { view!!.findViewById<RecyclerView>(R.id.cmn_recycler) }
    private val btnScan by lazy { view!!.findViewById<Button>(R.id.btn_scan) }

    private val vuBleManager: VUBleManager by lazy { VUBleManager.getInstance() }
    private val vuObservedDevice by lazy { vuBleManager.allObserveDevice }
    private var vuBleDeviceList = mutableListOf<VUBleDevice>()
    private val scanListAdapter by lazy { ScanListAdapter(vuBleDeviceList, this) }

    private var bluetoothAdapter: BluetoothAdapter? = null
    private val REQUEST_ENABLE_BT = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device_scan, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity!!.packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            val bluetoothManager = activity!!.getSystemService(
                Context.BLUETOOTH_SERVICE
            ) as BluetoothManager
            bluetoothAdapter = bluetoothManager.adapter
        } else {
            LogUtil.logE(TAG, "No BLE Feature")
        }

        if (bluetoothAdapter == null) {
            LogUtil.logE(TAG, "BLE not supported")
        }

        vuBleManager.setScanCallback(scanCallback)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(String.format(getString(R.string.device_scan_title), typeInfo.type))
        setToolbarLeftIcon(false)

        if (bluetoothAdapter != null && !bluetoothAdapter!!.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        imgDevice.setImageResource(typeInfo.img)
        desDevice.text = typeInfo.des

        btnScan.setOnClickListener { scan() }
    }

    private fun scan(){

        if (!vuBleManager.isGetLocationPermission) {
            showRequestLocationPermissionNotice()
            return
        }

        scanLeDevice(true, typeInfo.type)

        listDevice?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = scanListAdapter
            val decorator =
                DividerItemDecoration(
                    context,
                    LinearLayoutManager(context).orientation
                )
            decorator.setDrawable(resources.getDrawable(R.drawable.line_case_list, null))
            addItemDecoration(decorator)
        }
    }

    override fun onClicked(type: String, device: MeasurementDevice) {}

    override fun onClicked(typeInfo: DeviceTypeData) {}

    override fun onClicked(type: String, device: VUBleDevice?) {

        if(device == null) {
            Toast.makeText(context, "Device is null.", Toast.LENGTH_LONG).show()
            return
        }

        scanLeDevice(false, type)

        val typeDeviceList =
            mutableListOf<MeasurementDevice>().apply {
                vuObservedDevice.forEach{
                    PreferenceUtil.loadDevice(it.value.id)?.let {
                        if(it.deviceSku == typeInfo.type)
                            this.add(it)
                    }
                }
            }

        addScanDevice(typeDeviceList, device)

    }

    private val scanCallback =
        object : VUScanCallback() {
            override fun onScanResult(scanDevice: VUBleDevice) {
                LogUtil.logD(
                    TAG,
                    "rssi: ${scanDevice.rssi} \n name: ${scanDevice.name} \n id:${scanDevice.id}"
                )
                if(!vuBleDeviceList.contains(scanDevice) && !vuObservedDevice.containsValue(scanDevice)){
                    if(desDevice.visibility != View.GONE) desDevice.visibility = View.GONE
                    vuBleDeviceList.add(scanDevice)
                    scanListAdapter.notifyDataSetChanged()
                }

            }
        }

    private fun showRequestLocationPermissionNotice() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.device_notice)
        builder.setMessage(R.string.device_request_location_permission_msg)
        builder.setPositiveButton(
            android.R.string.ok
        ) { _: DialogInterface?, i: Int -> vuBleManager.requestLocationPermission(this) }
        builder.show()
    }

    private fun scanLeDevice(enable: Boolean, type: String) {
        if (enable) {
            val scanFilterList: MutableList<VUBleScanFilter> =
                ArrayList()
            when (type) {
                DeviceKey.DEVICE_X3 ->
                    scanFilterList.addAll(VUBleScanFilter.generateRossmaxBloodPressureX3ScanFilterList())
                DeviceKey.DEVICE_X5 ->
                    scanFilterList.addAll(VUBleScanFilter.generateRossmaxBloodPressureX5ScanFilterList())
                DeviceKey.DEVICE_HT100 ->
                    scanFilterList.addAll(VUBleScanFilter.generateRossmaxBloodGlucoseHT100ScanFilterList())
                DeviceKey.DEVICE_HS200 ->
                    scanFilterList.addAll(VUBleScanFilter.generateRossmaxBloodGlucoseHS200ScanFilterList())
                DeviceKey.DEVICE_HC700 ->
                    scanFilterList.addAll(VUBleScanFilter.generateRossmaxThermometerHC700ScanFilterList())
                DeviceKey.DEVICE_LS212B ->
                    scanFilterList.addAll(VUBleScanFilter.generateRossmaxWeightScaleLS212BScanFilterList())
                DeviceKey.DEVICE_SB210 ->
                    scanFilterList.addAll(VUBleScanFilter.generateRossmaxPulseOximeterSB210ScanFilterList())
            }
            vuBleManager.startScan(scanFilterList, 0)
        } else {
            vuBleManager.stopScan()
        }
    }

    private fun addScanDevice(deviceList: MutableList<MeasurementDevice>, device: VUBleDevice){

        var count = 1
        val macAddress = device.id
        if(vuBleManager.addObserveDevice(macAddress, device)){
            val measurementDevice: MeasurementDevice? =
                when(typeInfo.type){
                    DeviceKey.DEVICE_X3 -> {
                        deviceList.forEach {
                            if(it.deviceName.contains(DeviceKey.DEVICE_X3)) count+= 1
                        }
                        MeasurementDevice(
                            macAddress,
                            device.name,
                            DeviceKey.DEVICE_X3,
                            DeviceKey.DEVICE_KEY_BP,
                            "${DeviceKey.DEVICE_X3}-${String.format("%02d", count)}"
                        )
                    }
                    DeviceKey.DEVICE_X5 -> {
                        deviceList.forEach {
                            if(it.deviceName.contains(DeviceKey.DEVICE_X5)) count+= 1
                        }
                        MeasurementDevice(
                            macAddress,
                            device.name,
                            DeviceKey.DEVICE_X5,
                            DeviceKey.DEVICE_KEY_BP,
                            "${DeviceKey.DEVICE_X5}-${String.format("%02d", count)}"
                        )
                    }
                    DeviceKey.DEVICE_HT100 -> {
                        deviceList.forEach {
                            if(it.deviceName.contains(DeviceKey.DEVICE_HT100)) count+= 1
                        }
                        MeasurementDevice(
                            macAddress,
                            device.name,
                            DeviceKey.DEVICE_HT100,
                            DeviceKey.DEVICE_KEY_BG,
                            "${DeviceKey.DEVICE_HT100}-${String.format("%02d", count)}"
                        )
                    }
                    DeviceKey.DEVICE_HS200 -> {
                        deviceList.forEach {
                            if(it.deviceName.contains(DeviceKey.DEVICE_HS200)) count+= 1
                        }
                        MeasurementDevice(
                            macAddress,
                            device.name,
                            DeviceKey.DEVICE_HS200,
                            DeviceKey.DEVICE_KEY_BG,
                            "${DeviceKey.DEVICE_HS200}-${String.format("%02d", count)}"
                        )
                    }
                    DeviceKey.DEVICE_HC700 -> {
                        deviceList.forEach {
                            if (it.deviceName.contains(DeviceKey.DEVICE_HC700)) count += 1
                        }
                        MeasurementDevice(
                            macAddress,
                            device.name,
                            DeviceKey.DEVICE_HC700,
                            DeviceKey.DEVICE_KEY_TEMP,
                            "${DeviceKey.DEVICE_HC700}-${String.format("%02d", count)}"
                        )
                    }
                    DeviceKey.DEVICE_LS212B -> {
                        deviceList.forEach {
                            if (it.deviceName.contains(DeviceKey.DEVICE_LS212B)) count += 1
                        }
                        MeasurementDevice(
                            macAddress,
                            device.name,
                            DeviceKey.DEVICE_LS212B,
                            DeviceKey.DEVICE_KEY_WEIGHT,
                            "${DeviceKey.DEVICE_LS212B}-${String.format("%02d", count)}"
                        )
                    }
                    DeviceKey.DEVICE_SB210 -> {
                        deviceList.forEach {
                            if (it.deviceName.contains(DeviceKey.DEVICE_SB210)) count += 1
                        }
                        MeasurementDevice(
                            macAddress,
                            device.name,
                            DeviceKey.DEVICE_SB210,
                            DeviceKey.DEVICE_KEY_OXYGEN,
                            "${DeviceKey.DEVICE_SB210}-${String.format("%02d", count)}"
                        )
                    }
                    else -> null
                }

            measurementDevice?.apply {
                PreferenceUtil.saveDevice(macAddress, measurementDevice)
                replaceFragment(this@DeviceScanFragment, DeviceRenameFragment(measurementDevice), getString(R.string.tag_device_rename))
            }

        }else Toast.makeText(context, "SDK add observe device failed.", Toast.LENGTH_LONG).show()
    }
}
package com.viwave.collaborationproject.fragments

import android.app.AlertDialog.Builder
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
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import com.viwave.collaborationproject.DB.cache.DeviceKey
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.utils.LogUtil
import com.viwaveulife.vuioht.VUBleDevice
import com.viwaveulife.vuioht.VUBleManager
import com.viwaveulife.vuioht.VUBleScanFilter
import com.viwaveulife.vuioht.VUScanCallback
import java.util.*

class DeviceFragment: BaseFragment() {

    private val TAG = this::class.java.simpleName

    private lateinit var stringUnbind: String
    private lateinit var stringBind: String

    private val vuBleManager: VUBleManager by lazy { VUBleManager.getInstance() }
    private var vuBleDeviceList = mutableListOf<VUBleDevice>()
    private val adapter by lazy { DeviceListAdapter(vuBleDeviceList) }

    private val txtMacBP by lazy { view!!.findViewById<TextView>(R.id.mac_bp) }
    private val txtMacBG by lazy { view!!.findViewById<TextView>(R.id.mac_bg) }
    private val txtMacTemp by lazy { view!!.findViewById<TextView>(R.id.mac_temp) }

    private val btnBindBP by lazy { view!!.findViewById<Button>(R.id.btn_device_bp) }
    private val btnBindBG by lazy { view!!.findViewById<Button>(R.id.btn_device_bg) }
    private val btnBindTemp by lazy { view!!.findViewById<Button>(R.id.btn_device_temp) }

    private var tempView: View? = null
    private var bluetoothAdapter: BluetoothAdapter? = null
    private val REQUEST_ENABLE_BT = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device, container, false)
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

        stringUnbind = getString(R.string.device_unbind)
        stringBind = getString(R.string.device_bind)

        vuBleManager.setScanCallback(scanCallback)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(getString(R.string.menu_measurement_device))
        setToolbarLeftIcon(true)

        if (bluetoothAdapter != null && !bluetoothAdapter!!.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }

        initBPX3()
        initTempHC700()
        initBGHS200()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        val status =
            vuBleManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
        when (status) {
            VUBleManager.PERMISSION_GRANTED ->
                if (tempView != null) {
                    tempView!!.performClick()
                    tempView = null
                }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private val scanCallback =
        object : VUScanCallback() {
            override fun onScanResult(scanDevice: VUBleDevice) {
                LogUtil.logD(
                    TAG,
                    "rssi: ${scanDevice.rssi} \n name: ${scanDevice.name} \n id:${scanDevice.id}"
                )
                if(!vuBleDeviceList.contains(scanDevice)){
                    vuBleDeviceList.add(scanDevice)
                    adapter.notifyDataSetChanged()
                }

            }
        }

    private fun initBPX3() {
        val device = vuBleManager.getObserveDevice(DeviceKey.DEVICE_SKU_BP_X3)
        if (device != null) {
            btnBindBP.text = stringUnbind
            txtMacBP.text = device.id
        } else {
            btnBindBP.text = stringBind
        }

        btnBindBP.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(v: View?) {
                    when (btnBindBP.text.toString() == stringBind) {
                        true -> {
                            if (!vuBleManager.isGetLocationPermission) {
                                showRequestLocationPermissionNotice()
                                tempView = v
                                return
                            }
                            val type = DeviceKey.DEVICE_SKU_BP_X3
                            scanLeDevice(true, type)
                            showDeviceList(type)
                            vuBleDeviceList.clear()
                            adapter.notifyDataSetChanged()
                        }
                        false -> {
                            vuBleManager.delObserveDevice(device)
                            txtMacBP.text = ""
                            btnBindBP.text = stringBind
                        }
                    }
                }

            }
        )
    }

    private fun initBGHS200() {
        val device = vuBleManager.getObserveDevice(DeviceKey.DEVICE_SKU_BG_HS200)
        if (device != null) {
            btnBindBG.text = stringUnbind
            txtMacBG.text = device.id
        } else {
            btnBindBG.text = stringBind
        }

        btnBindBG.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(v: View?) {
                    when (btnBindBG.text.toString() == stringBind) {
                        true -> {
                            if (!vuBleManager.isGetLocationPermission) {
                                showRequestLocationPermissionNotice()
                                tempView = v
                                return
                            }
                            val type = DeviceKey.DEVICE_SKU_BG_HS200
                            scanLeDevice(true, type)
                            showDeviceList(type)
                            vuBleDeviceList.clear()
                            adapter.notifyDataSetChanged()
                        }
                        false -> {
                            vuBleManager.delObserveDevice(device)
                            txtMacBG.text = ""
                            btnBindBG.text = stringBind
                        }
                    }
                }

            }
        )
    }

    private fun initTempHC700() {
        val device = vuBleManager.getObserveDevice(DeviceKey.DEVICE_SKU_TM_HC700)
        if (device != null) {
            btnBindTemp.text = stringUnbind
            txtMacTemp.text = device.id
        } else {
            btnBindTemp.text = stringBind
        }

        btnBindTemp.setOnClickListener(
            object : View.OnClickListener {
                override fun onClick(v: View?) {
                    when (btnBindTemp.text.toString() == stringBind) {
                        true -> {
                            if (!vuBleManager.isGetLocationPermission) {
                                showRequestLocationPermissionNotice()
                                tempView = v
                                return
                            }
                            val type = DeviceKey.DEVICE_SKU_TM_HC700
                            scanLeDevice(true, type)
                            showDeviceList(type)
                            vuBleDeviceList.clear()
                            adapter.notifyDataSetChanged()
                        }
                        false -> {
                            vuBleManager.delObserveDevice(device)
                            txtMacTemp.text = ""
                            btnBindTemp.text = stringBind
                        }
                    }
                }

            }
        )
    }

    private fun showRequestLocationPermissionNotice() {
        val builder = Builder(context)
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
                DeviceKey.DEVICE_SKU_BP_X3 -> {
                    scanFilterList.addAll(VUBleScanFilter.generateRossmaxBloodPressureX3ScanFilterList())
                }
                DeviceKey.DEVICE_SKU_BG_HS200 -> {
                    scanFilterList.addAll(VUBleScanFilter.generateRossmaxBloodGlucoseHS200ScanFilterList())
                }
                DeviceKey.DEVICE_SKU_TM_HC700 -> {
                    scanFilterList.addAll(VUBleScanFilter.generateRossmaxThermometerHC700ScanFilterList())
                }
            }
            vuBleManager.startScan(scanFilterList, 0)
        } else {
            vuBleManager.stopScan()
        }

    }

    private fun showDeviceList(type: String) {
        val builder = Builder(context)
        builder.setTitle(getString(R.string.device_scan))
        builder.setSingleChoiceItems(
            adapter,
            0
        ) { dialog: DialogInterface, which: Int ->
            //bind device
            scanLeDevice(false, type)
            val device = adapter.getItem(which) as VUBleDevice
            val macAddress = device.id
            when (type) {
                DeviceKey.DEVICE_SKU_BP_X3 -> {
                    LogUtil.logD(TAG, macAddress)
                    txtMacBP.text = macAddress
                    vuBleManager.addObserveDevice(DeviceKey.DEVICE_SKU_BP_X3, device)
                    btnBindBP.text = stringUnbind
                }
                DeviceKey.DEVICE_SKU_BG_HS200 -> {
                    txtMacBG.text = macAddress
                    vuBleManager.addObserveDevice(DeviceKey.DEVICE_SKU_BG_HS200, device)
                    btnBindBG.text = stringUnbind
                }
                DeviceKey.DEVICE_SKU_TM_HC700 -> {
                    txtMacTemp.text = macAddress
                    vuBleManager.addObserveDevice(DeviceKey.DEVICE_SKU_TM_HC700, device)
                    btnBindTemp.text = stringUnbind
                }
            }
            dialog.dismiss()
        }

        builder.setNegativeButton(
            android.R.string.cancel
        ) { dialog: DialogInterface, _: Int ->
            scanLeDevice(false, type)
            dialog.dismiss()
        }

        builder.create().show()
    }

    private inner class DeviceListAdapter(private var deviceList: MutableList<VUBleDevice>) : BaseAdapter() {
        override fun getCount(): Int {
            return deviceList.size
        }

        override fun getItem(position: Int): Any {
            return deviceList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, view: View?, parent: ViewGroup): View? {
            var convertView: View? = view
            val holder: ViewHolder
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.view_list_item_ble, parent, false)
                holder = ViewHolder()
                holder.name = convertView?.findViewById(R.id.name)
                holder.address = convertView?.findViewById(R.id.address)
                convertView?.tag = holder
            } else {
                holder = convertView.tag as ViewHolder
            }
            val device = getItem(position) as VUBleDevice
            holder.name!!.text = device.name
            holder.address!!.text = device.id
            return convertView
        }

        inner class ViewHolder {
            var name: TextView? = null
            var address: TextView? = null
        }
    }

}
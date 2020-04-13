/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/31/20 5:32 PM
 */

package com.viwave.collaborationproject.fragments.device

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.DB.cache.DeviceKey
import com.viwave.collaborationproject.DB.cache.DeviceKey.DEVICE_KEY_BG
import com.viwave.collaborationproject.DB.cache.DeviceKey.DEVICE_KEY_BP
import com.viwave.collaborationproject.DB.cache.DeviceKey.DEVICE_KEY_OXYGEN
import com.viwave.collaborationproject.DB.cache.DeviceKey.DEVICE_KEY_TEMP
import com.viwave.collaborationproject.DB.cache.DeviceKey.DEVICE_KEY_WEIGHT
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.device.adapter.BindingListAdapter
import com.viwave.collaborationproject.fragments.device.data.DeviceData
import com.viwave.collaborationproject.fragments.device.data.DeviceTypeData
import com.viwave.collaborationproject.fragments.device.data.MeasurementDevice
import com.viwave.collaborationproject.utils.PreferenceUtil
import com.viwaveulife.vuioht.VUBleDevice
import com.viwaveulife.vuioht.VUBleManager

class DeviceBindingListFragment: BaseFragment(), IDeviceClicked, BackPressedDelegate {

    override fun onBackPressed(): Boolean {
        return true
    }

    private val TAG = this::class.java.simpleName

    private val vuBleManager: VUBleManager by lazy { VUBleManager.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_cmn_recycler, container, false)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(getString(R.string.menu_measurement_device))
        setToolbarLeftIcon(true)

        val bpDeviceList = mutableListOf<MeasurementDevice>()
        val bgDeviceList = mutableListOf<MeasurementDevice>()
        val tempDeviceList = mutableListOf<MeasurementDevice>()
        val oxygenDeviceList = mutableListOf<MeasurementDevice>()
        val weightDeviceList = mutableListOf<MeasurementDevice>()
        vuBleManager.allObserveDevice.forEach {
            PreferenceUtil.loadDevice(it.value.id)?.let { measurementDevice ->
                when(measurementDevice.deviceSku){
                    DeviceKey.DEVICE_X3 -> bpDeviceList.add(measurementDevice)
                    DeviceKey.DEVICE_X5 -> bpDeviceList.add(measurementDevice)
                    DeviceKey.DEVICE_HC700 -> tempDeviceList.add(measurementDevice)
                    DeviceKey.DEVICE_HT100 -> bgDeviceList.add(measurementDevice)
                    DeviceKey.DEVICE_HS200 -> bgDeviceList.add(measurementDevice)
                    DeviceKey.DEVICE_LS212B -> weightDeviceList.add(measurementDevice)
                    DeviceKey.DEVICE_SB210 -> oxygenDeviceList.add(measurementDevice)
                    else -> {}
                }
            }
        }

        val deviceList =
            mutableListOf<DeviceData>().apply {
                DeviceKey.getSupportDevice()[DEVICE_KEY_BP]?.let {
                    this.add(
                        DeviceData(
                            getString(R.string.device_blood_pressure),
                            bpDeviceList
                        )
                    )
                }
                DeviceKey.getSupportDevice()[DEVICE_KEY_BG]?.let {
                    this.add(
                        DeviceData(
                            getString(R.string.device_blood_glucose),
                            bgDeviceList
                        )
                    )
                }
                DeviceKey.getSupportDevice()[DEVICE_KEY_TEMP]?.let {
                    this.add(
                        DeviceData(
                            getString(R.string.device_temperature),
                            tempDeviceList
                        )
                    )
                }
                DeviceKey.getSupportDevice()[DEVICE_KEY_OXYGEN]?.let {
                    this.add(
                        DeviceData(
                            getString(R.string.device_oxygen),
                            oxygenDeviceList
                        )
                    )
                }
                DeviceKey.getSupportDevice()[DEVICE_KEY_WEIGHT]?.let {
                    this.add(
                        DeviceData(
                            getString(R.string.device_weight),
                            weightDeviceList
                        )
                    )
                }
            }

        deviceListView(deviceList)
    }

    private fun deviceListView(deviceList: MutableList<DeviceData>){
        val bindingListAdapter = BindingListAdapter(deviceList, this)
        val recyclerView = view?.findViewById<RecyclerView>(R.id.cmn_recycler)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bindingListAdapter
            val decorator =
                DividerItemDecoration(
                    context,
                    LinearLayoutManager(context).orientation
                )
            decorator.setDrawable(resources.getDrawable(R.drawable.line_case_list, null))
            addItemDecoration(decorator)
        }
    }

    override fun onClicked(type: String, device: VUBleDevice?) {
        val deviceTypeList = getSupportDeviceTypeList(type)
        if(deviceTypeList.size == 1)
            replaceFragment(this, DeviceScanFragment(deviceTypeList[0]), getString(R.string.tag_device_list))
        else
            replaceFragment(this, DeviceListFragment(type, deviceTypeList), getString(R.string.tag_device_list))
    }

    private fun getSupportDeviceTypeList(type: String): MutableList<DeviceTypeData>{

        return mutableListOf<DeviceTypeData>().apply {
            when(type){
                getString(R.string.device_blood_pressure) -> {
                    DeviceKey.getSupportDevice()[DEVICE_KEY_BP]?.forEach {
                        when(it){
                            DeviceKey.DEVICE_X3 ->
                                this.add(
                                    DeviceTypeData(
                                        DeviceKey.DEVICE_X3,
                                        R.mipmap.img_x3,
                                        getString(R.string.device_description_x3)
                                    )
                                )
                            DeviceKey.DEVICE_X5 ->
                                this.add(
                                    DeviceTypeData(
                                        DeviceKey.DEVICE_X5,
                                        R.mipmap.img_x5,
                                        "" //todo
                                    )
                                )
                        }
                    }
                }
                getString(R.string.device_blood_glucose) -> {
                    DeviceKey.getSupportDevice()[DEVICE_KEY_BG]?.forEach {
                        when(it){
                            DeviceKey.DEVICE_HT100 ->
                                this.add(
                                    DeviceTypeData(
                                        DeviceKey.DEVICE_HT100,
                                        R.mipmap.img_ht100,
                                        getString(R.string.device_description_ht100)
                                    )
                                )
                            DeviceKey.DEVICE_HS200 ->
                                this.add(
                                    DeviceTypeData(
                                        DeviceKey.DEVICE_HS200,
                                        R.mipmap.img_hs200,
                                        "" //todo
                                    )
                                )
                        }
                    }
                }
                getString(R.string.device_temperature) -> {
                    DeviceKey.getSupportDevice()[DEVICE_KEY_TEMP]?.forEach {
                        when(it){
                            DeviceKey.DEVICE_HC700 ->
                                this.add(
                                    DeviceTypeData(
                                        DeviceKey.DEVICE_HC700,
                                        R.mipmap.img_hc700,
                                        getString(R.string.device_description_hc700)
                                    )
                                )
                        }
                    }
                }
                getString(R.string.device_oxygen) -> {
                    DeviceKey.getSupportDevice()[DEVICE_KEY_OXYGEN]?.forEach {
                        when(it){
                            DeviceKey.DEVICE_SB210 ->
                                this.add(
                                    DeviceTypeData(
                                        DeviceKey.DEVICE_SB210,
                                        //todo
                                        R.mipmap.img_hc700,
                                        ""
                                    )
                                )
                        }
                    }
                }
                getString(R.string.device_weight) -> {
                    DeviceKey.getSupportDevice()[DEVICE_KEY_WEIGHT]?.forEach {
                        when(it){
                            DeviceKey.DEVICE_LS212B ->
                                this.add(
                                    DeviceTypeData(
                                        DeviceKey.DEVICE_LS212B,
                                        //todo
                                        R.mipmap.img_hc700,
                                        ""
                                    )
                                )
                        }
                    }
                }
            }
        }
    }

    override fun onClicked(type: String, device: MeasurementDevice) {
        replaceFragment(this, DeviceDetailFragment(device), getString(R.string.tag_device_detail))
    }

    override fun onClicked(typeInfo: DeviceTypeData) {

    }
}
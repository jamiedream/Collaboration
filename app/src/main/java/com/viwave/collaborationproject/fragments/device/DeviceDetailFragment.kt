/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 6/ 4/ 2020.
 * Last modified 4/6/20 2:35 PM
 */

package com.viwave.collaborationproject.fragments.device

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.device.adapter.DeviceDetailAdapter
import com.viwave.collaborationproject.fragments.device.data.DeviceDetailData
import com.viwave.collaborationproject.fragments.device.data.DeviceTypeData
import com.viwave.collaborationproject.fragments.device.data.MeasurementDevice
import com.viwave.collaborationproject.utils.PreferenceUtil
import com.viwaveulife.vuioht.VUBleDevice
import com.viwaveulife.vuioht.VUBleManager

class DeviceDetailFragment(private val device: MeasurementDevice): BaseFragment(), IDeviceClicked, BackPressedDelegate {

    override fun onBackPressed(): Boolean {
        fragmentManager?.popBackStack()
        return true
    }

    private val TAG = this::class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(getString(R.string.device_detail_title))
        setToolbarLeftIcon(false)
        val infoList =
            mutableListOf<DeviceDetailData>().apply {
                this.add(DeviceDetailData(getString(R.string.device_detail_brand), device.deviceName))
                this.add(DeviceDetailData(getString(R.string.device_detail_type), device.deviceSku))
                this.add(DeviceDetailData(getString(R.string.device_rename_title), PreferenceUtil.loadDevice(device.macAddress)?.deviceNickname?: ""))
            }
        val deviceDetailAdapter = DeviceDetailAdapter(device, infoList, this)
        view.findViewById<RecyclerView>(R.id.cmn_recycler)?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = deviceDetailAdapter
            val decorator =
                DividerItemDecoration(
                    context,
                    LinearLayoutManager(context).orientation
                )
            decorator.setDrawable(resources.getDrawable(R.drawable.line_case_list, null))
            addItemDecoration(decorator)
        }

        view.findViewById<Button>(R.id.detail_btn_unbind).setOnClickListener {
            if(VUBleManager.getInstance().delObserveDevice(VUBleManager.getInstance().getObserveDevice(device.macAddress))){
                PreferenceUtil.saveDevice(device.macAddress)
                fragmentManager?.popBackStack()
            }
        }
    }

    override fun onClicked(type: String, device: VUBleDevice?) {}

    override fun onClicked(typeInfo: DeviceTypeData) {}

    override fun onClicked(type: String, device: MeasurementDevice) {
        replaceFragment(this, DeviceRenameFragment(PreferenceUtil.loadDevice(device.macAddress)!!), getString(R.string.tag_device_rename))
    }
}
/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/31/20 5:33 PM
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
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.device.adapter.DeviceListAdapter
import com.viwave.collaborationproject.fragments.device.data.DeviceTypeData
import com.viwave.collaborationproject.fragments.device.data.MeasurementDevice
import com.viwaveulife.vuioht.VUBleDevice

class DeviceListFragment(private val type: String, private val deviceTypeList: MutableList<DeviceTypeData>): BaseFragment(), IDeviceClicked, BackPressedDelegate {

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
        return inflater.inflate(R.layout.view_cmn_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarTitle(String.format(getString(R.string.device_list_title), type))
        setToolbarLeftIcon(false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.cmn_recycler)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            val decorator =
                DividerItemDecoration(
                    context,
                    LinearLayoutManager(context).orientation
                )
            decorator.setDrawable(resources.getDrawable(R.drawable.line_case_list, null))
            addItemDecoration(decorator)
        }

        recyclerView?.adapter = DeviceListAdapter(deviceTypeList, this)
    }

    override fun onClicked(type: String, device: VUBleDevice?) {}

    override fun onClicked(type: String, device: MeasurementDevice) {}

    override fun onClicked(typeInfo: DeviceTypeData) {
        replaceFragment(this, DeviceScanFragment(typeInfo), getString(R.string.tag_device_list))

    }
}
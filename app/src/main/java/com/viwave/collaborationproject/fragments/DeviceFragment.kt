package com.viwave.collaborationproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.viwave.collaborationproject.R

class DeviceFragment: BaseFragment() {

    private val TAG = this::class.java.simpleName

    private val txtMacBP by lazy { view!!.findViewById<TextView>(R.id.mac_bp) }
    private val txtMacBG by lazy { view!!.findViewById<TextView>(R.id.mac_bg) }
    private val txtMacTemp by lazy { view!!.findViewById<TextView>(R.id.mac_temp) }
    
    private val btnBindBP by lazy { view!!.findViewById<Button>(R.id.btn_device_bp) }
    private val btnBindBG by lazy { view!!.findViewById<Button>(R.id.btn_device_bg) }
    private val btnBindTemp by lazy { view!!.findViewById<Button>(R.id.btn_device_temp) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_device, container, false)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(getString(R.string.menu_measurement_device))
        setToolbarLeftIcon(true)
    }
}
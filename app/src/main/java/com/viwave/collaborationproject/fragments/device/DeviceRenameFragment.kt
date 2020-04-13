/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 6/ 4/ 2020.
 * Last modified 4/6/20 12:28 PM
 */

package com.viwave.collaborationproject.fragments.device

import android.os.Bundle
import android.view.*
import android.widget.EditText
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.device.data.MeasurementDevice
import com.viwave.collaborationproject.utils.InputControlUtil
import com.viwave.collaborationproject.utils.PreferenceUtil
import java.lang.ref.WeakReference

class DeviceRenameFragment(private val device: MeasurementDevice): BaseFragment(), BackPressedDelegate{

    override fun onBackPressed(): Boolean {
        fragmentManager?.popBackStack()
        return true
    }

    private val TAG = this::class.java.simpleName
    private val editRename by lazy { view!!.findViewById<EditText>(R.id.rename_edit) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_device_rename, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_device_name_check, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.device_rename_check -> {
                changeName()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(getString(R.string.device_rename_title))
        setToolbarLeftIcon(false)

        editRename.setText(device.deviceNickname)
        if(editRename.requestFocus()){
            editRename.setSelection(device.deviceNickname.length)
            InputControlUtil.showKeyboard(WeakReference((activity)))
        }

        editRename.setOnEditorActionListener { _, _, _ ->
            changeName()
            true
        }

    }

    private fun changeName(){
        if(!editRename.text.isNullOrEmpty()){
            PreferenceUtil.saveDevice(
                device.macAddress,
                MeasurementDevice(
                    device.macAddress,
                    device.deviceName,
                    device.deviceSku,
                    device.deviceCategory,
                    editRename.text.toString()
                )
            )
            fragmentManager?.popBackStack()
        }
    }

}
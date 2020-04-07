/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import com.viwave.collaborationproject.DB.remote.BioAction
import com.viwave.collaborationproject.MainActivity.Companion.generalViewModel
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.data.cases.Case
import com.viwave.collaborationproject.data.http.HttpErrorData
import com.viwave.collaborationproject.data.http.UploadBioDto
import com.viwave.collaborationproject.http.HttpClientService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class PendingDataFragment: BaseFragment() {

    private val rootView:View by lazy { view!!.findViewById<TextView>(R.id.app_version) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pending_data, container, false)
    }


    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        inflater.inflate(R.menu.menu_pending_data, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.upload -> {
                testUpload()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun testUpload() {
        val uploadBioList: ArrayList<UploadBioDto> = ArrayList();
        var uploadData = UploadBioDto(
            "DCC099001",
            "n00198",
            "1818781876",
            "血糖",
            "50",
            "S03",
            "",
            "",
            "001"
        )
        uploadBioList.add(uploadData)

        uploadData = UploadBioDto(
            "DCC099001",
            "n00198",
            "1818781876",
            "體溫",
            "36.4",
            "S03",
            "",
            "",
            "002"
        )
        uploadBioList.add(uploadData)

        HttpClientService.uploadBio(uploadBioList, object : HttpClientService.HttpCallback<String> {
            override fun onSuccess(data: String) {
                Log.v("YuYu", "onSuccess $data");
            }

            override fun onFailure(errData: HttpErrorData) {
                Log.v("YuYu", "onFailure ${errData.code} ${errData.message}");
            }
        })
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle(getString(R.string.menu_unupload_data))
        setToolbarLeftIcon(true)

        GlobalScope.launch(Dispatchers.IO){
            val dataMap = BioAction.getAllPendingData()
            val sysList = generalViewModel.getLoginUser().value?.sysList
            //確認 sys 的排列順序
            sysList?.forEach {
                val caseMap: TreeMap<Case, ArrayList<Bio>> = dataMap[it.sysCode] ?: return@forEach

                //TODO add UI
            }
        }


    }

    override fun onResume() {
        super.onResume()

    }

    override fun onStop() {
        super.onStop()
    }


}
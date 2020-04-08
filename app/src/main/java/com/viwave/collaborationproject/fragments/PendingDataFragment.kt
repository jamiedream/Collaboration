/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.http.HttpErrorData
import com.viwave.collaborationproject.data.http.UploadBioDto
import com.viwave.collaborationproject.http.HttpClientService

class PendingDataFragment: BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(getString(R.string.menu_unupload_data))
        setToolbarLeftIcon(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
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
        val uploadBioList:ArrayList<UploadBioDto> = ArrayList();
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

        HttpClientService.uploadBio(uploadBioList, object: HttpClientService.HttpCallback<String>{
            override fun onSuccess(data: String) {
                Log.v("YuYu", "onSuccess $data" );
            }

            override fun onFailure(errData: HttpErrorData) {
                Log.v("YuYu", "onFailure ${errData.code} ${errData.message}");
            }
        })
    }
}
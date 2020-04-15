/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 15/ 4/ 2020.
 * Last modified 4/15/20 3:03 PM
 */

package com.viwave.collaborationproject.fragments.widgets

import android.app.AlertDialog
import android.content.Context
import com.viwave.collaborationproject.R

object LoadingDialog {

    private lateinit var loadingDialog: AlertDialog
    fun startLoadingDialog(context: Context?): AlertDialog{
        loadingDialog =
            AlertDialog.Builder(context)
                .setView(R.layout.view_cmn_progressbar)
                .show()
        return loadingDialog
    }

    fun dismissLoadingDialog(){
        loadingDialog.dismiss()
    }

}
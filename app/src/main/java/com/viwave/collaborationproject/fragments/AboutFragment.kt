/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.viwave.collaborationproject.BuildConfig
import com.viwave.collaborationproject.R

class AboutFragment: BaseFragment() {

    private val TAG = this::class.java.simpleName
    private val textAppVersion by lazy { view!!.findViewById<TextView>(R.id.app_version) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(getString(R.string.menu_about))
        setToolbarLeftIcon(true)
        textAppVersion.text = BuildConfig.VERSION_NAME
    }
}
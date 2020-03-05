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
        setLockDrawer(false)
        setToolbarTitle(getString(R.string.menu_about))
        textAppVersion.text = String.format(getString(R.string.about_app_version), BuildConfig.VERSION_NAME)
    }
}
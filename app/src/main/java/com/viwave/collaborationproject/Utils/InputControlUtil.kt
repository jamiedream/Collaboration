/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference

object InputControlUtil {

    fun hideKeyboard(weakReference: WeakReference<FragmentActivity?>){
        val imm = weakReference.get()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(weakReference.get()?.currentFocus?.windowToken, 0)
    }

    fun showKeyboard(weakReference: WeakReference<FragmentActivity?>){
        val imm = weakReference.get()?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0)
    }
}
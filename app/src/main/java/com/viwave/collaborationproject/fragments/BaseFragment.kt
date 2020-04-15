/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/31/20 12:08 PM
 */

package com.viwave.collaborationproject.fragments

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.viwave.collaborationproject.MainActivity
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.utils.InputControlUtil
import java.lang.ref.WeakReference

abstract class BaseFragment: Fragment() {

    fun setToolbarTitle(title: String){
        (activity as MainActivity).setToolbarTitle(title)
    }

    fun setToolbarLeftIcon(isDrawShow: Boolean, icon: Drawable? = context?.getDrawable(R.drawable.ic_arrow_back)){
        (activity as MainActivity).setToolbarLeftIcon(isDrawShow, icon)
    }

    /**
     * @param nowFragment fragment which showed on screen
     * @param fragmentManager fragmentManager from fragment which show in the screen
     * @param addedFragment fragment which will be add to stack
     * @param importLayout now fragment layout id
     * @param tag fragment's tag in stack
     * @param key key in intent fragment bundle
     * @param value value in intent fragment bundle
     * */
    fun replaceFragment(nowFragment: Fragment, replaceFragment: Fragment, tag: String){
        nowFragment.activity?.supportFragmentManager?.
            beginTransaction()?.
            replace(R.id.host_fragment, replaceFragment, tag)?.
            addToBackStack("replace $tag")?.
            commit()
    }

    fun replacePartialFragment(nowFragment: Fragment, replaceFragment: Fragment, replaceLayout: Int, tag: String){
        nowFragment.fragmentManager?.
            beginTransaction()?.
            replace(replaceLayout, replaceFragment, tag)?.
            commit()
    }

    protected var isShowToolBar = true

    override fun onResume() {
        super.onResume()
        if(!isShowToolBar){
            (activity as MainActivity).setToolbarVis(isShowToolBar)
            (activity as AppCompatActivity).supportActionBar?.hide()
        }

    }

    override fun onStop() {
        if(!isShowToolBar) {
            (activity as AppCompatActivity).supportActionBar?.show()
            (activity as MainActivity).setToolbarVis(true)
        }
        InputControlUtil.hideKeyboard(WeakReference((activity)))
        super.onStop()
    }

}
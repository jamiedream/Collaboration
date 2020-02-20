package com.viwave.collaborationproject.fragments

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import com.viwave.collaborationproject.MainActivity
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.utils.InputControlUtil
import java.lang.ref.WeakReference

abstract class BaseFragment: Fragment() {

    val BUNDLE_TOOLBAR_TITLE = "PRE_TOOLBAR_TITLE"

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
    fun addFragment(nowFragment: Fragment, addedFragment: Fragment, tag: String) {
//        LogUtil.logD(TAG, nowFragment.fragmentManager?.findFragmentByTag(tag)?.isAdded)
        when(nowFragment.fragmentManager?.findFragmentByTag(tag)?.isAdded){
            null -> {
                nowFragment.fragmentManager?.beginTransaction()?.add(R.id.host_fragment, addedFragment, tag)
                    ?.addToBackStack("add $tag")?.commit()
            }
        }
    }

    fun replaceFragment(nowFragment: Fragment, replaceFragment: Fragment, tag: String){
        nowFragment.activity?.supportFragmentManager?.
            beginTransaction()?.
            replace(R.id.host_fragment, replaceFragment, tag)?.
            addToBackStack("replace $tag")?.
            commit()
    }

    override fun onStop() {
        InputControlUtil.hideKeyboard(WeakReference((activity)))
        super.onStop()
    }

}
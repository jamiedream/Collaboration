package com.viwave.collaborationproject.fragments.adapter
/*
 * Copyright (c) viWave 2019.
 * Create by J.Y Yen 16/ 7/ 2019.
 * Last modified 7/15/19 4:22 PM
 */

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.fragments.ITogglePressedListener


class GridViewAdapter(dataArray: SparseArray<String>, private val listener: ITogglePressedListener): RecyclerView.Adapter<GridViewAdapter.ViewHolder>() {

    private val TAG = this.javaClass.simpleName
    private var data = dataArray
    private var toggleList = ArrayList<ToggleButton>()

    companion object{
        const val TAB_START = 0
        const val TAB_END = 2
        const val TAB_CENTER = 1
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val holder =
            when(viewType){
                TAB_START ->
                    ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.view_tab_start, viewGroup, false))
                TAB_END ->
                    ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.view_tab_end, viewGroup, false))
                else ->
                    ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.view_tab_center, viewGroup, false))
            }
        toggleList.add(holder.toggleButton)
        return holder
    }

    override fun getItemCount(): Int {
        return data.size()
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            TAB_START
        }else if(position == data.size() - 1){
            TAB_END
        }else TAB_CENTER
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.toggleButton.text = data[position].toString()
        if(position == 2) setTogglePressStatus(holder.toggleButton) //init
        holder.toggleButton.setOnTouchListener(setToggleTouchListener(holder.toggleButton))
    }

    private fun setToggleTouchListener(toggleButton: ToggleButton): View.OnTouchListener{
        return View.OnTouchListener { v, event ->
            when(event?.action){
                MotionEvent.ACTION_DOWN -> {
                    setTogglePressStatus(toggleButton)
                }
            }
            return@OnTouchListener true
        }
    }

    private fun setTogglePressStatus(toggleButton: ToggleButton){

        for(listNum in 0 until toggleList.size){
            when(toggleButton == toggleList[listNum]){
                true -> {
//                    LogUtil.logD("TogglePressStatus", "isTogglePressed")
//                    isTogglePressed = true
                    listener.pressedToggle(toggleList[listNum].text.toString())
                    toggleList[listNum].isPressed = true
                }
                false -> {
                    toggleList[listNum].isPressed = false
                }
            }
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var toggleButton = itemView.findViewById<ToggleButton>(R.id.toggle)
    }

}
/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 9/ 4/ 2020.
 * Last modified 4/9/20 3:23 PM
 */

package com.viwave.collaborationproject.fragments.subsys.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.Bio
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.utils.DataFormatUtil
import com.viwave.collaborationproject.utils.DateUtil

class HistoryListTypeAdapter(private val dataList: MutableList<out Bio>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = this::class.java.simpleName
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        bioViewModel.getSelectedType().value?.let {
            when(it){
                BioLiveData.Companion.BioType.Oxygen, BioLiveData.Companion.BioType.BloodGlucose ->
                    return HistoryListTypeTwoViewHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.layout_history_list_content1,
                            parent,
                            false))

                else ->
                    return HistoryListTypeOneViewHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.layout_history_list_content,
                            parent,
                            false))
            }
        }
        return HistoryListTypeOneViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_history_list_content,
                parent,
                false))
    }

    override fun getItemCount(): Int {
        return dataList?.size?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        bioViewModel.getSelectedType().value?.let {
            when(it){
                BioLiveData.Companion.BioType.Temperature -> {
                    holder as HistoryListTypeOneViewHolder
                    val data = dataList!![position] as Bio.Temperature
                    holder.measureTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                    holder.value.text = DataFormatUtil.formatString(data.temperature)
                }
                BioLiveData.Companion.BioType.Respire -> {
                    holder as HistoryListTypeOneViewHolder
                    val data = dataList!![position] as Bio.Respire
                    holder.measureTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                    holder.value.text = data.respire.toString()
                }
                BioLiveData.Companion.BioType.Pulse -> {
                    holder as HistoryListTypeOneViewHolder
                    val data = dataList!![position] as Bio.Pulse
                    holder.measureTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                    holder.value.text = data.pulse.toString()
                }
                BioLiveData.Companion.BioType.BloodPressure -> {
                    holder as HistoryListTypeOneViewHolder
                    val data = dataList!![position] as Bio.BloodPressure
                    holder.measureTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                    holder.value.text = String.format("%s/%s", data.sys, data.dia)
                }
                BioLiveData.Companion.BioType.Weight -> {
                    holder as HistoryListTypeOneViewHolder
                    val data = dataList!![position] as Bio.Weight
                    holder.measureTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                    holder.value.text = DataFormatUtil.formatString(data.weight)
                }
                BioLiveData.Companion.BioType.Height -> {
                    holder as HistoryListTypeOneViewHolder
                    val data = dataList!![position] as Bio.Height
                    holder.measureTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                    holder.value.text = DataFormatUtil.formatString(data.height)
                }
                BioLiveData.Companion.BioType.Oxygen -> {
                    holder as HistoryListTypeTwoViewHolder
                    val data = dataList!![position] as Bio.Oxygen
                    holder.measureTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                    holder.valueOne.text = data.spo2Highest.toString()
                    holder.valueTwo.text =
                        if(data.pulseHighest == null)
                            holder.itemView.context.getString(R.string.two_dash)
                        else
                            data.pulseHighest.toString()
                }
                BioLiveData.Companion.BioType.BloodGlucose -> {
                    holder as HistoryListTypeTwoViewHolder
                    val data = dataList!![position] as Bio.BloodGlucose
                    holder.measureTime.text = DateUtil.getMeasurementTime(data.takenAt * 1000L)
                    holder.valueOne.text = data.glucose.toString()
                    holder.valueTwo.text = data.meal
                }
            }
        }
    }
}
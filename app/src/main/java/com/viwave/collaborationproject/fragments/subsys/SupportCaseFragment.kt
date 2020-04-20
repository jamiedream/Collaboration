/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/25/20 1:49 PM
 */

package com.viwave.collaborationproject.fragments.subsys

import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.lifecycle.Observer
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.CollaborationApplication
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.DB.remote.CaseDatabase
import com.viwave.collaborationproject.DB.remote.DataCountAction.initDataCount
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.MainActivity.Companion.generalViewModel
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.general.SubSys
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.FEMALE
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.MALE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SupportCaseFragment: BaseFragment(), BackPressedDelegate {

    override fun onBackPressed(): Boolean {
        fragmentManager?.popBackStack()
        return true
    }

    private val TAG = this::class.java.simpleName
    private val editCaseNo by lazy { view!!.findViewById<EditText>(R.id.edit_case_no) }
    private val editCaseName by lazy { view!!.findViewById<EditText>(R.id.edit_case_name) }
    private val radioBtnFemale by lazy { view!!.findViewById<AppCompatRadioButton>(R.id.radio_female_case_gender) }

    private var subSys: SubSys? = null
    private val subSysObserver =
        Observer<SubSys?>{
            subSys = it
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_support_case, container, false)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(getString(R.string.support_case_title))
        setToolbarLeftIcon(false)
        generalViewModel.getSelectedSubSys().observe(this, subSysObserver)
    }

    override fun onStop() {
        generalViewModel.getSelectedSubSys().removeObserver(subSysObserver)
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_case_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.support_case_add -> {
                return if(addSupportCase()){
                    fragmentManager?.popBackStack()
                    true
                }else false

            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addSupportCase(): Boolean{
        return when{
            editCaseName.text.isNullOrEmpty() || editCaseNo.text.isNullOrEmpty() -> {
                Toast.makeText(context, "Please enter the name/number.", Toast.LENGTH_LONG).show()
                false
            }
            else -> {
                val caseName = editCaseName.text.toString()
                val caseNumber = editCaseNo.text.toString()
                val gender = if(radioBtnFemale.isChecked) FEMALE else MALE
                GlobalScope.launch(Dispatchers.IO) {
                    when (subSys?.sysCode) {
                        SysKey.DAILY_NURSING_CODE -> {
                            CaseDatabase(CollaborationApplication.context).getCaseNursingDao()
                                .insert(
                                    CaseEntity.CaseNursingEntity(
                                        caseNumber,
                                        caseName,
                                        gender,
                                        null,
                                        null,
                                        true,
                                        initDataCount
                                    )
                                )
                        }
                        SysKey.DAILY_HOME_CARE_CODE -> {
                            CaseDatabase(CollaborationApplication.context).getCaseHomeCareDao()
                                .insert(
                                    CaseEntity.CaseHomeCareEntity(
                                        caseNumber,
                                        caseName,
                                        gender,
                                        null,
                                        null,
                                        true,
                                        initDataCount
                                    )
                                )
                        }
                    }
                }
                true
            }
        }

    }
}
package com.viwave.collaborationproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.DB.cache.UserPreference
import com.viwave.collaborationproject.MainActivity.Companion.generalViewModel
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.general.SubSys
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment

class SysListFragment: BaseFragment() {

    private val TAG = this::class.java.simpleName

    private val textHiName by lazy { view!!.findViewById<TextView>(R.id.hi_name) }
    private val textDailyCare by lazy { view!!.findViewById<TextView>(R.id.item_daily_care) }
    private val textStation by lazy { view!!.findViewById<TextView>(R.id.item_station) }
    private val textDailyNursing by lazy { view!!.findViewById<TextView>(R.id.item_daily_nursing) }
    private val textHomeService by lazy { view!!.findViewById<TextView>(R.id.item_home_service) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sys_list, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowToolBar = false
    }

    override fun onResume() {
        super.onResume()
        setToolbarLeftIcon(false)

        UserPreference.instance.queryUser().run {
            generalViewModel.getLoginUser().value = this
            val sysList = this.sysList
            sysList.forEach {
                when(it){
                    SysKey.DailyCare -> enableSys(textDailyCare, SysKey.DailyCare)
                    SysKey.DailyNursing -> enableSys(textDailyNursing, SysKey.DailyNursing)
                    SysKey.Station -> enableSys(textStation, SysKey.Station)
                    SysKey.HomeCare -> enableSys(textHomeService, SysKey.HomeCare)
                }
            }
            textHiName.text = String.format(getString(R.string.login_hi), this.name)
        }

    }

    private fun enableSys(text: TextView, subSys: SubSys){
        text.apply {
            this.isEnabled = true
            this.setOnClickListener {
                UserPreference.instance.editSubSys(subSys)
                generalViewModel.getSelectedSubSys().value = subSys
                replaceFragment(this@SysListFragment, CaseListFragment(), getString(R.string.tag_case_list))
            }
        }
    }

}
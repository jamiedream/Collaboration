package com.viwave.collaborationproject.fragments.subsys.history

import android.os.Bundle
import android.view.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.Observer
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioLiveData
import com.viwave.collaborationproject.fragments.BaseFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.bioViewModel
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment.Companion.caseViewModel

class HistoryFragment: BaseFragment(){

    private val TAG = this::class.java.simpleName

    private val textDiagramType by lazy { view!!.findViewById<TextView>(R.id.diagram_type) }
    private val imgDiagramMenu by lazy { view!!.findViewById<ImageView>(R.id.diagram_menu) }
    private val imgDiagramChartList by lazy { view!!.findViewById<ImageView>(R.id.diagram_chart_list) }
    private val contentLayout by lazy { view!!.findViewById<FrameLayout>(R.id.content_history) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        setHasOptionsMenu(true)
        return view
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.dashboard_no)
        caseViewModel.getSelectedCase().value?.let {
            item.setTitle(it.getCaseNumber)
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_dashboard_no, menu)
    }

    private val selectedCaseObserver =
        Observer<CaseEntity>{
            setToolbarTitle(it.getCaseName)
            setToolbarLeftIcon(false)
        }

    private val selectedTypeObserver =
        Observer<BioLiveData.Companion.BioType>{
            textDiagramType.text = getString(bioViewModel.getSelectedTypeHistoryTitle().value?: R.string.temperature)
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //switch popup menu
        imgDiagramMenu.setOnClickListener {

            PopupMenu(view.context, it).apply {
                this.menuInflater.inflate(R.menu.menu_chart, menu)
                this.show()
                this.setOnMenuItemClickListener { item ->
                    bioViewModel.getSelectedType().value =
                        when (item.title) {
                            getString(R.string.temperature) -> BioLiveData.Companion.BioType.Temperature
                            getString(R.string.pulse) -> BioLiveData.Companion.BioType.Pulse
                            getString(R.string.respire) -> BioLiveData.Companion.BioType.Respire
                            getString(R.string.blood_pressure) -> BioLiveData.Companion.BioType.BloodPressure
                            getString(R.string.blood_glucose) -> BioLiveData.Companion.BioType.BloodGlucose
                            getString(R.string.height) -> BioLiveData.Companion.BioType.Height
                            getString(R.string.weight) -> BioLiveData.Companion.BioType.Weight
                            getString(R.string.oxygen) -> BioLiveData.Companion.BioType.Oxygen
                            else -> BioLiveData.Companion.BioType.Temperature
                        }
                    true
                }

            }
        }

        caseViewModel.getSelectedCase().observe(this, selectedCaseObserver)
        bioViewModel.getSelectedType().observe(this, selectedTypeObserver)

        //init
        replacePartialFragment(this,
            HistoryListFragment(), R.id.content_history, getString(R.string.tag_list))

        //switch chart and list
        imgDiagramChartList.setOnClickListener {
            when(imgDiagramChartList.tag){
                getString(R.string.tag_list) -> {
                    imgDiagramChartList.tag = getString(R.string.tag_chart)
                    imgDiagramChartList.isPressed = true
                    imgDiagramChartList.setImageResource(R.drawable.ic_baseline_menu)
                    replacePartialFragment(this,
                        HistoryChartFragment(), R.id.content_history, getString(R.string.tag_chart))
                }
                getString(R.string.tag_chart) -> {
                    imgDiagramChartList.tag = getString(R.string.tag_list)
                    imgDiagramChartList.isPressed = false
                    imgDiagramChartList.setImageResource(R.drawable.ic_health_chart)
                    replacePartialFragment(this,
                        HistoryListFragment(), R.id.content_history, getString(R.string.tag_list))
                }
            }
        }

    }

    override fun onStop() {
        caseViewModel.getSelectedCase().removeObserver(selectedCaseObserver)
        bioViewModel.getSelectedType().removeObserver(selectedTypeObserver)
        super.onStop()
    }


}
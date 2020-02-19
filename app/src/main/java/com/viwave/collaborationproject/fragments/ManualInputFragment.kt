package com.viwave.collaborationproject.fragments

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.viwave.collaborationproject.BackPressedDelegate
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.bios.BioType
import com.viwave.collaborationproject.utils.InputFormatUtil
import com.viwave.collaborationproject.utils.LogUtil
import kotlinx.android.synthetic.main.layout_dashboard_temp.*

class ManualInputFragment(private val bioType: String): BaseFragment(), BackPressedDelegate {

    override fun onBackPressed(): Boolean {
        fragmentManager?.popBackStack()
        return true
    }

    private val TAG = this::class.java

    private val textValue by lazy { view!!.findViewById<EditText>(R.id.value_measurement) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        when(bioType){
            BioType.BloodGlucose -> {}
            BioType.BloodPressure -> {}
            BioType.Height -> {}
            BioType.Oxygen -> {}
            BioType.Pulse -> {}
            BioType.Respire -> {}
            BioType.Temperature -> {}
            BioType.Weight -> {}

        }

        val view = inflater.inflate(R.layout.layout_manual_temp, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_manual_add, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.manual_add -> {
                LogUtil.logD("ManualInputFragment", "新增資料")
                fragmentManager?.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
//        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        setToolbarLeftIcon(false)
        setToolbarTitle(getString(R.string.manual_input))

        textValue.filters = arrayOf(InputFormatUtil(3, 1))

    }

}
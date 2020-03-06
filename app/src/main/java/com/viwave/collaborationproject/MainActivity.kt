package com.viwave.collaborationproject

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.DB.cache.UserKey
import com.viwave.collaborationproject.DB.cache.UserPreference
import com.viwave.collaborationproject.data.general.GeneralViewModel
import com.viwave.collaborationproject.data.general.SubSys
import com.viwave.collaborationproject.fragments.AboutFragment
import com.viwave.collaborationproject.fragments.DeviceFragment
import com.viwave.collaborationproject.fragments.LoginFragment
import com.viwave.collaborationproject.fragments.PendingDataFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment
import com.viwave.collaborationproject.utils.LogUtil

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.layout_drawer) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val navDrawer by lazy { findViewById<NavigationView>(R.id.nav_view) }
    private val navigationListener =
        NavigationView.OnNavigationItemSelectedListener { menuItem ->
            when(menuItem.title){
                getString(R.string.menu_case_list) -> switchFragmentToTop(CaseListFragment())
                getString(R.string.menu_unupload_data) -> switchFragmentToTop(PendingDataFragment())
                getString(R.string.menu_measurement_device) -> switchFragmentToTop(DeviceFragment())
                getString(R.string.menu_about) -> switchFragmentToTop(AboutFragment())
            }
            drawerLayout.closeDrawer(GravityCompat.START, true)
            true
        }
    private val navDrawerName by lazy { navDrawer.getHeaderView(0).findViewById<TextView>(R.id.txt_login_name) }
    private val navDrawerSys by lazy { navDrawer.getHeaderView(0).findViewById<TextView>(R.id.list_subsys) }
    private val navDrawerLogout by lazy { navDrawer.findViewById<Button>(R.id.btn_logout) }

    companion object{
        lateinit var generalViewModel: GeneralViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generalViewModel = ViewModelProviders.of(this).get(GeneralViewModel::class.java)
        lockDrawer(true)
        reloadDrawer()
        navDrawer.setNavigationItemSelectedListener(navigationListener)
        toolbar.setNavigationIcon(R.drawable.btn_arrow)
        when(UserPreference.instance.query(UserKey.IS_LOGIN, false)){
            true -> switchFragmentToTop(CaseListFragment())
            false -> switchFragmentToTop(LoginFragment())
        }

    }

    override fun onResume() {
        super.onResume()

        generalViewModel.getLoginUserName().observe(this, Observer<String?>{
            navDrawerName.text = it
        })

        generalViewModel.getSelectedSubSys().observe(this, Observer<SubSys?>{
            UserPreference.instance.editSubSys(it)
            navDrawerSys.text =
                when(it?.sysName){
                    SysKey.DAILY_CARE_NAME -> getString(R.string.sys_daily_care)
                    SysKey.DAILY_NURSING_NAME -> getString(R.string.sys_daily_nursing)
                    SysKey.DAILY_STATION_NAME -> getString(R.string.sys_station)
                    SysKey.DAILY_HOME_CARE_NAME -> getString(R.string.sys_home_service)
                    else -> getString(R.string.sys_daily_care)
                }
        })


        navDrawerSys.setOnClickListener { v ->
            navDrawerSys.isEnabled = false
            navDrawerSys.refreshDrawableState()

            PopupMenu(this@MainActivity, v).apply {
                this.menuInflater.inflate(R.menu.menu_sys, this.menu)
                this.show()
                this.setOnMenuItemClickListener { item ->
                    generalViewModel.getSelectedSubSys().value =
                        when(item?.title){
                            getString(R.string.sys_daily_care) -> SysKey.DailyCare
                            getString(R.string.sys_daily_nursing) -> SysKey.DailyNursing
                            getString(R.string.sys_station) -> SysKey.Station
                            getString(R.string.sys_home_service) -> SysKey.HomeCare
                            else -> SysKey.DailyCare
                        }
                    true
                }

                this.setOnDismissListener {
                    navDrawerSys.isEnabled = true
                    navDrawerSys.refreshDrawableState()
                }
            }
        }

        navDrawerLogout.setOnClickListener {
            //dialog: deal with data upload
//            UserPreference.instance.clear()
//            switchFragmentToTop(LoginFragment())
            LogUtil.logD(TAG, "logout")
        }
    }

    private fun switchFragmentToTop(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.host_fragment, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun onBackPressed() {

        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.host_fragment)

        //todo, test
        LogUtil.logD(TAG, supportFragmentManager.backStackEntryCount)
        if(supportFragmentManager.backStackEntryCount == 0){
            //dialog: sure to close app?
            LogUtil.logD(TAG, "Last stack")
            return
        }

        when ((fragment as? BackPressedDelegate)?.onBackPressed()) {
            true -> return
        }
        super.onBackPressed()
    }

    /**
     * Drawer
     * */
    private fun lockDrawer(isLock: Boolean){
        if(isLock){
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        }else{
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        }
    }

    private fun reloadDrawer(){
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.tag_open_drawer, R.string.tag_close_drawer
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        drawerLayout.closeDrawer(GravityCompat.START, true)
    }

    /**
     * Toolbar
     * */
    fun setToolbarTitle(title: String){
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

    fun setToolbarLeftIcon(isDrawer: Boolean, icon: Drawable? = getDrawable(R.drawable.ic_arrow)) {
        when (isDrawer) {
            true -> {
                lockDrawer(false)
                reloadDrawer()
                navDrawer.setNavigationItemSelectedListener(navigationListener)
            }
            false -> {
                lockDrawer(true)
                reloadDrawer()
                navDrawer.setNavigationItemSelectedListener(navigationListener)

                toolbar.navigationIcon = icon
                toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }
            }
        }
    }

    fun setToolbarVis(isShowToolbar: Boolean){
        when(isShowToolbar){
            true -> toolbar.visibility = View.VISIBLE
            false -> toolbar.visibility = View.GONE
        }
    }
}

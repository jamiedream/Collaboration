package com.viwave.collaborationproject

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.navigation.NavigationView
import com.viwave.collaborationproject.DB.cache.UserKey
import com.viwave.collaborationproject.DB.cache.UserPreference
import com.viwave.collaborationproject.data.general.GeneralViewModel
import com.viwave.collaborationproject.fragments.LoginFragment
import com.viwave.collaborationproject.fragments.subsys.caseList.CaseListFragment

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName
    private val drawerLayout by lazy { findViewById<DrawerLayout>(R.id.layout_drawer) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val navDrawer by lazy { findViewById<NavigationView>(R.id.nav_view) }
    private val navigationListener =
        object: NavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.string.menu_case_list -> {}
                    R.string.menu_unupload_data -> {}
                    R.string.menu_measurement_device -> {}
                    R.string.menu_about -> {}
                }
                return true
            }
        }

    companion object{
        lateinit var generalViewModel: GeneralViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generalViewModel = ViewModelProviders.of(this).get(GeneralViewModel::class.java)
        navDrawer.setNavigationItemSelectedListener(navigationListener)
        toolbar.setNavigationIcon(R.drawable.btn_arrow)
        when(UserPreference.instance.query(UserKey.IS_LOGIN, false)){
            true -> switchFragmentToTop(supportFragmentManager, CaseListFragment())
            false -> switchFragmentToTop(supportFragmentManager, LoginFragment())
        }

    }

    override fun onResume() {
        super.onResume()

    }


    private fun switchFragmentToTop(fm: FragmentManager, fragment: Fragment) {
        val transaction = fm.beginTransaction()
        for (i in 0 until fm.backStackEntryCount) {
            fm.popBackStack()
        }
        transaction.replace(R.id.host_fragment, fragment, fragment.javaClass.simpleName)
        transaction.commitAllowingStateLoss()
    }

    override fun onBackPressed() {

        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.host_fragment)

        when ((fragment as? BackPressedDelegate)?.onBackPressed()) {
            true -> return
        }
        super.onBackPressed()
    }

    /**
     * Drawer
     * */
    fun lockDrawer(isLock: Boolean){
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
                reloadDrawer()
            }
            false -> {
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

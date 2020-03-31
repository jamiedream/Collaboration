/*
 * Copyright (c) viWave 2020.
 * Create by J.Y Yen 31/ 3/ 2020.
 * Last modified 3/31/20 2:26 PM
 */

package com.viwave.collaborationproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializer
import com.google.gson.reflect.TypeToken
import com.viwave.collaborationproject.DB.cache.SysKey
import com.viwave.collaborationproject.DB.cache.UserKey
import com.viwave.collaborationproject.DB.cache.UserPreference
import com.viwave.collaborationproject.DB.remote.CaseDatabase
import com.viwave.collaborationproject.DB.remote.DataCountAction.initDataCount
import com.viwave.collaborationproject.DB.remote.entity.CaseEntity
import com.viwave.collaborationproject.FakeData.QueryData
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.DataSort
import com.viwave.collaborationproject.data.UploadData
import com.viwave.collaborationproject.data.general.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment: BaseFragment() {

    private val TAG = this::class.java.simpleName
    private val btnLogin by lazy { view!!.findViewById<Button>(R.id.btn_login) }
    private val textError by lazy {view!!.findViewById<TextView>(R.id.login_error_msg)}
    private val editAccount by lazy { view!!.findViewById<EditText>(R.id.login_account) }
    private val editPassword by lazy { view!!.findViewById<EditText>(R.id.login_password) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowToolBar = false
    }

    override fun onResume() {
        super.onResume()
        setToolbarLeftIcon(false)

        editAccount.setOnEditorActionListener { _, _, _ ->
            editPassword.requestFocus()
            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin.setOnClickListener {
            //api login, case list, history data
            when{
                editAccount.text.isNullOrEmpty() && editPassword.text.isNullOrEmpty() ->
                    Toast.makeText(context, "Account and password are empty.", Toast.LENGTH_LONG).show()
                editAccount.text.isNullOrEmpty() && !editPassword.text.isNullOrEmpty() ->
                    Toast.makeText(context, "Account is empty.", Toast.LENGTH_LONG).show()
                !editAccount.text.isNullOrEmpty() && editPassword.text.isNullOrEmpty() ->
                    Toast.makeText(context, "Password is empty.", Toast.LENGTH_LONG).show()
                else ->{
                    //account: test, pwd: abc123
                    val loginObject = UploadData.uploadLoginInfo(editAccount.text.toString(), editPassword.text.toString())
                    when{
                        editAccount.text.toString() == "test" && editPassword.text.toString() == "abc123" -> {
                            val gson = GsonBuilder().registerTypeAdapter(User::class.java, DataSort.staffInfo).create()
                            //login success
                            UserPreference.instance.editUser(gson.fromJson(QueryData().loginReturn, User::class.java))
                            GlobalScope.launch(Dispatchers.IO){
                                QueryData().caseList.forEach {
                                    CaseDatabase(context!!).getCaseCareDao().insert(
                                        CaseEntity.CaseCareEntity(
                                            it.caseNumber,
                                            it.caseName,
                                            it.caseGender,
                                            it.SCDTID,
                                            it.startTime,
                                            false,
                                            initDataCount
                                        )
                                    )
                                }
                            }

//                            LogUtil.logD(TAG, gson.fromJson(QueryData().loginReturn2, User::class.java))
//                            LogUtil.logD(TAG, UserPreference.instance.queryUser())
                            UserPreference.instance.edit(UserKey.IS_LOGIN, true)
                            replaceFragment(this@LoginFragment, SysListFragment(), getString(R.string.tag_sys_list))
                        }
                        editAccount.text.toString() == "test2" && editPassword.text.toString() == "abc123" -> {
                            val gson = GsonBuilder().registerTypeAdapter(User::class.java, DataSort.staffInfo).create()
                            //login success
                            UserPreference.instance.editUser(gson.fromJson(QueryData().loginReturn2, User::class.java))
                            val sys = QueryData().loginReturn2.getAsJsonArray("system").toMutableList()
                            GlobalScope.launch(Dispatchers.IO) {
                                sys.forEach {
                                    val ob = it.asJsonObject
                                    when (ob.get("sysCode").asString) {
                                        SysKey.DAILY_CARE_CODE -> {
                                            QueryData().caseList.forEach {
                                                CaseDatabase(context!!).getCaseCareDao().insert(
                                                    CaseEntity.CaseCareEntity(
                                                        it.caseNumber,
                                                        it.caseName,
                                                        it.caseGender,
                                                        it.SCDTID,
                                                        it.startTime,
                                                        false,
                                                        initDataCount
                                                    )
                                                )
                                            }
                                        }
                                        SysKey.DAILY_NURSING_CODE -> {
                                            QueryData().caseList2.forEach {
                                                CaseDatabase(context!!).getCaseNursingDao().insert(
                                                    CaseEntity.CaseNursingEntity(
                                                        it.caseNumber,
                                                        it.caseName,
                                                        it.caseGender,
                                                        it.SCDTID,
                                                        it.startTime,
                                                        false,
                                                        initDataCount
                                                    )
                                                )
                                            }
                                            //todo, load history data
//                                            LogUtil.logD(TAG, CaseDatabase(context!!).getCaseNursingDao().getDataCountStr("3333333"))
                                        }
                                        SysKey.DAILY_STATION_CODE -> {
                                            QueryData().caseList.forEach {
                                                CaseDatabase(context!!).getCaseStationDao().insert(
                                                    CaseEntity.CaseStationEntity(
                                                        it.caseNumber,
                                                        it.caseName,
                                                        it.caseGender,
                                                        it.SCDTID,
                                                        it.startTime,
                                                        false,
                                                        initDataCount
                                                    )
                                                )
                                            }
                                        }
                                        SysKey.DAILY_HOME_CARE_CODE -> {
                                            QueryData().caseList2.forEach {
                                                CaseDatabase(context!!).getCaseHomeCareDao().insert(
                                                    CaseEntity.CaseHomeCareEntity(
                                                        it.caseNumber,
                                                        it.caseName,
                                                        it.caseGender,
                                                        it.SCDTID,
                                                        it.startTime,
                                                        false,
                                                        initDataCount
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    }
                            }



                            UserPreference.instance.edit(UserKey.IS_LOGIN, true)
                            replaceFragment(this@LoginFragment, SysListFragment(), getString(R.string.tag_sys_list))
                        }
                        else -> Toast.makeText(context, "Login failed.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun <T> sortBioData(deserializer: JsonDeserializer<MutableList<T>>, data: JsonArray): MutableList<T>{
        return GsonBuilder()
            .registerTypeAdapter(object: TypeToken<MutableList<T>>(){}.type, deserializer)
            .create()
            .fromJson(data, object: TypeToken<MutableList<T>>(){}.type)
    }

}
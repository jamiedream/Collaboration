package com.viwave.collaborationproject.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.viwave.collaborationproject.DB.cache.UserKey
import com.viwave.collaborationproject.DB.cache.UserPreference
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.UploadData
import com.viwave.collaborationproject.http.Apis
import com.viwave.collaborationproject.http.IAPIResult

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
                    //account: p00012, pwd: st13579
                    val loginObject = UploadData.uploadLoginInfo(editAccount.text.toString(), editPassword.text.toString())
                    Apis.login(loginObject,
                        object: IAPIResult{
                            override fun onSuccess() {
                                UserPreference.instance.edit(UserKey.IS_LOGIN, true)
                                replaceFragment(this@LoginFragment, SysListFragment(), getString(R.string.tag_sys_list))
                            }

                            override fun onFailed(msg: String?) {
                                textError.text = msg
                            }
                        })

//                    HttpManager.client.
//                        create(IHttp::class.java).
//                        login(loginObject).
//                        enqueue(object : Callback<Login> {
//                            override fun onFailure(call: Call<Login>, t: Throwable) {
//                                textError.text = t.message
//                            }
//
//                            override fun onResponse(call: Call<Login>, response: Response<Login>) {
//                                val res = response.body()
//                                val resCode = res?.res?.toInt()
//                                val msg = res?.msg
//                                LogUtil.logD(TAG, "$resCode $msg")
//                                if(response.isSuccessful){
//                                    when(resCode){
//                                        1 -> {
//                                            //login success
//                                            UserPreference.instance.editUser(DataSort.staff(res))
////                                            LogUtil.logD(TAG, "$resCode $msg")
//
//                                            UserPreference.instance.edit(UserKey.IS_LOGIN, true)
//                                            replaceFragment(this@LoginFragment, SysListFragment(), getString(R.string.tag_sys_list))
//                                        }
//                                        else -> textError.text = msg
//                                    }
//                                }else textError.text = msg
//                            }
//                        })

//                    when{
//                        editAccount.text.toString() == "test" && editPassword.text.toString() == "abc123" -> {
//                            val gson = GsonBuilder().registerTypeAdapter(User::class.java, DataSort.staffInfo).create()
//                            //login success
//                            UserPreference.instance.editUser(gson.fromJson(QueryData().loginReturn, User::class.java))
//                            GlobalScope.launch(Dispatchers.IO){
//                                QueryData().caseList.forEach {
//                                    CaseDatabase(context!!).getCaseCareDao().insert(
//                                        CaseEntity.CaseCareEntity(
//                                            it.caseNumber,
//                                            it.caseName,
//                                            it.caseGender,
//                                            it.SCDTID,
//                                            it.startTime,
//                                            false,
//                                            initDataCount
//                                        )
//                                    )
//                                }
//                            }
//
////                            LogUtil.logD(TAG, gson.fromJson(QueryData().loginReturn2, User::class.java))
////                            LogUtil.logD(TAG, UserPreference.instance.queryUser())
//                            UserPreference.instance.edit(UserKey.IS_LOGIN, true)
//                            replaceFragment(this@LoginFragment, SysListFragment(), getString(R.string.tag_sys_list))
//                        }
//                        editAccount.text.toString() == "test2" && editPassword.text.toString() == "abc123" -> {
//                            val gson = GsonBuilder().registerTypeAdapter(User::class.java, DataSort.staffInfo).create()
//                            //login success
//                            UserPreference.instance.editUser(gson.fromJson(QueryData().loginReturn2, User::class.java))
//                            val sys = QueryData().loginReturn2.getAsJsonArray("system").toMutableList()
//                            GlobalScope.launch(Dispatchers.IO) {
//                                sys.forEach {
//                                    val ob = it.asJsonObject
//                                    when (ob.get("sysCode").asString) {
//                                        SysKey.DAILY_CARE_CODE -> {
//                                            QueryData().caseList.forEach {
//                                                CaseDatabase(context!!).getCaseCareDao().insert(
//                                                    CaseEntity.CaseCareEntity(
//                                                        it.caseNumber,
//                                                        it.caseName,
//                                                        it.caseGender,
//                                                        it.SCDTID,
//                                                        it.startTime,
//                                                        false,
//                                                        initDataCount
//                                                    )
//                                                )
//                                            }
//                                        }
//                                        SysKey.DAILY_NURSING_CODE -> {
//                                            QueryData().caseList2.forEach {
//                                                CaseDatabase(context!!).getCaseNursingDao().insert(
//                                                    CaseEntity.CaseNursingEntity(
//                                                        it.caseNumber,
//                                                        it.caseName,
//                                                        it.caseGender,
//                                                        it.SCDTID,
//                                                        it.startTime,
//                                                        false,
//                                                        initDataCount
//                                                    )
//                                                )
//                                            }
//                                            //todo, load history data
//                                        }
//                                        SysKey.DAILY_STATION_CODE -> {
//                                            QueryData().caseList.forEach {
//                                                CaseDatabase(context!!).getCaseStationDao().insert(
//                                                    CaseEntity.CaseStationEntity(
//                                                        it.caseNumber,
//                                                        it.caseName,
//                                                        it.caseGender,
//                                                        it.SCDTID,
//                                                        it.startTime,
//                                                        false,
//                                                        initDataCount
//                                                    )
//                                                )
//                                            }
//                                        }
//                                        SysKey.DAILY_HOME_CARE_CODE -> {
//                                            QueryData().caseList2.forEach {
//                                                CaseDatabase(context!!).getCaseHomeCareDao().insert(
//                                                    CaseEntity.CaseHomeCareEntity(
//                                                        it.caseNumber,
//                                                        it.caseName,
//                                                        it.caseGender,
//                                                        it.SCDTID,
//                                                        it.startTime,
//                                                        false,
//                                                        initDataCount
//                                                    )
//                                                )
//                                            }
//                                        }
//                                    }
//                                    }
//                            }
//
//
//
//                            UserPreference.instance.edit(UserKey.IS_LOGIN, true)
//                            replaceFragment(this@LoginFragment, SysListFragment(), getString(R.string.tag_sys_list))
//                        }
//                        else -> Toast.makeText(context, "Login failed.", Toast.LENGTH_LONG).show()
//                    }
                }
            }
        }
    }


}
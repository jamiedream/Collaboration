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
import com.viwave.collaborationproject.DB.cache.UserKey
import com.viwave.collaborationproject.DB.cache.UserPreference
import com.viwave.collaborationproject.FakeData.QueryData
import com.viwave.collaborationproject.R
import com.viwave.collaborationproject.data.DataSort
import com.viwave.collaborationproject.data.UploadData
import com.viwave.collaborationproject.data.general.User

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnLogin.setOnClickListener(
            object: View.OnClickListener{
                override fun onClick(v: View?) {
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
//                                    LogUtil.logD(TAG, gson.fromJson(QueryData().loginReturn2, User::class.java))
//                                    LogUtil.logD(TAG, UserPreference.instance.queryUser())
                                    UserPreference.instance.edit(UserKey.IS_LOGIN, true)
                                    replaceFragment(this@LoginFragment, SysListFragment(), getString(R.string.tag_sys_list))
                                }
                                else -> Toast.makeText(context, "Login failed.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }

            }
        )
    }

}
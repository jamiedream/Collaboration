package com.viwave.collaborationproject.DB.cache

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.viwave.collaborationproject.CollaborationApplication
import com.viwave.collaborationproject.data.general.SubSys
import com.viwave.collaborationproject.data.general.User

class UserPreference private constructor(){

    private val TAG = this::class.java.simpleName

    val USER_PREFERENCE_NAME = "UserPreference"

    val userPreferences = CollaborationApplication.context.getSharedPreferences(USER_PREFERENCE_NAME, Context.MODE_PRIVATE)

    companion object {
        val instance: UserPreference by lazy { UserPreference() }
    }

    fun editUser(user: User){
        val edit = userPreferences.edit()
        val json = Gson().toJson(user)
        edit.putString(UserKey.USER, json)
        edit.apply()
    }

    fun editSubSys(subSys: SubSys?){
        val edit = userPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(subSys)
        edit.putString(UserKey.SELECTED_SUBSYS, json)
        edit.apply()
    }

    fun edit(key: String, value: Boolean){
        val edit = userPreferences.edit()
        edit.putBoolean(key, value)
        edit.apply()
    }

    fun edit(key: String, value: String){
        val edit = userPreferences.edit()
        edit.putString(key, value)
        edit.apply()
    }

    fun edit(key: String, value: Float){
        val edit = userPreferences.edit()
        edit.putFloat(key, value)
        edit.apply()
    }

    fun edit(key: String, value: Int){
        val edit = userPreferences.edit()
        edit.putInt(key, value)
        edit.apply()
    }


    fun query(key: String, default: String): String{
        return userPreferences.getString(key, default)?: ""
    }

    fun query(key: String, default: Float): Float{
        return userPreferences.getFloat(key, default)
    }

    fun query(key: String, default: Int): Int{
        return userPreferences.getInt(key, default)
    }

    fun query(key: String, default: Boolean): Boolean{
        return userPreferences.getBoolean(key, default)
    }

    fun queryUser(): User?{
        val json = userPreferences.getString(UserKey.USER, "")
        Log.d("queryUser", json)
        return Gson().fromJson(json, User::class.java)
    }

    fun querySubSys(): SubSys?{
        val json = userPreferences.getString(UserKey.SELECTED_SUBSYS, "")
        return Gson().fromJson(json, SubSys::class.java)
    }

    fun remove(key: String){
        userPreferences.edit().remove(key).apply()
    }

    //logout
    fun clear(){
        userPreferences.edit().clear().apply()
    }

}
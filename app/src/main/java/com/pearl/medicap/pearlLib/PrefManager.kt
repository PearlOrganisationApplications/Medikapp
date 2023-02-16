package com.pearl.medicap.pearlLib

import android.content.Context
import android.content.SharedPreferences
import com.pearl.medicap.pearlLib.PrefManager

class PrefManager(var _context: Context) {
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor

    // shared pref mode  
    var PRIVATE_MODE = 0
    companion object {
        // Shared preferences file name
        private const val PREF_NAME = "welcome"
        private const val IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch"
    }
    init {
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    var isFirstTimeLaunch: Boolean
        get() = pref.getBoolean(IS_FIRST_TIME_LAUNCH, true)
        set(isFirstTime) {
            editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime)
            editor.commit()
        }
    var isCustomerlogin: Boolean
        get() = pref.getBoolean("Clogin", true)
        set(isFirstTime) {
            editor.putBoolean("Clogin", isFirstTime)
            editor.commit()
        }
    var isMedicallogin: Boolean
        get() = pref.getBoolean("Dlogin", true)
        set(isFirstTime) {
            editor.putBoolean("Dlogin", isFirstTime)
            editor.commit()
        }

    //location prefs
    fun setToLatL(lat:String){
        editor?.putString("lat_to_l",lat)
        editor?.commit()
    }

    fun getToLatL():String{
        return pref?.getString("lat_to_l","null").toString()
    }
    fun setToLngL(lat:String){
        editor?.putString("lng_to_l",lat)
        editor?.commit()
    }

    fun getToLngL():String{
        return pref?.getString("lng_to_l","null").toString()
    }
    fun setToLngM(lat:String){
        editor?.putString("lng_to_m",lat)
        editor?.commit()
    }

    fun getToLngM():String{
        return pref?.getString("lng_to_m","null").toString()
    }
    fun setToLatM(lat:String){
        editor?.putString("lat_to_m",lat)
        editor?.commit()
    }

    fun getToLatM():String{
        return pref?.getString("lat_to_m","null").toString()
    }


    fun setType(type:String){
        editor?.putString("type",type)
        editor?.commit()
    }

    fun getType():String{
        return pref?.getString("type","null").toString()
    }
    fun setTypeC(type:String){
        editor?.putString("typeC",type)
        editor?.commit()
    }

    fun getTypeC():String{
        return pref?.getString("typeC","null").toString()
    }

    fun setToLatLC(lat:String){
        editor?.putString("lat_to_lc",lat)
        editor?.commit()
    }

    fun getToLatLC():String{
        return pref?.getString("lat_to_lc","null").toString()
    }
    fun setToLngLC(lat:String){
        editor?.putString("lng_to_lc",lat)
        editor?.commit()
    }
    fun getToLngLC():String{
        return pref?.getString("lng_to_lc","null").toString()
    }


}
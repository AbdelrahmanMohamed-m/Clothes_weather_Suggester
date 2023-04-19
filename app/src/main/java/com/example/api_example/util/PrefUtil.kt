package com.example.api_example.util
import android.content.Context
import android.content.SharedPreferences
import com.example.api_example.ui.Clothes
import com.google.gson.Gson
object PrefUtil {
    private var sharedPref:SharedPreferences?=null
    private const val SHARED_PREFS_NAME="myClothes"
    private const val CLOTHE_KEY="keyClothes"
    private const val DateKey="dateValue"
    fun initPrefUtil(context:Context){
        sharedPref =context.getSharedPreferences(SHARED_PREFS_NAME,Context.MODE_PRIVATE)
    }
    var clothesList: List<Clothes>
        get() {
            val get = sharedPref?.getString(CLOTHE_KEY,"")
            return Gson().fromJson(get, Array<Clothes>::class.java)!!.toList()
        }
        set(value){
            val edit= sharedPref?.edit()
            val myObjectListJson = Gson().toJson(value)
            edit?.putString(CLOTHE_KEY,myObjectListJson)?.apply()
        }
    var date: String?
        get() = sharedPref?.getString(DateKey, "")
        set(value){
            val edit= sharedPref?.edit()
            edit?.putString(DateKey, value!!)?.apply()
        }
}

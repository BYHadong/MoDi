package byh.adong.modi.util

import android.content.Context
import android.content.SharedPreferences

//간단한 SQLite
object SharedPreferenceUtil {

    fun getPreference(context: Context) : String {
        val preferences : SharedPreferences = context.getSharedPreferences("modi", Context.MODE_PRIVATE)
        return preferences.getString("token", "")
    }

    fun savePreference(context: Context, value : String){
        val preferences : SharedPreferences = context.getSharedPreferences("modi", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("token", value)
        editor.apply()
    }

    fun removePreference(context: Context){
        val preferences : SharedPreferences = context.getSharedPreferences("modi", Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.remove("token")
        editor.apply()
    }
}
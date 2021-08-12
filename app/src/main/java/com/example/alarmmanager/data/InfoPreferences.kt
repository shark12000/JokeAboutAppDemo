package com.example.alarmmanager.data

import android.content.Context
import android.content.SharedPreferences
import com.example.alarmmanager.UserData

class InfoPreferences(context: Context) {
    private val userPreferences = context.getSharedPreferences("NamePref", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = userPreferences.edit()

    fun saveData(firstName: String, lastName:String) {
        editor.putString("name", firstName)
        editor.putString("lastName", lastName)
        editor.apply()
    }

    fun loadData(): UserData {
        val firstName = userPreferences.getString("name", "")
        val lastName = userPreferences.getString("lastName", "")
        return UserData(firstName, lastName)
    }
}
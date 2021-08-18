package com.example.alarmmanager.data

import android.content.Context
import android.content.SharedPreferences
import com.example.alarmmanager.UserData

class InfoPreferences(context: Context) {
    private val userPreferences = context.getSharedPreferences("NamePref", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = userPreferences.edit()

    fun saveData(firstName: String, lastName:String, time: Long) {
        editor.putString("name", firstName)
        editor.putString("lastName", lastName)
        editor.putLong("time", time)
        editor.apply()
    }

    fun loadData(): UserData {
        val firstName = userPreferences.getString("name", "")
        val lastName = userPreferences.getString("lastName", "")
        val time = userPreferences.getLong("time", 0)
        return UserData(firstName, lastName, time)
    }

    fun saveJoke(joke: String) {
        editor.putString("joke", joke)
        editor.apply()
    }

    fun loadJoke() : String {
        return userPreferences.getString("joke", "")!!
    }
 }
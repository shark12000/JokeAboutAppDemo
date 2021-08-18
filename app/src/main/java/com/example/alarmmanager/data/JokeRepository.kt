package com.example.alarmmanager.data

import com.example.alarmmanager.UserData
import javax.inject.Inject

class JokeRepository @Inject constructor(private val infoPreferences: InfoPreferences) {

    private var userData = infoPreferences.loadData()

    fun saveData(userData: UserData) {
        infoPreferences.saveData(userData.firstName!!, userData.lastName!!, userData.time!!)
        this.userData = userData
    }

    fun loadData(): UserData {
        return userData
    }

    fun saveJoke(joke: String) {
        infoPreferences.saveJoke(joke)
    }

    fun loadJoke() : String {
        return infoPreferences.loadJoke()
    }
}
package com.example.alarmmanager.data

import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.work.WorkInfo
import com.example.alarmmanager.UserData

class JokeRepository(private val infoPreferences: InfoPreferences) {

    private var userData = infoPreferences.loadData()
    private var mData: MediatorLiveData<WorkInfo> = MediatorLiveData()

    fun saveData(userData: UserData) {
        infoPreferences.saveData(userData.firstName!!, userData.lastName!!)
        this.userData = userData
    }

    fun loadData(): UserData {
        return userData
    }

    fun getJoke() : LiveData<WorkInfo> {
        return mData
    }

    fun addDataSource(data: LiveData<WorkInfo>)  {
        mData.addSource(data, mData::setValue)
    }

    fun removeDataSource(data: LiveData<WorkInfo>)  {
        mData.removeSource(data)
    }
}
package com.example.alarmmanager

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.WorkInfo
import com.example.alarmmanager.data.JokeRepository

class MainViewModel(private val repository: JokeRepository) : ViewModel() {
        fun getData(): LiveData<WorkInfo> {
            return repository.getJoke()
        }
}
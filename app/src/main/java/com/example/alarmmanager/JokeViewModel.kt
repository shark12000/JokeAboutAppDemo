package com.example.alarmmanager

import androidx.lifecycle.ViewModel
import com.example.alarmmanager.data.JokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(private val jokeRepository: JokeRepository) : ViewModel() {
    fun saveData(userData: UserData) {
        jokeRepository.saveData(userData = userData)
    }
}
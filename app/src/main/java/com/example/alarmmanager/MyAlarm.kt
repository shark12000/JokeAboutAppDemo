package com.example.alarmmanager

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.alarmmanager.data.JokeRepository

class MyAlarm : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        workManager(context, intent)
    }

    private fun workManager(context: Context, intent: Intent) {
        val workManager: WorkManager = WorkManager.getInstance(context)
        val request = OneTimeWorkRequest.Builder(JokeWorker::class.java)
        //val data = Data.Builder()
        //data.putString("firstName", intent.getStringExtra("firstName"))
        //data.putString("lastName", intent.getStringExtra("lastName"))
        //request.setInputData(data.build())
        val req = request.build()
        workManager.enqueue(req)
    }
}
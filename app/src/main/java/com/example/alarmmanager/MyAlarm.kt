package com.example.alarmmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager

class MyAlarm : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        workManager(context)
    }

    private fun workManager(context: Context) {
        val workManager: WorkManager = WorkManager.getInstance(context)
        val request = OneTimeWorkRequest.Builder(JokeWorker::class.java)
        val req = request.build()
        workManager.enqueue(req)
    }
}
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

class MyAlarm : BroadcastReceiver() {

    private var liveData: MutableLiveData<WorkInfo> = MutableLiveData()

    fun getData(): LiveData<WorkInfo> {
        return liveData
    }

    override fun onReceive(context: Context, intent: Intent) {
        workManager(context, intent)
        showNotification(context)
    }

    private fun showNotification(context: Context) {
        val intent: PendingIntent = PendingIntent
                .getActivity(context, 0
                        , Intent(context, MainActivity::class.java), 0)

        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Joke!!!")
    }

    private fun workManager(context: Context, intent: Intent) {
        val workManager: WorkManager = WorkManager.getInstance(context)
        val request = OneTimeWorkRequest.Builder(JokeWorker::class.java)
        val data = Data.Builder()
        data.putString("firstName", intent.getStringExtra("firstName"))
        data.putString("lastName", intent.getStringExtra("lastName"))
        request.setInputData(data.build())
        val req = request.build()
        workManager.enqueue(req)
        liveData = workManager.getWorkInfoByIdLiveData(req.id) as MutableLiveData<WorkInfo>
    }
}
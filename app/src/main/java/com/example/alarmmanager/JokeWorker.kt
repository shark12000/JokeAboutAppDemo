package com.example.alarmmanager

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.core.content.contentValuesOf
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.alarmmanager.data.InfoPreferences
import com.example.alarmmanager.data.JokeRepository
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class JokeWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {
    private val jokeRepository = JokeRepository(InfoPreferences(context))

    companion object {
        const val PRIORITY_HIGH = 2
    }

    override fun doWork(): Result {
        getJoke(jokeRepository.loadData().firstName!!, jokeRepository.loadData().lastName!!)
        showNotification(context = context)
        return Result.success()
    }

    private fun showNotification(context: Context) {
        val intent = PendingIntent
                .getActivity(context, 0
                        , Intent(context, MainActivity::class.java), 0)

        val builder = NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Joke!!!")
                .setContentText(jokeRepository.loadJoke())
                .setPriority(PRIORITY_HIGH)
                .setContentIntent(intent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

        val notification: Notification = builder.notification
        val manager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, notification)
    }

    private fun getJoke(firstName: String, lastName: String) {
        val client = OkHttpClient()

        val urlBuilder: HttpUrl.Builder = "https://api.icndb.com/jokes/random".toHttpUrlOrNull()!!.newBuilder()
        urlBuilder.addQueryParameter("firstName", firstName)
        urlBuilder.addQueryParameter("lastName", lastName)

        val request: Request = Request.Builder()
                .url(urlBuilder.build().toString())
                .build()

        client.newCall(request).execute().use { response ->
            try {
                if(response.isSuccessful) {
                    val json = JSONObject(response.body!!.string())
                    val joke = json.getJSONObject("value")
                    jokeRepository.saveJoke(joke.getString("joke"))
                }
            } catch (e: Exception) {
                jokeRepository.saveJoke(e.message.toString())
            }

        }
    }
}
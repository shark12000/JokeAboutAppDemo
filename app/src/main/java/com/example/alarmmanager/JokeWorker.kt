package com.example.alarmmanager

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class JokeWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val string = getJoke(inputData.getString("firstName")!!, inputData.getString("lastName")!!)
        val outputData: Data = Data.Builder().putString("joke", string).build()
        return Result.success(outputData)
    }

    private fun getJoke(firstName: String, lastName: String) : String {
        val client = OkHttpClient()
        lateinit var responseString: String

        val urlBuilder: HttpUrl.Builder = "http://api.icndb.com/jokes/random".toHttpUrlOrNull()!!.newBuilder()
        urlBuilder.addQueryParameter("firstName", firstName)
        urlBuilder.addQueryParameter("lastName", lastName)

        val request: Request = Request.Builder()
                .url(urlBuilder.build().toString())
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                call.cancel()
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful) {
                    val json = JSONObject(response.body!!.string())
                    responseString = json.getString("joke")
                }
            }
        })

        return responseString
    }
}
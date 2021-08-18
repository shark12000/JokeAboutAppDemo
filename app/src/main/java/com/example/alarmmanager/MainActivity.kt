package com.example.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var firstNameText: EditText
    private lateinit var lastNameText: EditText
    private lateinit var radioButtonFor5Min: RadioButton
    private lateinit var radioButtonFor30Min: RadioButton
    private lateinit var radioButtonFor60Min: RadioButton
    private lateinit var radioGroup: RadioGroup
    private val viewModel: JokeViewModel by viewModels()

    companion object {
        const val FIVE_MINUTES: Long = 5
        const val HALF_HOUR: Long  = 30
        const val ONE_HOUR: Long  = 60
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        onButtonClicked()
    }

    private fun init() {
        saveButton = findViewById(R.id.button)
        firstNameText = findViewById(R.id.firstName)
        lastNameText = findViewById(R.id.lastName)
        radioButtonFor30Min = findViewById(R.id.treMinRadio)
        radioButtonFor5Min = findViewById(R.id.femMinRadio)
        radioButtonFor60Min = findViewById(R.id.enHourRadio)
        radioGroup = findViewById(R.id.radioGroup)
    }

    private fun onButtonClicked() {
        saveButton.setOnClickListener {
            val firstName = firstNameText.text.toString()
            val lastName = lastNameText.text.toString()
            var time: Long = 0
            when (radioGroup.checkedRadioButtonId) {
                R.id.enHourRadio -> {
                    setAlarm(TimeUnit.MINUTES.toMillis(ONE_HOUR))
                    time = ONE_HOUR
                }
                R.id.femMinRadio -> {
                    setAlarm(TimeUnit.SECONDS.toMillis(FIVE_MINUTES))
                    time = FIVE_MINUTES
                }
                R.id.treMinRadio -> {
                    setAlarm(TimeUnit.SECONDS.toMillis(HALF_HOUR))
                    time = HALF_HOUR
                }
            }
            viewModel.saveData(UserData(firstName = firstName, lastName = lastName, time = time))
        }
    }

    private fun setAlarm(timeInMillis: Long) {
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, MyAlarm::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.setRepeating(AlarmManager.RTC, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(this, timeInMillis.toString(), Toast.LENGTH_SHORT).show()
    }
}
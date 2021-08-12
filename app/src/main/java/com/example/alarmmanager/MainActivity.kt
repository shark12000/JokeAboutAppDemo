package com.example.alarmmanager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var firstNameText: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var radioButtonFor5Min: RadioButton
    private lateinit var radioButtonFor30Min: RadioButton
    private lateinit var radioButtonFor60Min: RadioButton
    private lateinit var radioGroup: RadioGroup
    private lateinit var mReceiver: MyAlarm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        onButtonClicked()
        passData()
    }

    override fun onStart() {
        super.onStart()

    }

    private fun init() {
        saveButton = findViewById(R.id.button)
        firstNameText = findViewById(R.id.firstName)
        lastNameTextView = findViewById(R.id.lastName)
        radioButtonFor30Min = findViewById(R.id.treMinRadio)
        radioButtonFor5Min = findViewById(R.id.femMinRadio)
        radioButtonFor60Min = findViewById(R.id.enHourRadio)
        radioGroup = findViewById(R.id.radioGroup)
    }

    private fun onButtonClicked() {
       saveButton.setOnClickListener {
           when (radioGroup.checkedRadioButtonId) {
               R.id.enHourRadio -> setAlarm(TimeUnit.MINUTES.toMillis(60))
               R.id.femMinRadio -> setAlarm(TimeUnit.MINUTES.toMillis(5))
               R.id.treMinRadio -> setAlarm(TimeUnit.SECONDS.toMillis(30))
           }
       }
    }

    private fun passData() {
        val intent = Intent(baseContext, MyAlarm::class.java)
        intent.putExtra("firstName", firstNameText.text.toString())
        intent.putExtra("lastName", lastNameTextView.text.toString())
    }

    private fun setAlarm(timeInMillis: Long) {
        val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, MyAlarm::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        alarmManager.setRepeating(AlarmManager.RTC, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show()
    }
}
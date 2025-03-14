package com.jdw.justdrink.notifications

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.jdw.justdrink.helper.SharedPreferencesHelper
import java.util.Calendar

class NotificationWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val prefs = SharedPreferencesHelper(applicationContext)
        val currentTime = Calendar.getInstance()
        val currentHour = currentTime.get(Calendar.HOUR_OF_DAY)
        val currentMinute = currentTime.get(Calendar.MINUTE)

        val sleepStart = prefs.getSleepStart()
        val sleepEnd = prefs.getSleepEnd()

        Log.d("NotificationWorker", "Worker triggered at $currentHour:$currentMinute")

        if (currentHour in sleepStart until sleepEnd) {
            Log.d("NotificationWorker", "Skipping notification: Sleep hours active ($sleepStart - $sleepEnd)")
            return Result.success()
        }

        Log.d("NotificationWorker", "Sending notification")
        NotificationUtils.showNotification(applicationContext, "Water Reminder", "Time to drink water!")
        return Result.success()
    }
}

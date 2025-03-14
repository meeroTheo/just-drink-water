package com.jdw.justdrink.notifications

import android.content.Context
import androidx.work.*
import com.jdw.justdrink.helper.SharedPreferencesHelper
import java.util.concurrent.TimeUnit

object NotificationScheduler {
    fun scheduleNotifications(context: Context) {
        val prefs = SharedPreferencesHelper(context)
        var interval = prefs.getReminderFrequency().toLong()

        if (interval < 15) {
            interval = 15 //min interval reqired for workmanager
        }

        //cancel existing work before scheduling new one
        WorkManager.getInstance(context).cancelUniqueWork("WaterReminder")


        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(interval, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true) //prevent running on low battery
                    .setRequiresCharging(false) //can run even if not charging
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "WaterReminder",
            ExistingPeriodicWorkPolicy.REPLACE, //ensure only one job exists
            workRequest
        )
    }
}

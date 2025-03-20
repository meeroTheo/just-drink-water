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
            interval = 15
        }

        //cancels the previous work when interval changes
        WorkManager.getInstance(context).cancelUniqueWork("WaterReminder")

        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(1, TimeUnit.MINUTES)
            .setInitialDelay(interval, TimeUnit.MINUTES)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .setRequiresCharging(false)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "WaterReminder",
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }


}

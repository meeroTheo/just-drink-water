package com.jdw.justdrink.components.settings


import android.content.Context
import androidx.compose.runtime.*

import android.util.Log
import androidx.work.WorkManager


@Composable
fun SettingsPage(context: Context) {
    NotificationSelector(context)
    val workManager = WorkManager.getInstance(context)
    workManager.getWorkInfosForUniqueWorkLiveData("WaterReminder").observeForever { workInfos ->
        for (workInfo in workInfos) {
            Log.d("WorkManagerStatus", "Work ID: ${workInfo.id}, State: ${workInfo.state}")
        }
    }

}

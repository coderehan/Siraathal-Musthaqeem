package com.islamicapp.siraathalmusthaqeem

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islamicapp.core.di.coreModule
import com.islamicapp.data.di.dataModule
import com.islamicapp.data.qurannotification.QuranReminderScheduler
import com.islamicapp.domain.di.domainModule
import com.islamicapp.siraathalmusthaqeem.di.appModule
import com.islamicapp.siraathalmusthaqeem.screen.qurannotification.QuranReminderReceiver
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        // Initialize Koin
        startKoin {
            androidContext(this@MyApp)
            androidLogger(Level.ERROR)
            modules(
                coreModule,
                dataModule,
                domainModule,
                appModule
            )
        }

        // Schedule Quran reminders
        val scheduler = QuranReminderScheduler(
            context = this,
            receiverClass = QuranReminderReceiver::class.java
        )
        scheduler.scheduleAllReminders()
    }
}

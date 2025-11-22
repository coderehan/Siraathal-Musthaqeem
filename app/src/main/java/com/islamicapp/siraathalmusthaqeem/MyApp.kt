package com.islamicapp.siraathalmusthaqeem

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.islamicapp.core.di.coreModule
import com.islamicapp.data.di.dataModule
import com.islamicapp.domain.di.domainModule
import com.islamicapp.siraathalmusthaqeem.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
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
    }
}

package com.islamicapp.siraathalmusthaqeem

import android.app.Application
import com.islamicapp.siraathalmusthaqeem.di.makeApiModule
import com.islamicapp.siraathalmusthaqeem.di.makeAppModule
import com.islamicapp.siraathalmusthaqeem.di.makeDataSourceModule
import com.islamicapp.siraathalmusthaqeem.di.makeDomainModule
import com.islamicapp.siraathalmusthaqeem.di.makeRepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                    makeApiModule(),
                    makeDataSourceModule(),
                    makeRepositoryModule(),
                    makeDomainModule(),
                    makeAppModule()
                )
        }
    }
}

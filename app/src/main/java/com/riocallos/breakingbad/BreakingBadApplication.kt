package com.riocallos.breakingbad

import android.app.Application
import com.riocallos.breakingbad.di.appModule
import com.riocallos.breakingbad.di.databaseModule
import com.riocallos.breakingbad.di.networkModule
import com.riocallos.breakingbad.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Application class for initializing config, initiations and dependencies
 */
class BreakingBadApplication : Application() {

    /**
     * initializes the koin dependencies when application is created
     */
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BreakingBadApplication)
            modules(listOf(appModule, databaseModule, networkModule, viewModelModule))
        }

    }

}
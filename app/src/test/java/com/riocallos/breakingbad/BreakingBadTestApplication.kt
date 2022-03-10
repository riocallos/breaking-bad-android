package com.riocallos.breakingbad

import android.app.Application
import com.riocallos.breakingbad.di.appModule
import com.riocallos.breakingbad.di.databaseModule
import com.riocallos.breakingbad.di.networkModule
import com.riocallos.breakingbad.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.core.logger.Level
import org.koin.core.module.Module


class BreakingBadTestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        stopKoin()
        startKoin {
            if (BuildConfig.DEBUG) {
                androidLogger(Level.DEBUG)
            }
            androidContext(this@BreakingBadTestApplication)
            modules(listOf(appModule, databaseModule, networkModule, viewModelModule))
        }

    }

    fun loadModules(module: Module) {
        loadKoinModules(module)
    }

    fun unloadModules(module: Module) {
        unloadKoinModules(module)
    }

    companion object {

        @JvmStatic
        @get:Synchronized
        lateinit var instance: BreakingBadTestApplication
            private set
    }
}
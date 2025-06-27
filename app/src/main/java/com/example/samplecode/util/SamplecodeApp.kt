package com.example.samplecode.util

import android.app.Application
import com.example.samplecode.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SamplecodeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SamplecodeApp)
            modules(appModule)
        }
    }
}
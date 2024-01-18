package com.remid.hangmangame

import android.app.Application
import com.remid.hangmangame.di.application.AppComponent
import com.remid.hangmangame.di.application.AppModule
import com.remid.hangmangame.di.application.DaggerAppComponent

class HangmanApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        appComponent.inject(this)
        super.onCreate()
    }

}
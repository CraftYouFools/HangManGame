package com.remid.hangmangame.di.application

import com.remid.hangmangame.HangmanApplication
import com.remid.hangmangame.di.activity.ActivityComponent
import com.remid.hangmangame.di.activity.ActivityModule
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    fun newActivityComponent(activityModule: ActivityModule): ActivityComponent

    fun inject(hangmanApplication: HangmanApplication)
}
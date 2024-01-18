package com.remid.hangmangame.di.application

import android.app.Application
import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.hangman_game.data.HangmanHangmanGameRepositoryImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@Module
class AppModule(private val application: Application) {

    @Provides
    fun application() = application


    @Provides
    @AppScope
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

}
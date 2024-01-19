package com.remid.hangmangame.di.application

import android.app.Application
import android.content.Context
import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.hangman_game.data.HangmanGameRepositoryImpl
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

    @Provides
    @AppScope
    @AppContextQualifier
    fun provideAppContext(): Context = application.applicationContext


    @Provides
    @AppScope
    fun hangmanGameRepository(hangmanGameRepositoryImpl: HangmanGameRepositoryImpl) : HangmanGameRepository {
        return hangmanGameRepositoryImpl
    }

}
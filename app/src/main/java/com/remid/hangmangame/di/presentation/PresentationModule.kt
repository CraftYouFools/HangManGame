package com.remid.hangmangame.di.presentation

import com.remid.hangmangame.di.application.MainDispatcher
import com.remid.hangmangame.hangman_game.business.GetCurrentGameUseCase
import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.hangman_game.data.HangmanHangmanGameRepositoryImpl
import com.remid.hangmangame.hangman_game.presentation.viewmodel.HangManGameViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher


@Module
class PresentationModule {

    @Provides
    @PresentationScope
    fun hangmanGameRepository(hangmanGameRepositoryImpl: HangmanHangmanGameRepositoryImpl) : HangmanGameRepository {
        return hangmanGameRepositoryImpl
    }




    @Provides
    @PresentationScope
    fun provideHangManViewModel(
        getCurrentGameUseCase: GetCurrentGameUseCase,
        @MainDispatcher dispatcher: CoroutineDispatcher
    ): HangManGameViewModel {
        return HangManGameViewModel(getCurrentGameUseCase,dispatcher)
    }

}
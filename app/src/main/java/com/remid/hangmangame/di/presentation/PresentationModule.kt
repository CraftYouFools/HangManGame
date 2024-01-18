package com.remid.hangmangame.di.presentation

import com.remid.hangmangame.di.application.MainDispatcher
import com.remid.hangmangame.hangman_game.business.usecases.GetCurrentGameUseCase
import com.remid.hangmangame.hangman_game.business.usecases.GetWordHiddenWordUseCase
import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.hangman_game.business.usecases.GuessNewLetterUseCase
import com.remid.hangmangame.hangman_game.business.usecases.InitWordListUseCase
import com.remid.hangmangame.hangman_game.business.usecases.StartNewGameUseCase
import com.remid.hangmangame.hangman_game.data.HangmanGameRepositoryImpl
import com.remid.hangmangame.hangman_game.presentation.viewmodel.HangManGameViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher


@Module
class PresentationModule {

    @Provides
    @PresentationScope
    fun hangmanGameRepository(hangmanGameRepositoryImpl: HangmanGameRepositoryImpl) : HangmanGameRepository {
        return hangmanGameRepositoryImpl
    }

    @Provides
    @PresentationScope
    fun provideHangManViewModel(
        initWordListUseCase: InitWordListUseCase,
        getCurrentGameUseCase: GetCurrentGameUseCase,
        startNewGameUseCase: StartNewGameUseCase,
        getWordHiddenWordUseCase : GetWordHiddenWordUseCase,
        guessNewLetterUseCase: GuessNewLetterUseCase,
        @MainDispatcher dispatcher: CoroutineDispatcher
    ): HangManGameViewModel {
        return HangManGameViewModel(initWordListUseCase,getCurrentGameUseCase,startNewGameUseCase,getWordHiddenWordUseCase, guessNewLetterUseCase,dispatcher)
    }

}
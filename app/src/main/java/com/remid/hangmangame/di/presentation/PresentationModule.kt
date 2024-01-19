package com.remid.hangmangame.di.presentation

import com.remid.hangmangame.di.application.MainDispatcher
import com.remid.hangmangame.hangman_game.business.usecases.GetCurrentGameUseCase
import com.remid.hangmangame.hangman_game.business.usecases.GetHiddenWordUseCase
import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.hangman_game.business.usecases.ClearCurrentWordUseCase
import com.remid.hangmangame.hangman_game.business.usecases.FinishCurrentGameUseCase
import com.remid.hangmangame.hangman_game.business.usecases.GetCurrentWordUseCase
import com.remid.hangmangame.hangman_game.business.usecases.GuessNewLetterUseCase
import com.remid.hangmangame.hangman_game.business.usecases.InitWordListUseCase
import com.remid.hangmangame.hangman_game.business.usecases.IsGameLostUseCase
import com.remid.hangmangame.hangman_game.business.usecases.IsGameWonUseCase
import com.remid.hangmangame.hangman_game.business.usecases.ResetGameHistoryUseCase
import com.remid.hangmangame.hangman_game.business.usecases.StartNewGameUseCase
import com.remid.hangmangame.hangman_game.data.HangmanGameRepositoryImpl
import com.remid.hangmangame.hangman_game.presentation.viewmodel.HangManGameViewModel
import com.remid.hangmangame.hangman_game.presentation.viewmodel.HangManGameWonViewModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher


@Module
class PresentationModule {

    @Provides
    @PresentationScope
    fun provideHangManViewModel(
        initWordListUseCase: InitWordListUseCase,
        getCurrentGameUseCase: GetCurrentGameUseCase,
        startNewGameUseCase: StartNewGameUseCase,
        getHiddenWordUseCase: GetHiddenWordUseCase,
        guessNewLetterUseCase: GuessNewLetterUseCase,
        finishCurrentGameUseCase: FinishCurrentGameUseCase,
        clearCurrentWordUseCase: ClearCurrentWordUseCase,
        resetGameHistoryUseCase: ResetGameHistoryUseCase,
        @MainDispatcher dispatcher: CoroutineDispatcher
    ): HangManGameViewModel {
        return HangManGameViewModel(
            initWordListUseCase,
            getCurrentGameUseCase,
            startNewGameUseCase,
            getHiddenWordUseCase,
            guessNewLetterUseCase,
            finishCurrentGameUseCase,
            clearCurrentWordUseCase,
            resetGameHistoryUseCase,
            dispatcher
        )
    }

    @Provides
    @PresentationScope
    fun provideHangManGameWonViewModel(
        getCurrentWordUseCase: GetCurrentWordUseCase,
        @MainDispatcher dispatcher: CoroutineDispatcher
    ): HangManGameWonViewModel {
        return HangManGameWonViewModel(
            getCurrentWordUseCase,
            dispatcher
        )
    }

}
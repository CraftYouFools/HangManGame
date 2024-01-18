package com.remid.hangmangame.hangman_game.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remid.hangmangame.hangman_game.business.usecases.GetCurrentGameUseCase
import com.remid.hangmangame.hangman_game.business.usecases.GetHiddenWordUseCase
import com.remid.hangmangame.hangman_game.business.usecases.GuessNewLetterUseCase
import com.remid.hangmangame.hangman_game.business.usecases.InitWordListUseCase
import com.remid.hangmangame.hangman_game.business.usecases.IsGameLostUseCase
import com.remid.hangmangame.hangman_game.business.usecases.IsGameWonUseCase
import com.remid.hangmangame.hangman_game.business.usecases.StartNewGameUseCase
import com.remid.hangmangame.hangman_game.presentation.HangmanGameContent
import com.remid.hangmangame.hangman_game.presentation.HangmanGameViewState
import com.remid.hangmangame.shared.business.HangAppResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class HangManGameViewModel(
    private val initWordListUseCase: InitWordListUseCase,
    private val getCurrentGameUseCase: GetCurrentGameUseCase,
    private val startNewGameUseCase: StartNewGameUseCase,
    private val getHiddenWordUseCase: GetHiddenWordUseCase,
    private val guessNewLetterUseCase : GuessNewLetterUseCase,
    private val isGameLostUseCase: IsGameLostUseCase,
    private val isGameWonUseCase: IsGameWonUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private var _viewState = MutableLiveData<HangmanGameViewState>()
    val viewState: LiveData<HangmanGameViewState>
        get() = _viewState

    private var currentWordToGuess:String?=null

    init {
        getCurrentGame()
        startNewGame()
    }

    private fun startNewGame() {
        viewModelScope.launch(dispatcher) {
            initWordListUseCase.execute()
            startNewGameUseCase.execute()
        }
    }

    private fun getCurrentGame() {
        viewModelScope.launch(dispatcher) {
            _viewState.postValue(HangmanGameViewState.Loading)
            when (val result = getCurrentGameUseCase.execute()) {
                is HangAppResult.OnFailure -> {
                    _viewState.postValue(HangmanGameViewState.Error)
                }
                is HangAppResult.OnSuccess -> {
                    result.data.let {
                        it.collect { hangManGameDetails ->
                            with(hangManGameDetails) {
                                val content = HangmanGameContent(
                                    victories,
                                    gameNumber,
                                    TriesNumber,
                                    getHiddenWordUseCase.execute(guessWord, letterList)
                                )
                                _viewState.value = if (isGameLostUseCase.execute(hangManGameDetails.TriesNumber)) {
                                        HangmanGameViewState.GamLost(content)
                                    } else if (isGameWonUseCase.execute( getHiddenWordUseCase.execute(guessWord, letterList),guessWord)
                                    ) {
                                        HangmanGameViewState.GameWon(content)
                                    } else {
                                        HangmanGameViewState.Content(content)
                                    }
                                currentWordToGuess = hangManGameDetails.guessWord
                            }
                        }
                    }
                }
            }
        }
    }

    fun onGuessNewLetter(letter : Char) {
        viewModelScope.launch {
            currentWordToGuess?.let {
                guessNewLetterUseCase.execute(letter,it)
            }
        }
    }

}
package com.remid.hangmangame.hangman_game.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remid.hangmangame.hangman_game.business.usecases.ClearCurrentWordUseCase
import com.remid.hangmangame.hangman_game.business.usecases.FinishCurrentGameUseCase
import com.remid.hangmangame.hangman_game.business.usecases.GetCurrentGameUseCase
import com.remid.hangmangame.hangman_game.business.usecases.GetHiddenWordUseCase
import com.remid.hangmangame.hangman_game.business.usecases.GuessNewLetterUseCase
import com.remid.hangmangame.hangman_game.business.usecases.InitWordListUseCase
import com.remid.hangmangame.hangman_game.business.usecases.ResetGameHistoryUseCase
import com.remid.hangmangame.hangman_game.business.usecases.StartNewGameUseCase
import com.remid.hangmangame.hangman_game.presentation.HangmanGameContent
import com.remid.hangmangame.hangman_game.presentation.HangmanGameViewState
import com.remid.hangmangame.shared.business.HangAppResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HangManGameViewModel(
    private val initWordListUseCase: InitWordListUseCase,
    private val getCurrentGameUseCase: GetCurrentGameUseCase,
    private val startNewGameUseCase: StartNewGameUseCase,
    private val getHiddenWordUseCase: GetHiddenWordUseCase,
    private val guessNewLetterUseCase : GuessNewLetterUseCase,
    private val finishCurrentGameUseCase : FinishCurrentGameUseCase,
    private val clearCurrentWordUseCase: ClearCurrentWordUseCase,
    private val resetGameHistoryUseCase : ResetGameHistoryUseCase,
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

    private var job: Job? = null
    private fun startNewGame() {
        viewModelScope.launch(dispatcher) {
            initWordListUseCase.execute()
            startNewGameUseCase.execute()
        }
    }

    private fun getCurrentGame() {
        job = viewModelScope.launch(dispatcher) {
            _viewState.postValue(HangmanGameViewState.Loading)
            when (val result = getCurrentGameUseCase.execute()) {
                is HangAppResult.OnFailure -> {
                    _viewState.postValue(HangmanGameViewState.Error)
                }
                is HangAppResult.OnSuccess -> {
                    result.data.let {
                        it.collect { hangManGameDetails ->
                            Log.d(TAG, " guessWord : "+hangManGameDetails.guessWord)
                            with(hangManGameDetails) {
                                val content = HangmanGameContent(
                                    victories,
                                    gameNumber,
                                    triesNumber,
                                    getHiddenWordUseCase.execute(guessWord, letterList)
                                )
                                Log.d(TAG, " content : $content")

                                _viewState.value = if (hangManGameDetails.isGameLost) {
                                        finishCurrentGameUseCase.execute(false)
                                        HangmanGameViewState.GameLost(content)
                                    } else if (isGameWon) {
                                        finishCurrentGameUseCase.execute(true)
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

    fun onDialogShown(){
        job?.cancel()
    }

    fun onDialogClosed() {
        reInitGame()
    }

    private fun reInitGame() {
        viewModelScope.launch(dispatcher) {
            clearCurrentWordUseCase.execute()
            _viewState.postValue(HangmanGameViewState.Loading)
            startNewGame()
            getCurrentGame()
        }
    }

    fun onGuessNewLetter(letter : Char) {
        viewModelScope.launch(dispatcher) {
            currentWordToGuess?.let {
                guessNewLetterUseCase.execute(letter,it)
            }
        }
    }

    fun onReset() {
        viewModelScope.launch(dispatcher) {
            resetGameHistoryUseCase.execute()
        }
    }

    companion object {
        private const val TAG = "HangManGameViewModel"
    }

}
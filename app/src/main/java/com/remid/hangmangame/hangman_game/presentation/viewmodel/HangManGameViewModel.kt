package com.remid.hangmangame.hangman_game.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remid.hangmangame.hangman_game.business.GetCurrentGameUseCase
import com.remid.hangmangame.hangman_game.presentation.HangmanGameContent
import com.remid.hangmangame.hangman_game.presentation.HangmanGameViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import com.remid.hangmangame.shared.business.HangAppResult
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class HangManGameViewModel(
    private val getCurrentGameUseCase: GetCurrentGameUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {


    private var _viewState = MutableLiveData<HangmanGameViewState>()
    val viewState: LiveData<HangmanGameViewState>
        get() = _viewState


    fun getCurrentGame() {
        viewModelScope.launch(dispatcher) {
            _viewState.postValue(HangmanGameViewState.Loading)
            when (val result = getCurrentGameUseCase.execute()) {
                is HangAppResult.OnFailure -> {
                    _viewState.postValue(HangmanGameViewState.Error)
                }

                is HangAppResult.OnSuccess -> {
                    result.data.let {
                        it.collect { hangManGameDetails ->
                            _viewState.value = HangmanGameViewState.Content(
                                    with(hangManGameDetails) {
                                        HangmanGameContent(
                                            victories,
                                            gameNumber,
                                            leftTriesNumber,
                                            guessWord
                                        )
                                    }

                                )
                        }
                    }
                }
            }
        }
    }

}
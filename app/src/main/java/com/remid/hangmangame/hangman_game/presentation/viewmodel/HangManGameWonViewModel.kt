package com.remid.hangmangame.hangman_game.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remid.hangmangame.hangman_game.business.usecases.GetGameStateUseCase
import com.remid.hangmangame.hangman_game.presentation.viewstate.viewstate_content.HangmanGameFinishedContent
import com.remid.hangmangame.hangman_game.presentation.viewstate.HangmanGameFinishedViewState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HangManGameWonViewModel(
    private val getGameStateUseCase: GetGameStateUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _viewState = MutableLiveData<HangmanGameFinishedViewState>()
    val viewState: LiveData<HangmanGameFinishedViewState>
        get() = _viewState

    private var job: Job? = null

    fun getCurrentWord() {
        Log.d(TAG, "getCurrentWord() called")
        job = viewModelScope.launch(dispatcher) {
            val result = getGameStateUseCase.execute()
            _viewState.value = HangmanGameFinishedViewState(HangmanGameFinishedContent(result.word,result.isGameWon))
        }
    }

    companion object {
        private const val TAG = "HangManGameWonViewModel"
    }

}
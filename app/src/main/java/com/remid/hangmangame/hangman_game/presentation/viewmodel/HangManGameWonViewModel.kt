package com.remid.hangmangame.hangman_game.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.remid.hangmangame.hangman_game.business.usecases.ClearCurrentWordUseCase
import com.remid.hangmangame.hangman_game.business.usecases.FinishCurrentGameUseCase
import com.remid.hangmangame.hangman_game.business.usecases.GetCurrentWordUseCase
import com.remid.hangmangame.hangman_game.presentation.HangmanGameFinishedContent
import com.remid.hangmangame.hangman_game.presentation.HangmanGameFinishedViewState
import com.remid.hangmangame.shared.business.HangAppResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HangManGameWonViewModel(
    private val getCurrentWordUseCase: GetCurrentWordUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _viewState = MutableLiveData<HangmanGameFinishedViewState>()
    val viewState: LiveData<HangmanGameFinishedViewState>
        get() = _viewState

    private var job: Job? = null

    fun getCurrentWord() {
        Log.d(TAG, "getCurrentWord() called")
        job = viewModelScope.launch(dispatcher) {
            when (val result = getCurrentWordUseCase.execute()) {
                is HangAppResult.OnFailure -> {
                    Log.d(TAG, " On failure ")
                }
                is HangAppResult.OnSuccess -> {
                    Log.d(TAG, " On Success ")
                    result.data.collect {
                        _viewState.value = HangmanGameFinishedViewState(HangmanGameFinishedContent(it.word))
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "HangManGameWonViewModel"
    }

}
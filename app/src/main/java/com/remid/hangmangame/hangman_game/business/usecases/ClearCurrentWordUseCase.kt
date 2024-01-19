package com.remid.hangmangame.hangman_game.business.usecases

import android.util.Log
import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.shared.business.HangAppResult
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ClearCurrentWordUseCase @Inject constructor(private val repository: HangmanGameRepository) {
    suspend fun execute() {
        repository.clearCurrentWordToGuess()
    }

    companion object {
        private const val TAG = "ClearCurrentWordUseCase"
    }

}
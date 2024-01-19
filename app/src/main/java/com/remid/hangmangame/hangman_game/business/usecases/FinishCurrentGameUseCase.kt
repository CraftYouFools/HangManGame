package com.remid.hangmangame.hangman_game.business.usecases

import android.util.Log
import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import javax.inject.Inject

class FinishCurrentGameUseCase @Inject constructor(private val repository: HangmanGameRepository) {
    suspend fun execute(isVictory :Boolean) {
        Log.d(TAG, "FinishCurrentGameUseCase")
        repository.updateGameNumber()
        if(isVictory) {
            repository.updateNbVictories()
        }
        repository.clearCurrentGuessedLetters()
        repository.clearTries()
    }

    companion object {
        private const val TAG = "FinishCurrentGameUseCas"
    }

}
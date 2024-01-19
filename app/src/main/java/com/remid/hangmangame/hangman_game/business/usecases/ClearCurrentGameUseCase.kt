package com.remid.hangmangame.hangman_game.business.usecases

import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import javax.inject.Inject

class ClearCurrentGameUseCase @Inject constructor(private val repository: HangmanGameRepository) {
    suspend fun execute() {
        repository.clearCurrentWordToGuess()
        repository.clearCurrentGuessedLetters()
        repository.clearTries()
    }

    companion object {
        private const val TAG = "ClearCurrentWordUseCase"
    }

}
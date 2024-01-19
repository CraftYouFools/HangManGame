package com.remid.hangmangame.hangman_game.business.usecases

import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import javax.inject.Inject

class ResetGameHistoryUseCase @Inject constructor(private val repository: HangmanGameRepository) {
    suspend fun execute() {
        repository.clearGameNumber()
        repository.clearVictories()
    }
}
package com.remid.hangmangame.hangman_game.business.usecases

import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import javax.inject.Inject

class InitWordListUseCase @Inject constructor(private val repository: HangmanGameRepository) {
    suspend fun execute() {
        val list = mutableListOf("Hello", "Amazing","Difficult", "Interview","Mobile", "Android", "Always", "Payxpert", "Challenge")
        repository.setupAllAvailableWords(list)
    }
}
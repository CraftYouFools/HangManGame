package com.remid.hangmangame.hangman_game.business.usecases

import javax.inject.Inject

class IsGameLostUseCase @Inject constructor() {
    suspend fun execute(guessNumber : Int) : Boolean {
        return guessNumber> MAX_GUESS_VALUE
    }

    companion object {
        const val MAX_GUESS_VALUE = 10
    }

}
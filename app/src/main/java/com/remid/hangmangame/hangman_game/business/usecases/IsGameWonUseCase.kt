package com.remid.hangmangame.hangman_game.business.usecases

import android.util.Log
import javax.inject.Inject

class IsGameWonUseCase @Inject constructor () {
    suspend fun execute(guessedWord : String?, wordToGuess : String?) : Boolean {
        val wordToCompare = guessedWord?.replace(" ","")
        Log.d(TAG , "compare words guessedWord : ${wordToCompare?.lowercase()} and wordToGuess : ${wordToGuess?.lowercase()}")
        return wordToCompare?.lowercase()==wordToGuess?.lowercase()
    }

    companion object {
        private const val TAG = "IsGameWonUseCase"
    }

}
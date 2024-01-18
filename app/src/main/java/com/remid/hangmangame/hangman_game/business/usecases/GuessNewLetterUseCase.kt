package com.remid.hangmangame.hangman_game.business.usecases

import android.util.Log
import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import javax.inject.Inject

class GuessNewLetterUseCase @Inject constructor(private val repository: HangmanGameRepository){
    suspend fun execute(letter : Char, wordToGuess : String) {
        Log.d(TAG, " check for letter : $letter in word : $wordToGuess")
        if(wordToGuess.lowercase().contains(char = letter, ignoreCase = true)) {
            Log.d(TAG, " word contains letter")
            repository.addCurrentGuessedLetter(letter.toString().lowercase())
        }
        else {
            Log.d(TAG, " word doesn't contains letter")
        }
    }

    companion object {
        private const val TAG = "GuessNewLetterUseCase"
    }
}
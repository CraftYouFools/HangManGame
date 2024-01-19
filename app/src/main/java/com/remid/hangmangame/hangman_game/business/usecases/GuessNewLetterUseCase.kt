package com.remid.hangmangame.hangman_game.business.usecases

import android.util.Log
import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.shared.business.HangAppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GuessNewLetterUseCase @Inject constructor(private val repository: HangmanGameRepository){
    suspend fun execute(letter : Char, wordToGuess : String) {
        withContext(Dispatchers.IO) {
            Log.d(TAG, " check for letter : $letter in word : $wordToGuess")
            if(wordToGuess.lowercase().contains(char = letter, ignoreCase = true)) {
                Log.d(TAG, " word contains letter")
                when (val result = repository.getCurrentGuessedLetters()){
                    is HangAppResult.OnFailure -> {
                        Log.d(TAG, "Issue while getting letters from datastore")
                    }
                    is HangAppResult.OnSuccess -> {
                        if(result.data.firstOrNull()?.contains(letter.lowercase())==false){
                            repository.updateTries(false)
                        }
                    }
                }
                repository.addCurrentGuessedLetter(letter.lowercase())
            }
            else {
                Log.d(TAG, " word doesn't contains letter")
                repository.updateTries(true)
            }
        }

    }

    companion object {
        private const val TAG = "GuessNewLetterUseCase"
    }
}
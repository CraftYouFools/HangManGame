package com.remid.hangmangame.hangman_game.business.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetHiddenWordUseCase @Inject constructor() {

    suspend fun execute(wordToGuess: String?, guessedLetters: List<String>): String {
        return withContext(Dispatchers.IO) {
            if (wordToGuess == null)
                return@withContext ""
            var stringResult = ""

            wordToGuess.lowercase().forEach { char ->
                if (guessedLetters.contains(char.toString().lowercase())) {
                    stringResult += char
                } else {
                    stringResult += "_"
                }
            }
            return@withContext stringResult
        }
    }

}
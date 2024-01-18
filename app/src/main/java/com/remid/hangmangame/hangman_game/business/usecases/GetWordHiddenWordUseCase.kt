package com.remid.hangmangame.hangman_game.business.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWordHiddenWordUseCase @Inject constructor() {

    suspend fun execute(wordToGuess: String?, gessedLetters: List<String>): String {
        return withContext(Dispatchers.IO) {
            if (wordToGuess == null)
                return@withContext ""
            var stringResult = ""

            wordToGuess.lowercase().forEach { char ->
                if (gessedLetters.contains(char.toString().lowercase())) {
                    stringResult += char
                } else {
                    stringResult += "_"
                }
                stringResult += " "
            }
            return@withContext stringResult
        }
    }

}
package com.remid.hangmangame.hangman_game.business.usecases

import android.util.Log
import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.shared.business.HangAppResult
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class IsGameWonUseCase @Inject constructor(
    private val repository: HangmanGameRepository,
    private val getHiddenWordUseCase: GetHiddenWordUseCase,
    private val getCurrentWordUseCase: GetCurrentWordUseCase
) {
    suspend fun execute(): Boolean {
        var guessedWord: String? = null
        var wordToGuess: String? = null
        when (val letterResult = repository.getCurrentGuessedLetters()) {
            is HangAppResult.OnFailure -> {
                throw exception
            }

            is HangAppResult.OnSuccess -> {
                wordToGuess = getCurrentWordUseCase.execute()
                guessedWord = getHiddenWordUseCase.execute(wordToGuess, letterResult.data.first())
            }
        }

        Log.d(
            TAG,
            "compare words guessedWord : ${guessedWord.lowercase()} and wordToGuess : ${wordToGuess?.lowercase()}"
        )
        return guessedWord.lowercase() == wordToGuess?.lowercase()
    }

    companion object {
        private val exception = Exception("Impossible to get letters from repository ")
        private const val TAG = "IsGameWonUseCase"
    }

}
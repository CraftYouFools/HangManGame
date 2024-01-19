package com.remid.hangmangame.hangman_game.business

import kotlinx.coroutines.flow.Flow
import com.remid.hangmangame.shared.business.HangAppResult

interface HangmanGameRepository {

    suspend fun getTries():  HangAppResult<Flow<Int>>
    suspend fun updateTries(increment: Boolean)

    suspend fun getCurrentWordToGuess():  HangAppResult<Flow<String?>>

    suspend fun setCurrentWordToGuess(word: String)

    suspend fun getCurrentGuessedLetters(): HangAppResult<Flow<List<String>>>

    suspend fun addCurrentGuessedLetter(char: String)

    suspend fun getVictoriesNumber(): HangAppResult<Flow<Int>>

    suspend fun updateNbVictories()

    suspend fun getGameNumber():  HangAppResult<Flow<Int>>

    suspend fun updateGameNumber()

    suspend fun getAllAvailableWords():  HangAppResult<Flow<List<String>>>

    suspend fun setupAllAvailableWords(words: List<String>)
    suspend fun clearCurrentGuessedLetters()
    suspend fun clearCurrentWordToGuess()
    suspend fun clearTries()
    suspend fun clearGameNumber()
    suspend fun clearVictories()
}
package com.remid.hangmangame.hangman_game.business

import kotlinx.coroutines.flow.Flow
import com.remid.hangmangame.shared.business.HangAppResult

interface HangmanGameRepository {

    suspend fun getTriesLeft():  HangAppResult<Flow<Int>>
    suspend fun setTriesLeft(tries: Int)

    suspend fun getCurrentWordToGuess():  HangAppResult<Flow<String?>>

    suspend fun setCurrentWordToGuess(word: String)

    suspend fun getCurrentGuessedLetters(): HangAppResult<Flow<List<String>>>

    suspend fun addCurrentGuessedLetter(char: String)

    suspend fun getVictoriesNumber(): HangAppResult<Flow<Int>>

    suspend fun setNbVictories(number: Int)

    suspend fun getGameNumber():  HangAppResult<Flow<Int>>

    suspend fun setNbGames(number: Int)

    suspend fun getAllAvailableWords():  HangAppResult<Flow<List<String>>>


    suspend fun setupAllAvailableWords(words: List<String>)
    suspend fun clearCurrentGuessedLetters()
}
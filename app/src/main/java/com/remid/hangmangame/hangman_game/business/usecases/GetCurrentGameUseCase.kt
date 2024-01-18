package com.remid.hangmangame.hangman_game.business.usecases

import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.hangman_game.business.entity.HangmanGameDetails
import com.remid.hangmangame.shared.business.HangAppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCurrentGameUseCase @Inject constructor(private val hangmanGameRepository: HangmanGameRepository) {

    suspend fun execute(): HangAppResult<Flow<HangmanGameDetails>> {
        val victoriesNumber = hangmanGameRepository.getVictoriesNumber()
        val gameNumber = hangmanGameRepository.getGameNumber()
        val triesLeft = hangmanGameRepository.getTriesLeft()
        val currentWord = hangmanGameRepository.getCurrentWordToGuess()
        val currentLetters = hangmanGameRepository.getCurrentGuessedLetters()

        if (victoriesNumber is HangAppResult.OnFailure) {
            return HangAppResult.OnFailure(victoriesNumber.exception)
        }
        if (gameNumber is HangAppResult.OnFailure) {
            return HangAppResult.OnFailure(gameNumber.exception)
        }
        if (triesLeft is HangAppResult.OnFailure) {
            return HangAppResult.OnFailure(triesLeft.exception)
        }
        if (currentWord is HangAppResult.OnFailure) {
            return HangAppResult.OnFailure(currentWord.exception)
        }
        if (currentLetters is HangAppResult.OnFailure) {
            return HangAppResult.OnFailure(currentLetters.exception)
        }
        else {
            victoriesNumber as HangAppResult.OnSuccess
            gameNumber as HangAppResult.OnSuccess
            triesLeft as HangAppResult.OnSuccess
            currentWord as HangAppResult.OnSuccess
            currentLetters as HangAppResult.OnSuccess

            return HangAppResult.OnSuccess(
                combine(
                    victoriesNumber.data,
                    gameNumber.data,
                    triesLeft.data,
                    currentWord.data,
                    currentLetters.data
                ) { victoriesNumberData, gameNumberData, triesLeftData, currentWordData, currentLettersData ->
                    HangmanGameDetails(
                        victoriesNumberData,
                        gameNumberData,
                        triesLeftData,
                        currentWordData,
                        currentLettersData
                    )
                })
        }
    }
}
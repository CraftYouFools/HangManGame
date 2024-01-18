package com.remid.hangmangame.hangman_game.business

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
        } else {
            victoriesNumber as HangAppResult.OnSuccess
            gameNumber as HangAppResult.OnSuccess
            triesLeft as HangAppResult.OnSuccess
            currentWord as HangAppResult.OnSuccess

            return HangAppResult.OnSuccess(
                combine(
                    victoriesNumber.data,
                    gameNumber.data,
                    triesLeft.data,
                    currentWord.data
                ) { victoriesNumberData, gameNumberData, triesLeftData, currentWordData ->
                    HangmanGameDetails(
                        victoriesNumberData,
                        gameNumberData,
                        triesLeftData,
                        currentWordData
                    )
                })
        }
    }
}
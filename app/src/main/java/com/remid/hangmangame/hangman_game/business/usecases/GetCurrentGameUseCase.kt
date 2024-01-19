package com.remid.hangmangame.hangman_game.business.usecases

import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.hangman_game.business.entity.HangmanGameDetails
import com.remid.hangmangame.shared.business.HangAppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentGameUseCase @Inject constructor(private val hangmanGameRepository: HangmanGameRepository,
                                                private val isGameLostUseCase: IsGameLostUseCase,
                                                private val isGameWonUseCase: IsGameWonUseCase,
                                                private val getHiddenWordUseCase: GetHiddenWordUseCase, ) {

    suspend fun execute(): HangAppResult<Flow<HangmanGameDetails>> {

        return withContext(Dispatchers.IO) {
            val victoriesNumber = hangmanGameRepository.getVictoriesNumber()
            val gameNumber = hangmanGameRepository.getGameNumber()
            val triesLeft = hangmanGameRepository.getTries()
            val currentWord = hangmanGameRepository.getCurrentWordToGuess()
            val currentLetters = hangmanGameRepository.getCurrentGuessedLetters()

            if (victoriesNumber is HangAppResult.OnFailure) {
                return@withContext HangAppResult.OnFailure(victoriesNumber.exception)
            }
            if (gameNumber is HangAppResult.OnFailure) {
                return@withContext HangAppResult.OnFailure(gameNumber.exception)
            }
            if (triesLeft is HangAppResult.OnFailure) {
                return@withContext HangAppResult.OnFailure(triesLeft.exception)
            }
            if (currentWord is HangAppResult.OnFailure) {
                return@withContext HangAppResult.OnFailure(currentWord.exception)
            }
            if (currentLetters is HangAppResult.OnFailure) {
                return@withContext HangAppResult.OnFailure(currentLetters.exception)
            }
            else {
                victoriesNumber as HangAppResult.OnSuccess
                gameNumber as HangAppResult.OnSuccess
                triesLeft as HangAppResult.OnSuccess
                currentWord as HangAppResult.OnSuccess
                currentLetters as HangAppResult.OnSuccess

                return@withContext HangAppResult.OnSuccess(
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
                            currentLettersData,
                            isGameWonUseCase.execute( getHiddenWordUseCase.execute(currentWordData, currentLettersData),currentWordData),
                            isGameLostUseCase.execute(triesLeftData),
                        )
                    })
            }
        }

    }
}
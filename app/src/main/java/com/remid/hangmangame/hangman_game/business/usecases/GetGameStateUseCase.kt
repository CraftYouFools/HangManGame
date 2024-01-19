package com.remid.hangmangame.hangman_game.business.usecases

import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.hangman_game.business.entity.HangManGameFinishDetails
import com.remid.hangmangame.shared.business.HangAppResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetGameStateUseCase @Inject constructor(
    private val repository: HangmanGameRepository,
    private val getCurrentWordUseCase: GetCurrentWordUseCase,
    private val isGameLostUseCase: IsGameLostUseCase,
    private val isGameWonUseCase: IsGameWonUseCase,
) {

    suspend fun execute(): HangManGameFinishDetails {
        return withContext(Dispatchers.IO) {
            when (val result = repository.getTries()) {
                is HangAppResult.OnSuccess -> {
                    val tries = result.data.firstOrNull() ?: throw exception
                    if (isGameLostUseCase.execute(tries)) {
                        return@withContext HangManGameFinishDetails(
                            getCurrentWordUseCase.execute(),
                            false
                        )
                    }
                }

                is HangAppResult.OnFailure -> {
                    throw exception
                }
            }
            return@withContext HangManGameFinishDetails(
                getCurrentWordUseCase.execute(),
                isGameWonUseCase.execute()
            )
        }
    }

    private val exception = Exception("Impossible to get data from repository ")

}
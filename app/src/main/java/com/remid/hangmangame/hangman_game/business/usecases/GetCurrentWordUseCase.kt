package com.remid.hangmangame.hangman_game.business.usecases

import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.hangman_game.business.entity.HangManGameFinishDetails
import com.remid.hangmangame.hangman_game.business.entity.HangmanGameDetails
import com.remid.hangmangame.shared.business.HangAppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception
import javax.inject.Inject
import kotlin.jvm.Throws

class GetCurrentWordUseCase @Inject constructor(private val repository: HangmanGameRepository) {

    suspend fun execute() : HangAppResult<Flow<HangManGameFinishDetails>> {
        return when (val result = repository.getCurrentWordToGuess()) {
            is HangAppResult.OnSuccess -> {
                HangAppResult.OnSuccess(result.data.map { t ->
                    HangManGameFinishDetails(t)
                })
            }
            is HangAppResult.OnFailure -> {
                HangAppResult.OnFailure(Exception("$TAG Error while getting current word to guess from data store "))
            }
        }
    }

    companion object {
        private const val TAG = "GetCurrentWordUseCase"
    }
}
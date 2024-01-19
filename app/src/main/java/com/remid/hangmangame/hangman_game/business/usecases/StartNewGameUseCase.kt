package com.remid.hangmangame.hangman_game.business.usecases

import android.util.Log
import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.shared.business.HangAppResult
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class StartNewGameUseCase @Inject constructor(private val repository: HangmanGameRepository) {
    suspend fun execute() {
        Log.d(TAG, "Start new Game ")
        when(val result = repository.getCurrentWordToGuess()) {
            is HangAppResult.OnSuccess -> {
                result.data.first().let{ currentWord ->
                    if(currentWord==null){
                        when(val resultAllWorld = repository.getAllAvailableWords()) {
                            is HangAppResult.OnSuccess -> {
                                resultAllWorld.data.first().let{
                                    if(it.isEmpty()) {
                                        Log.d(TAG, "No words available in datastore")
                                        throw Exception("$TAG : No words available in datastore")
                                    }
                                    else {
                                        Log.d(TAG, "list word : $it")
                                        val newWord = it[(it.indices).random()]
                                        repository.setCurrentWordToGuess(newWord)
                                    }
                                }
                            }
                            else -> {
                                Log.d(TAG, "Issue with getting all available words from datastore")
                                throw Exception("$TAG : Issue with getting all available words from datastore")
                            }
                        }

                    }
                }
            }
            else -> {
                Log.d(TAG, "Issue with getting current word from datastore")
                throw Exception("$TAG : Issue with getting current word from datastore")
            }
        }
    }

    companion object {
        private const val TAG = "StartNewGameUseCase"
    }

}
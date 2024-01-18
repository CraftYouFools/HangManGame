package com.remid.hangmangame.hangman_game.business.usecases

import android.util.Log
import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import com.remid.hangmangame.shared.business.HangAppResult
import javax.inject.Inject

class StartNewGameUseCase @Inject constructor(private val repository: HangmanGameRepository) {
    suspend fun execute() {
        when(val result = repository.getCurrentWordToGuess()) {
            is HangAppResult.OnSuccess -> {
                result.data.collect{ currentWord ->
                    if(currentWord==null){
                        when(val resultAllWorld = repository.getAllAvailableWords()) {
                            is HangAppResult.OnSuccess -> {
                                resultAllWorld.data.collect{
                                    if(it.isEmpty()) {
                                        Log.d(TAG, "No words available in datastore")
                                        throw Exception("$TAG : No words available in datastore")
                                    }
                                    else {
                                        val newWord = it[(0..it.size).random()]
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
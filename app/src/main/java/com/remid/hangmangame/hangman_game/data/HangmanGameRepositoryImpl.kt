package com.remid.hangmangame.hangman_game.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.remid.hangmangame.di.application.AppContextQualifier
import com.remid.hangmangame.hangman_game.business.HangmanGameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.remid.hangmangame.shared.business.HangAppResult
import javax.inject.Inject

class HangmanGameRepositoryImpl @Inject constructor(@AppContextQualifier private val context: Context) : HangmanGameRepository {

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    override suspend fun getTries(): HangAppResult<Flow<Int>> {
        return HangAppResult.OnSuccess(context.dataStore.data.map { preference -> preference[TRIES_KEY] ?: 0 })
    }

    override suspend fun updateTries(increment: Boolean) {
        context.dataStore.edit { preference ->
            val currentValue = preference[TRIES_KEY] ?: 0
            val value = when (increment) {
                true -> { currentValue + 1 }
                else -> { if (currentValue > 0) { currentValue - 1 } else { 0 } }
            }
            preference[TRIES_KEY] = value
        }
    }

    override suspend fun clearTries() {
        context.dataStore.edit { preference ->
            preference.remove(TRIES_KEY)
        }
    }

    override suspend fun getCurrentWordToGuess(): HangAppResult<Flow<String?>> {
        return HangAppResult.OnSuccess(context.dataStore.data.map { preference -> preference[CURRENT_WORD_KEY] })
    }

    override suspend fun setCurrentWordToGuess(word: String) {
        Log.d(TAG, "setCurrentWordToGuess : $word")
        context.dataStore.edit { preference ->
            preference[CURRENT_WORD_KEY] = word
        }
    }

    override suspend fun clearCurrentWordToGuess() {
        context.dataStore.edit { preference ->
            Log.d(TAG, "clearCurrentWordToGuess()" )
            preference.remove(CURRENT_WORD_KEY)
        }
    }


    override suspend fun getCurrentGuessedLetters(): HangAppResult<Flow<List<String>>> {
        return HangAppResult.OnSuccess(context.dataStore.data.map {
                preference -> preference[CHAR_LIST_KEY]?.toList()?: listOf()
        })
    }

    override suspend fun addCurrentGuessedLetter(char: String) {
        context.dataStore.edit { preference ->
            Log.d(TAG, "Adding letter : $char in datastore")
            val set = preference[CHAR_LIST_KEY]?.toMutableSet() ?: mutableSetOf()
            Log.d(TAG, "setContent : $set")
            set.add(char)
            Log.d(TAG, "Updating letters in datastore : $set")
            preference[CHAR_LIST_KEY] = set
        }
    }

    override suspend fun clearCurrentGuessedLetters() {
        context.dataStore.edit { preference ->
            preference.remove(CHAR_LIST_KEY)
        }
    }

    override suspend fun getVictoriesNumber(): HangAppResult<Flow<Int>> {
        return HangAppResult.OnSuccess(context.dataStore.data.map { preference -> preference[VICTORIES_KEY] ?: 0 })
    }

    override suspend fun updateNbVictories() {
        context.dataStore.edit { preference ->
            if (preference[VICTORIES_KEY] == null) {
                preference[VICTORIES_KEY] = 1
            } else {
                preference[VICTORIES_KEY]?.let { value ->
                    preference[VICTORIES_KEY] = value + 1
                }
            }
        }
    }

    override suspend fun getGameNumber(): HangAppResult<Flow<Int>> {
        return HangAppResult.OnSuccess(context.dataStore.data.map { preference -> preference[GAME_NUMBER_KEY] ?: 0 })
    }

    override suspend fun updateGameNumber() {
        context.dataStore.edit { preference ->
            if (preference[GAME_NUMBER_KEY] == null) {
                preference[GAME_NUMBER_KEY] = 1
            } else {
                preference[GAME_NUMBER_KEY]?.let { value ->
                    preference[GAME_NUMBER_KEY] = value + 1
                }
            }
        }
    }

    override suspend fun clearGameNumber() {
        context.dataStore.edit { preference ->
            preference.remove(GAME_NUMBER_KEY)
        }
    }

    override suspend fun clearVictories() {
        context.dataStore.edit { preference ->
            preference.remove(VICTORIES_KEY)
        }
    }

    override suspend fun getAllAvailableWords():  HangAppResult<Flow<List<String>>> {
        return HangAppResult.OnSuccess(context.dataStore.data.map {
            preference -> preference[WORD_LIST_KEY]?.toList()?: listOf()
        })
    }

    override suspend fun setupAllAvailableWords(words: List<String>) {
        context.dataStore.edit { preference ->
            if (preference[WORD_LIST_KEY].isNullOrEmpty()) {
                Log.d(TAG, "Setting up word list in datastore ")
                val set = mutableSetOf<String>()
                set.addAll(words)
                preference[WORD_LIST_KEY] = set
            }
            else {
                Log.d(TAG, "No need to setup word list in datastore ")
            }
        }
    }



    companion object {
        val TRIES_KEY = intPreferencesKey("TRIES_KEY")
        val CURRENT_WORD_KEY = stringPreferencesKey("CURRENT_WORD_KEY")
        val VICTORIES_KEY = intPreferencesKey("VICTORIES_KEY")
        val GAME_NUMBER_KEY = intPreferencesKey("GAME_NUMBER_KEY")
        val WORD_LIST_KEY = stringSetPreferencesKey("WORD_LIST_KEY")
        val CHAR_LIST_KEY = stringSetPreferencesKey("CHAR_LIST_KEY")

        private const val USER_PREFERENCES_NAME = "com.remid.hangmangame.game"

        private const val TAG = "HangmanGameRepos"
    }
}
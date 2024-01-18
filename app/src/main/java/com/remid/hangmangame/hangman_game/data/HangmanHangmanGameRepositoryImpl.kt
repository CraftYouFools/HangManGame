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

class HangmanHangmanGameRepositoryImpl @Inject constructor(@AppContextQualifier private val context: Context) : HangmanGameRepository {

    private val Context.dataStore by preferencesDataStore(
        name = USER_PREFERENCES_NAME
    )

    override suspend fun getTriesLeft(): HangAppResult<Flow<Int>> {
        return HangAppResult.OnSuccess(context.dataStore.data.map { preference -> preference[TRIES_KEY] ?: 0 })
    }

    override suspend fun setTriesLeft(tries: Int) {
        context.dataStore.edit { preference ->
            preference[TRIES_KEY] = tries
        }
    }

    override suspend fun getCurrentWordToGuess(): HangAppResult<Flow<String?>> {
        return HangAppResult.OnSuccess(context.dataStore.data.map { preference -> preference[CURRENT_WORD_KEY] })
    }

    override suspend fun setCurrentWordToGuess(word: String) {
        context.dataStore.edit { preference ->
            preference[CURRENT_WORD_KEY] = word
        }
    }

    override suspend fun getVictoriesNumber(): HangAppResult<Flow<Int>> {
        return HangAppResult.OnSuccess(context.dataStore.data.map { preference -> preference[VICTORIES_KEY] ?: 0 })
    }

    override suspend fun setNbVictories(number: Int) {
        context.dataStore.edit { preference ->
            preference[VICTORIES_KEY] = number
        }
    }

    override suspend fun getGameNumber(): HangAppResult<Flow<Int>> {
        return HangAppResult.OnSuccess(context.dataStore.data.map { preference -> preference[GAME_NUMBER_KEY] ?: 0 })
    }

    override suspend fun setNbGames(number: Int) {
        context.dataStore.edit { preference ->
            preference[GAME_NUMBER_KEY] = number
        }        }

    override suspend fun getAllAvailableWords():  HangAppResult<Flow<List<String>>> {
        return HangAppResult.OnSuccess(context.dataStore.data.map {
            preference -> preference[WORD_LIST_KEY]?.toList()?: listOf()
        })
    }

    override suspend fun setupAllAvailableWords(words: List<String>) {
        context.dataStore.edit { preference ->
            if (preference[WORD_LIST_KEY]?.isEmpty() == true) {
                Log.d(TAG, "Setting up word list in datastore ")
                val set = mutableSetOf<String>()
                set.addAll(words)
                preference[WORD_LIST_KEY] = set
            }
        }
    }



    companion object {
        val TRIES_KEY = intPreferencesKey("TRIES_KEY")
        val CURRENT_WORD_KEY = stringPreferencesKey("CURRENT_WORD_KEY")
        val VICTORIES_KEY = intPreferencesKey("VICTORIES_KEY")
        val GAME_NUMBER_KEY = intPreferencesKey("GAME_NUMBER_KEY")
        val WORD_LIST_KEY = stringSetPreferencesKey("WORD_LIST_KEY")

        private const val USER_PREFERENCES_NAME = "com.remid.hangmangame.game"

        private const val TAG = "HangmanHangmanGameRepos"
    }
}
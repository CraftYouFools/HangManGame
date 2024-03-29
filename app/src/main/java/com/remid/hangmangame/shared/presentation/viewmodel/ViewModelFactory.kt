package com.remid.hangmangame.shared.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.remid.hangmangame.hangman_game.presentation.viewmodel.HangManGameViewModel
import com.remid.hangmangame.hangman_game.presentation.viewmodel.HangManGameWonViewModel
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    private val hangManGameViewModelProvider: Provider<HangManGameViewModel>,
    private val hangManGameWonViewModel: Provider<HangManGameWonViewModel>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HangManGameViewModel::class.java)) {
            hangManGameViewModelProvider.get() as T
        } else if (modelClass.isAssignableFrom(HangManGameWonViewModel::class.java)) {
            hangManGameWonViewModel.get() as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found ")
        }
    }
}
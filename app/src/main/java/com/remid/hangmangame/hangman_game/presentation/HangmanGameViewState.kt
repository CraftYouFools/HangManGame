package com.remid.hangmangame.hangman_game.presentation

sealed class HangmanGameViewState {

    object Loading : HangmanGameViewState()
    data class Content(val home: HangmanGameContent) : HangmanGameViewState()
    object Error : HangmanGameViewState()

}
package com.remid.hangmangame.hangman_game.presentation

sealed class HangmanGameViewState {

    object Loading : HangmanGameViewState()
    data class Content(val content: HangmanGameContent) : HangmanGameViewState()
    object Error : HangmanGameViewState()

    data class GameWon (val content: HangmanGameContent) : HangmanGameViewState()

    data class GamLost(val content: HangmanGameContent) : HangmanGameViewState()

}
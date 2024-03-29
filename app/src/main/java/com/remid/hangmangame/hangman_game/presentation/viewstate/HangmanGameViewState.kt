package com.remid.hangmangame.hangman_game.presentation.viewstate

import com.remid.hangmangame.hangman_game.presentation.viewstate.viewstate_content.HangmanGameContent

sealed class HangmanGameViewState {

    object Loading : HangmanGameViewState()
    data class Content(val content: HangmanGameContent) : HangmanGameViewState()
    object Error : HangmanGameViewState()

    data class GameWon (val content: HangmanGameContent) : HangmanGameViewState()

    data class GameLost(val content: HangmanGameContent) : HangmanGameViewState()

}